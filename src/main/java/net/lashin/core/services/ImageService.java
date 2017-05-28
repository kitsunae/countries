package net.lashin.core.services;


import net.lashin.core.beans.CityImage;
import net.lashin.core.beans.CountryImage;

import java.io.InputStream;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public interface ImageService {

    List<CountryImage> save(String countryCode, InputStream inputStream, String fileName);

    List<CountryImage> save(String countryCode, InputStream inputStream, String fileName, String description);

    List<CityImage> save(Long id, InputStream inputStream, String fileName);

    List<CityImage> save(Long id, InputStream inputStream, String fileName, String description);
}
