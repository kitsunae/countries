package net.lashin.web.controllers;

import net.lashin.core.beans.CityImage;
import net.lashin.core.beans.CountryImage;
import net.lashin.core.beans.ImageSize;
import net.lashin.core.hateoas.ImageResource;
import net.lashin.core.hateoas.asm.ImageResourceHandler;
import net.lashin.core.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/images")
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    private final ImageService imageService;
    private final ImageResourceHandler resourceHandler;

    @Autowired
    public ImageController(ImageService imageService, ImageResourceHandler resourceHandler) {
        this.imageService = imageService;
        this.resourceHandler = resourceHandler;
    }

    @RequestMapping(value = "/country/{code}", method = RequestMethod.POST)
    public ResponseEntity<List<ImageResource>> saveCountryImage(@PathVariable String code, @RequestPart MultipartFile file, String description) {
        LOGGER.debug("Processing country image of country {} with description {}", code, description);
        if (file == null)
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        try {
            List<CountryImage> images = imageService.save(code, file.getInputStream(), file.getOriginalFilename(), description);
            List<ImageResource> imageResources = images.stream().map(resourceHandler::toResource).collect(Collectors.toList());
            return new ResponseEntity<>(imageResources, HttpStatus.CREATED);
        } catch (IOException e) {
            LOGGER.error("Error processing multipart data: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/city/{id}", method = RequestMethod.POST)
    public ResponseEntity<List<ImageResource>> saveCityImage(@PathVariable Long id, @RequestPart MultipartFile file, String description) {
        LOGGER.debug("Processing city image of city {} with description {}", id, description);
        if (file == null)
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        try {
            List<CityImage> images = imageService.save(id, file.getInputStream(), file.getOriginalFilename(), description);
            List<ImageResource> resources = images.stream().map(resourceHandler::toResource).collect(Collectors.toList());
            return new ResponseEntity<>(resources, HttpStatus.CREATED);
        } catch (IOException e) {
            LOGGER.error("Error processing multipart data: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/country/{code}/{size}/{fileName}", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public
    @ResponseBody
    byte[] getCountryImage(@PathVariable String code, @PathVariable ImageSize size, @PathVariable String fileName) {
        LOGGER.debug("Get request for country image {}/{}/{}", code, size, fileName);
        return imageService.produceRawCountryImage(code, size, fileName);
    }

    @RequestMapping(value = "/city/{id}/{size}/{fileName}", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public
    @ResponseBody
    byte[] getCityImage(@PathVariable Long id, @PathVariable ImageSize size, @PathVariable String fileName) {
        LOGGER.debug("Get request for city image {}/{}/{}", id, size, fileName);
        return imageService.produceRawCityImage(id, size, fileName);
    }
}
