package net.lashin.core.services;


import net.lashin.core.beans.CountryImage;
import net.lashin.core.beans.Image;
import net.lashin.core.beans.ImageSize;
import net.lashin.core.hateoas.ImageResourceType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class ImageServiceTest extends AbstractServiceTest {

    private static final String TEST_FILES_DIRECTORY = "src\\test\\resources\\test_files";
    private static final String TEST_IMAGE = "img_1.jpg";

    @Autowired
    private ImageService imageService;

    @Test
    public void serviceTakesCountryImageAndSavesIt() throws Exception {
        InputStream inputStream = Files.newInputStream(Paths.get(TEST_FILES_DIRECTORY, TEST_IMAGE));
        List<CountryImage> countryImages = imageService.save("RUS", inputStream, ImageResourceType.COUNTRY, "img_1.jpg");
        assertEquals(5, countryImages.size());
        Set<ImageSize> sizes = countryImages.stream().map(Image::getSize).collect(Collectors.toSet());
        for (ImageSize is : ImageSize.values()) {
            assertTrue(sizes.contains(is));
        }
//        List<String> urls = countryImages.stream().map(Image::getUrl).collect(Collectors.toList());
//        assertEquals(imageService.getFilesPath(), urls);
    }


}
