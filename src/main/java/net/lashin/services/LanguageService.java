package net.lashin.services;

import net.lashin.beans.Country;
import net.lashin.beans.CountryLanguage;

import java.util.List;

/**
 * Created by lashi on 15.02.2017.
 */
public interface LanguageService {
    List<CountryLanguage> getAllLanguages();
    List<CountryLanguage> getLanguagesByCountryCode(String countryId);
    List<CountryLanguage> getLanguagesByOfficialty(boolean isOfficial);
    CountryLanguage getLanguageByNameAndCountry(String language, String countryCode);
    List<CountryLanguage> getLanguagesByPercentRate(Double percentage, boolean less);
    CountryLanguage save(CountryLanguage language);
}
