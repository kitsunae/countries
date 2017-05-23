package net.lashin.core.services;

import net.lashin.core.beans.CountryImage;
import net.lashin.core.beans.ImageSize;
import net.lashin.core.dao.CityImageRepository;
import net.lashin.core.dao.CityRepository;
import net.lashin.core.dao.CountryImageRepository;
import net.lashin.core.dao.CountryRepository;
import net.lashin.core.hateoas.ImageResourceType;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final CityImageRepository cityImageRepository;
    private final CountryImageRepository countryImageRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public ImageServiceImpl(CityImageRepository cityImageRepository, CountryImageRepository countryImageRepository, CityRepository cityRepository, CountryRepository countryRepository) {
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
    public List<CountryImage> save(String countryCode, InputStream inputStream, ImageResourceType imageResourceType, String fileName, String description) {
        List<CountryImage> result = new ArrayList<>(5);
        try {
            BufferedImage image = ImageIO.read(inputStream);
            for (ImageSize size : ImageSize.values()) {
                BufferedImage scaled = Scalr.resize(image, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, size.getSize());
                CountryImage countryImage = new CountryImage();
                countryImage.setDescription(description);
                countryImage.setSize(size);
                countryImage.setUrl("src\\test\\resources\\test_files\\" + size.name() + "\\" + fileName);
                countryImage.setCountry(countryRepository.getOne(countryCode));
                CountryImage save = countryImageRepository.save(countryImage);
                boolean writed = ImageIO.write(scaled, "jpg", new File("src\\test\\resources\\test_files\\" + size.name() + "\\" + save.getId() + ".jpg"));
                if (!writed)
                    throw new RuntimeException("Failed to write file");
                save.setUrl("src\\test\\resources\\test_files\\" + size.name() + "\\" + save.getId() + ".jpg");
                CountryImage entity = countryImageRepository.save(save);
                scaled.flush();
                result.add(entity);
            }
            image.flush();

        } catch (IOException e) {
            LOG.error("Failed to process input stream of image {}, error: {}", imageResourceType, e.getMessage());
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
    public List<CountryImage> save(String countryCode, InputStream inputStream, ImageResourceType imageResourceType, String fileName) {
        return save(countryCode, inputStream, imageResourceType, fileName, null);
    }
}
