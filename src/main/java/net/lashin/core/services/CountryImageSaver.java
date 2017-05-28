package net.lashin.core.services;


import net.lashin.core.beans.CountryImage;
import net.lashin.core.beans.ImageSize;
import net.lashin.core.dao.CountryImageRepository;
import net.lashin.core.dao.CountryRepository;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

public class CountryImageSaver implements Callable<CountryImage> {

    private final ImageSize size;
    private final BufferedImage imageToSave;
    private final Path rootPath;
    private final String description;
    private final CountryRepository countryRepository;
    private final CountryImageRepository countryImageRepository;
    private final String startDirectory;
    private final String fileName;
    private final String countryCode;
    private final String fileType;

    public CountryImageSaver(ImageSize size, BufferedImage imageToSave, Path rootPath, String description, CountryRepository countryRepository, CountryImageRepository countryImageRepository, String startDirectory, String fileName, String countryCode, String fileType) {
        this.size = size;
        this.imageToSave = imageToSave;
        this.rootPath = rootPath;
        this.description = description;
        this.countryRepository = countryRepository;
        this.countryImageRepository = countryImageRepository;
        this.startDirectory = startDirectory;
        this.fileName = fileName;
        this.countryCode = countryCode;
        this.fileType = fileType;
    }

    @Override
    public CountryImage call() throws Exception {
        if (size == ImageSize.DEFAULT)
            size.setSize(Math.max(imageToSave.getHeight(), imageToSave.getWidth()));
        BufferedImage scaled = Scalr.resize(imageToSave, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, size.getSize());
        CountryImage countryImage = new CountryImage();
        countryImage.setDescription(description);
        countryImage.setSize(size);
        countryImage.setUrl(startDirectory + "/" + size.name() + "/" + fileName);
        countryImage.setCountry(countryRepository.getOne(countryCode));
        CountryImage save = countryImageRepository.save(countryImage);
        Path dir = rootPath.resolve(size.name());
        dir = Files.createDirectories(dir);
        Path file = dir.resolve(save.getId() + fileType);
        file = Files.createFile(file);
        boolean writed = ImageIO.write(scaled, fileType.substring(1), file.toFile());
        if (!writed)
            throw new RuntimeException("Failed to write file");
        save.setUrl(startDirectory + "/" + size.name() + "/" + save.getId() + fileType);
        CountryImage entity = countryImageRepository.save(save);
        scaled.flush();
        return entity;
    }
}
