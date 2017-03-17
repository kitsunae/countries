package net.lashin.core.services;

import net.lashin.core.beans.CountryLanguage;

import java.util.List;

/**
 * Created by lashi on 15.02.2017.
 */
public interface LanguageService {
    List<CountryLanguage> getAllLanguages();
    List<CountryLanguage> getLanguagesByCountryCode(String countryId);
    List<CountryLanguage> getLanguagesByCountryAndOfficialty(String countryCode, boolean isOfficial);
    CountryLanguage getLanguageByNameAndCountry(String language, String countryCode);
    CountryLanguage save(CountryLanguage language);
    void remove(String language, String countryCode);
}
