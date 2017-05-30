package net.lashin.core.services;

import net.lashin.core.beans.CityImage;
import net.lashin.core.beans.CountryImage;
import net.lashin.core.beans.ImageSize;
import net.lashin.core.dao.CityImageRepository;
import net.lashin.core.dao.CityRepository;
import net.lashin.core.dao.CountryImageRepository;
import net.lashin.core.dao.CountryRepository;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.*;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final String startDirectory;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final CityImageRepository cityImageRepository;
    private final CountryImageRepository countryImageRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public ImageServiceImpl(@Value("${UPLOAD_DIR}") String startDirectory, CityImageRepository cityImageRepository, CountryImageRepository countryImageRepository, CityRepository cityRepository, CountryRepository countryRepository) {
        Objects.requireNonNull(startDirectory);
        this.startDirectory = startDirectory;
        this.cityImageRepository = cityImageRepository;
        this.countryImageRepository = countryImageRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }


    @Override
    @Transactional
    public List<CountryImage> save(String countryCode, InputStream inputStream, String fileName, String description) {
        List<CountryImage> result = new ArrayList<>(ImageSize.values().length);
        List<Future<CountryImage>> images = new ArrayList<>(ImageSize.values().length);
        try {
            BufferedImage image = ImageIO.read(inputStream);
            Path rootDir = Files.createDirectories(Paths.get(startDirectory, "images", "country", countryCode));
            for (ImageSize size : ImageSize.values()) {
                if (size.getSize() > Math.max(image.getHeight(), image.getWidth()))
                    continue;
                Future<CountryImage> imageFuture = executorService.submit(new CountryImageSaver(size, image, rootDir, description, fileName, countryCode));
                images.add(imageFuture);
            }
            image.flush();
            for (Future<CountryImage> imageFuture : images) {
                try {
                    result.add(imageFuture.get(5, TimeUnit.SECONDS));
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    LOG.error("Failed to operate image {}, error : {}", imageFuture, e.getMessage());
                }
            }
            if (result.size() == 0)
                throw new RuntimeException("Failed to execute all types of images for " + countryCode);

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
    @Transactional
    public List<CityImage> save(Long id, InputStream inputStream, String fileName, String description) {
        List<CityImage> result = new ArrayList<>(ImageSize.values().length);
        List<Future<CityImage>> images = new ArrayList<>(ImageSize.values().length);
        try {
            BufferedImage image = ImageIO.read(inputStream);
            Path rootDir = Files.createDirectories(Paths.get(startDirectory, "images", "city", String.valueOf(id)));
            for (ImageSize size : ImageSize.values()) {
                if (size.getSize() > Math.max(image.getHeight(), image.getWidth()))
                    continue;
                Future<CityImage> imageFuture = executorService.submit(new CityImageSaver(size, image, rootDir, fileName, description, id));
                images.add(imageFuture);
            }
            image.flush();
            for (Future<CityImage> imageFuture : images) {
                try {
                    result.add(imageFuture.get(5, TimeUnit.SECONDS));
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    LOG.error("Failed to operate image {}, error : {}", imageFuture, e.getMessage());
                }
            }
            if (result.size() == 0)
                throw new RuntimeException("Failed to execute all types of images for " + id);

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
    public byte[] produceRawCountryImage(String code, ImageSize size, String fileName) {
        Path path = Files.exists(Paths.get(startDirectory, "images", "country", code, size.name(), fileName + ".jpg")) ? Paths.get(startDirectory, "images", "country", code, size.name(), fileName + ".jpg") : Paths.get(startDirectory, "images", "country", code, size.name(), fileName + ".png");
        return produceRawImage(path, path.getFileName().toString().substring(path.getFileName().toString().lastIndexOf(".") + 1));
    }

    @Override
    public byte[] produceRawCityImage(Long id, ImageSize size, String fileName) {
        Path path = Files.exists(Paths.get(startDirectory, "images", "city", id.toString(), size.name(), fileName + ".jpg")) ? Paths.get(startDirectory, "images", "city", id.toString(), size.name(), fileName + ".jpg") : Paths.get(startDirectory, "images", "city", id.toString(), size.name(), fileName + ".png");
        return produceRawImage(path, path.getFileName().toString().substring(path.getFileName().toString().lastIndexOf(".") + 1));
    }

    private byte[] produceRawImage(Path path, String fileType) {
        if (path == null || fileType == null)
            throw new IllegalArgumentException("Cannot resolve image");
        try {
            LOG.debug("Trying to read file {}", path);
            BufferedImage image = ImageIO.read(path.toFile());
            try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream()) {
                boolean writed = ImageIO.write(image, fileType, byteOutputStream);
                if (!writed)
                    throw new RuntimeException("Failed to process image");
                image.flush();
                return byteOutputStream.toByteArray();
            }
        } catch (IOException e) {
            LOG.error("Failed to load image {}, error: {}", path, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public List<CountryImage> save(String countryCode, InputStream inputStream, String fileName) {
        return save(countryCode, inputStream, fileName, null);
    }

    @Override
    @Transactional
    public List<CityImage> save(Long id, InputStream inputStream, String fileName) {
        return save(id, inputStream, fileName, null);
    }

    // todo rewrite
    private String constructUrl(Path path, String... args) {
        StringJoiner stringJoiner = new StringJoiner("/");
        path = Paths.get(startDirectory).relativize(path);
        for (int i = path.getNameCount() - 3; i < path.getNameCount(); ++i) {
            stringJoiner.add(path.getName(i).toString());
        }
        if (args != null) {
            for (String s : args) {
                stringJoiner.add(s);
            }
        }
        return stringJoiner.toString();
    }

    @SuppressWarnings("WeakerAccess")
    private abstract class ImageSaver<V> implements Callable<V> {

        protected final ImageSize size;
        protected final BufferedImage imageToSave;
        protected final Path rootPath;
        protected final String fileName;
        protected final String fileType;

        private ImageSaver(ImageSize size, BufferedImage imageToSave, Path rootPath, String fileName) {
            this.size = size;
            this.imageToSave = imageToSave;
            this.rootPath = rootPath;
            this.fileName = fileName;
            this.fileType = fileName.substring(fileName.lastIndexOf("."));
        }
    }

    private class CountryImageSaver extends ImageSaver<CountryImage> {

        private final String description;
        private final String countryCode;

        private CountryImageSaver(ImageSize size, BufferedImage imageToSave, Path rootPath, String description, String fileName, String countryCode) {
            super(size, imageToSave, rootPath, fileName);
            this.description = description;
            this.countryCode = countryCode;
        }

        @Override
        public CountryImage call() throws Exception {
            if (size == ImageSize.DEFAULT)
                size.setSize(Math.max(imageToSave.getHeight(), imageToSave.getWidth()));
            BufferedImage scaled = Scalr.resize(imageToSave, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, size.getSize());
            if (size == ImageSize.DEFAULT)
                size.setSize(0);
            CountryImage countryImage = new CountryImage();
            countryImage.setDescription(description);
            countryImage.setSize(size);
            countryImage.setUrl(constructUrl(rootPath, size.name(), fileName));
            countryImage.setCountry(countryRepository.getOne(countryCode));
            CountryImage save = countryImageRepository.save(countryImage);
            Path dir = rootPath.resolve(size.name());
            dir = Files.createDirectories(dir);
            Path file = dir.resolve(save.getId() + fileType);
            file = Files.createFile(file);
            boolean writed = ImageIO.write(scaled, fileType.substring(1), file.toFile());
            if (!writed)
                throw new RuntimeException("Failed to write file");
            save.setUrl(constructUrl(rootPath, size.name(), save.getId() + fileType));
            CountryImage entity = countryImageRepository.save(save);
            scaled.flush();
            return entity;
        }

        @Override
        public String toString() {
            return "CountryImageSaver{" +
                    "size=" + size +
                    ", description='" + description + '\'' +
                    ", countryCode='" + countryCode + '\'' +
                    '}';
        }
    }

    private class CityImageSaver extends ImageSaver<CityImage> {

        private final String description;
        private final Long id;

        private CityImageSaver(ImageSize size, BufferedImage imageToSave, Path rootPath, String fileName, String description, Long id) {
            super(size, imageToSave, rootPath, fileName);
            this.description = description;
            this.id = id;
        }

        @Override
        public CityImage call() throws Exception {
            if (size == ImageSize.DEFAULT)
                size.setSize(Math.max(imageToSave.getHeight(), imageToSave.getWidth()));
            BufferedImage scaled = Scalr.resize(imageToSave, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, size.getSize());
            if (size == ImageSize.DEFAULT)
                size.setSize(0);
            CityImage cityImage = new CityImage();
            cityImage.setDescription(description);
            cityImage.setSize(size);
            cityImage.setUrl(constructUrl(rootPath, size.name(), fileName));
            cityImage.setCity(cityRepository.getOne(id));
            CityImage save = cityImageRepository.save(cityImage);
            Path dir = rootPath.resolve(size.name());
            dir = Files.createDirectories(dir);
            Path file = dir.resolve(save.getId() + fileType);
            file = Files.createFile(file);
            boolean writed = ImageIO.write(scaled, fileType.substring(1), file.toFile());
            if (!writed)
                throw new RuntimeException("Failed to write file");
            save.setUrl(constructUrl(rootPath, size.name(), save.getId() + fileType));
            CityImage entity = cityImageRepository.save(save);
            scaled.flush();
            return entity;
        }

        @Override
        public String toString() {
            return "CityImageSaver{" +
                    "description='" + description + '\'' +
                    ", id=" + id +
                    '}';
        }
    }
}
