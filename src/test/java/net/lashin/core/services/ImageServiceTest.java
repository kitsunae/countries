package net.lashin.core.services;


import net.lashin.core.beans.CountryImage;
import net.lashin.core.beans.Image;
import net.lashin.core.beans.ImageSize;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    private static final String TEST_FILES_DIRECTORY = "src\\test\\resources\\test_files";
    private static final String TEST_IMAGE_COUNTRY = "img_1.jpg";
    private static final String TEST_IMAGE_CITY = "";

    @Autowired
    private ImageService imageService;

    @Test
    public void serviceTakesCountryImageAndSavesIt() throws Exception {
        InputStream inputStream = Files.newInputStream(Paths.get(TEST_FILES_DIRECTORY, TEST_IMAGE_COUNTRY));
        List<CountryImage> countryImages = imageService.save("RUS", inputStream, "img_1.jpg");
        assertEquals(6, countryImages.size());
        Set<ImageSize> sizes = countryImages.stream().map(Image::getSize).collect(Collectors.toSet());
        for (ImageSize is : ImageSize.values()) {
            assertTrue(sizes.contains(is));
            Path path = Paths.get(TEST_FILES_DIRECTORY, "country", "RUS", is.name());
            assertTrue(Files.exists(path));
        }
    }

    public void serviceTakesCityImageAndSavesIt() throws Exception {

    }
}
