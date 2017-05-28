package net.lashin.core.services;


import net.lashin.core.beans.CityImage;
import net.lashin.core.beans.CountryImage;
import net.lashin.core.beans.Image;
import net.lashin.core.beans.ImageSize;
import net.lashin.core.dao.CityImageRepository;
import net.lashin.core.dao.CountryImageRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class ImageServiceTest extends AbstractServiceTest {

    private static final String TEST_FILES_DIRECTORY = "src/test/resources/test_files";
    private static final String TEST_IMAGE_COUNTRY = "images/country/img_1.jpg";
    private static final String TEST_IMAGE_CITY = "images/city/img_1.jpg";

    @Autowired
    private ImageService imageService;
    @Autowired
    private CountryImageRepository countryImageRepository;
    @Autowired
    private CityImageRepository cityImageRepository;
    @Autowired
    private Environment environment;

    @Test
    public void serviceTakesCountryImageAndSavesIt() throws Exception {
        InputStream inputStream = Files.newInputStream(Paths.get(TEST_FILES_DIRECTORY, TEST_IMAGE_COUNTRY));
        String imageDir = environment.getProperty("image.dir");
        List<CountryImage> countryImages = imageService.save("RUS", inputStream, "img_1.jpg");
        assertEquals(6, countryImages.size());
        Set<ImageSize> sizes = countryImages.stream().map(Image::getSize).collect(Collectors.toSet());
        for (ImageSize is : ImageSize.values()) {
            assertTrue(sizes.contains(is));
            Path path = Paths.get(TEST_FILES_DIRECTORY, "images", "country", "RUS", is.name());
            assertTrue(Files.exists(path));
        }
        Iterable<CountryImage> images = countryImageRepository.findAll();
        for (CountryImage image : images) {
            assertEquals(imageDir + "/country/RUS/" + image.getSize() + "/" + image.getId() + ".jpg", image.getUrl());
        }
    }

    @Test
    public void serviceTakesCityImageAndSavesIt() throws Exception {
        InputStream inputStream = Files.newInputStream(Paths.get(TEST_FILES_DIRECTORY, TEST_IMAGE_CITY));
        String imageDir = environment.getProperty("image.dir");
        List<CityImage> cityImages = imageService.save(3580L, inputStream, "img_1.jpg", "View on Moscow City");
        assertEquals(2, cityImages.size());
        Set<ImageSize> sizes = cityImages.stream().map(Image::getSize).collect(Collectors.toSet());
        for (ImageSize is : sizes) {
            Path path = Paths.get(TEST_FILES_DIRECTORY, "images", "country", "RUS", is.name());
            assertTrue(Files.exists(path));
        }
        Iterable<CityImage> images = cityImageRepository.findAll();
        for (CityImage image : images) {
            assertEquals(imageDir + "/city/3580/" + image.getSize() + "/" + image.getId() + ".jpg", image.getUrl());
        }
    }
}
