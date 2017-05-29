package net.lashin.core.services;


import net.lashin.core.beans.CityImage;
import net.lashin.core.beans.CountryImage;
import net.lashin.core.beans.Image;
import net.lashin.core.beans.ImageSize;
import net.lashin.core.dao.CityImageRepository;
import net.lashin.core.dao.CountryImageRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
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
    @Value("${UPLOAD_DIR}")
    private String uploadDir;

    @Override
    public void setUp() {
        super.setUp();
        Path city = Paths.get(uploadDir + "/images/city/3580");
        Path country = Paths.get(uploadDir + "/images/country/RUS");
        try {
            if (Files.exists(city)) {
                Files.walkFileTree(city, new TestFileVisitor());
            }
            if (Files.exists(country)) {
                Files.walkFileTree(country, new TestFileVisitor());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void serviceTakesCountryImageAndSavesIt() throws Exception {
        InputStream inputStream = Files.newInputStream(Paths.get(TEST_FILES_DIRECTORY, TEST_IMAGE_COUNTRY));
        List<CountryImage> countryImages = imageService.save("RUS", inputStream, "img_1.jpg");
        assertEquals(6, countryImages.size());
        Set<ImageSize> sizes = countryImages.stream().map(Image::getSize).collect(Collectors.toSet());
        for (ImageSize is : ImageSize.values()) {
            assertTrue(sizes.contains(is));
            Path path = Paths.get(uploadDir, "images", "country", "RUS", is.name());
            assertTrue(Files.exists(path));
        }
        Iterable<CountryImage> images = countryImageRepository.findAll();
        for (CountryImage image : images) {
            assertEquals("images/country/RUS/" + image.getSize() + "/" + image.getId() + ".jpg", image.getUrl());
        }
    }

    @Test
    public void serviceTakesCityImageAndSavesIt() throws Exception {
        InputStream inputStream = Files.newInputStream(Paths.get(TEST_FILES_DIRECTORY, TEST_IMAGE_CITY));
        List<CityImage> cityImages = imageService.save(3580L, inputStream, "img_1.jpg", "View on Moscow City");
        assertEquals(2, cityImages.size());
        Set<ImageSize> sizes = cityImages.stream().map(Image::getSize).collect(Collectors.toSet());
        for (ImageSize is : sizes) {
            Path path = Paths.get(uploadDir, "images", "city", "3580", is.name());
            assertTrue(Files.exists(path));
        }
        Iterable<CityImage> images = cityImageRepository.findAll();
        for (CityImage image : images) {
            assertEquals("images/city/3580/" + image.getSize() + "/" + image.getId() + ".jpg", image.getUrl());
        }
    }

    private static class TestFileVisitor implements FileVisitor<Path> {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.TERMINATE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    }
}
