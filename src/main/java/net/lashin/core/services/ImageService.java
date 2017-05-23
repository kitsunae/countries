package net.lashin.core.services;


import net.lashin.core.beans.CountryImage;
import net.lashin.core.hateoas.ImageResourceType;

import java.io.InputStream;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public interface ImageService {

    List<String> getFilesPath();

    List<CountryImage> save(String countryCode, InputStream inputStream, ImageResourceType imageResourceType, String fileName);

    List<CountryImage> save(String countryCode, InputStream inputStream, ImageResourceType imageResourceType, String fileName, String description);
}
