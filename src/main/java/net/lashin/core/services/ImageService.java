package net.lashin.core.services;


import net.lashin.core.beans.CityImage;
import net.lashin.core.beans.CountryImage;
import net.lashin.core.beans.ImageSize;

import java.io.InputStream;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public interface ImageService {

    List<CountryImage> save(String countryCode, InputStream inputStream, String fileName);

    List<CountryImage> save(String countryCode, InputStream inputStream, String fileName, String description);

    List<CityImage> save(Long id, InputStream inputStream, String fileName);

    List<CityImage> save(Long id, InputStream inputStream, String fileName, String description);

    byte[] produceRawCountryImage(String code, ImageSize size, String fileName);

    byte[] produceRawCityImage(Long id, ImageSize size, String fileName);
}
