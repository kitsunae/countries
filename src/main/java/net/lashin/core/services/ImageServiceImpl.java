package net.lashin.core.services;

import net.lashin.core.beans.CountryImage;
import net.lashin.core.beans.ImageSize;
import net.lashin.core.dao.CityImageRepository;
import net.lashin.core.dao.CityRepository;
import net.lashin.core.dao.CountryImageRepository;
import net.lashin.core.dao.CountryRepository;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Transactional
public class ImageServiceImpl implements ImageService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final String startDirectory;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final CityImageRepository cityImageRepository;
    private final CountryImageRepository countryImageRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public ImageServiceImpl(String startDirectory, CityImageRepository cityImageRepository, CountryImageRepository countryImageRepository, CityRepository cityRepository, CountryRepository countryRepository) {
        this.startDirectory = startDirectory;
        this.cityImageRepository = cityImageRepository;
        this.countryImageRepository = countryImageRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<String> getFilesPath() {
        return new ArrayList<>();
    }

    @Override
    public List<CountryImage> save(String countryCode, InputStream inputStream, String fileName, String description) {
        List<CountryImage> result = new ArrayList<>(ImageSize.values().length);
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        try {
            BufferedImage image = ImageIO.read(inputStream);
            Path rootDir = Files.createDirectories(Paths.get(startDirectory, "country", countryCode));
            for (ImageSize size : ImageSize.values()) {
                if (size.getSize() > Math.max(image.getHeight(), image.getWidth()))
                    continue;
                if (size == ImageSize.DEFAULT)
                    size.setSize(Math.max(image.getHeight(), image.getWidth()));
                BufferedImage scaled = Scalr.resize(image, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, size.getSize());
                CountryImage countryImage = new CountryImage();
                countryImage.setDescription(description);
                countryImage.setSize(size);
                countryImage.setUrl(startDirectory + "/" + size.name() + "/" + fileName);
                countryImage.setCountry(countryRepository.getOne(countryCode));
                CountryImage save = countryImageRepository.save(countryImage);
                Path dir = rootDir.resolve(size.name());
                dir = Files.createDirectories(dir);
                Path file = dir.resolve(save.getId() + fileType);
                file = Files.createFile(file);
                boolean writed = ImageIO.write(scaled, fileType.substring(1), file.toFile());
                if (!writed)
                    throw new RuntimeException("Failed to write file");
                save.setUrl(startDirectory + "/" + size.name() + "/" + save.getId() + fileType);
                CountryImage entity = countryImageRepository.save(save);
                scaled.flush();
                result.add(entity);
            }
            image.flush();

        } catch (IOException e) {
            LOG.error("Failed to process input stream of image, error: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                //nothing to do here
            }
        }
        return result;
    }

    @Override
    public List<CountryImage> save(String countryCode, InputStream inputStream, String fileName) {
        return save(countryCode, inputStream, fileName, null);
    }
}
