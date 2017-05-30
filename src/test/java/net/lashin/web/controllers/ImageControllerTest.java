package net.lashin.web.controllers;

import net.lashin.util.TestFilesUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class ImageControllerTest extends AbstractControllerTest {

    private static final String TEST_FILES_DIRECTORY = "src/test/resources/test_files";
    private static final String TEST_IMAGE_COUNTRY = "images/country/img_1.jpg";
    private static final String TEST_IMAGE_CITY = "images/city/img_1.jpg";

    @Value("${UPLOAD_DIR}")
    private String uploadDir;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        Path city = Paths.get(uploadDir + "/images/city/3580");
        Path country = Paths.get(uploadDir + "/images/country/RUS");
        TestFilesUtil.cleanUp(country, city);
    }

    @Test
    public void saveCountryImage() throws Exception {
        InputStream inputStream = Files.newInputStream(Paths.get(TEST_FILES_DIRECTORY, TEST_IMAGE_COUNTRY));
        int available = inputStream.available();
        byte[] array = new byte[available];
        inputStream.read(array);
        inputStream.close();
        MockMultipartFile file = new MockMultipartFile("file", "img_1.jpg", "img/jpg", array);
        mockMvc.perform(fileUpload("/images/country/RUS").file(file).param("description", "View on Kremlin"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void saveCityImage() throws Exception {
        InputStream inputStream = Files.newInputStream(Paths.get(TEST_FILES_DIRECTORY, TEST_IMAGE_CITY));
        int available = inputStream.available();
        byte[] array = new byte[available];
        inputStream.read(array);
        inputStream.close();
        MockMultipartFile file = new MockMultipartFile("file", "img_1.jpg", "img/jpg", array);
        mockMvc.perform(fileUpload("/images/city/3580").file(file).param("description", "Moscow City"))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}