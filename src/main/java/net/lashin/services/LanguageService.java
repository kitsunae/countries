package net.lashin.services;

import net.lashin.beans.Country;
import net.lashin.beans.CountryLanguage;
import net.lashin.beans.CountryLanguageId;

import java.util.List;

/**
 * Created by lashi on 15.02.2017.
 */
public interface LanguageService {
    List<CountryLanguage> getAllLanguages();
    List<CountryLanguage> getLanguagesByCountry(Country country);
    List<CountryLanguage> getLanguagesByOfficialty(boolean isOfficial);
    CountryLanguage getLanguageByNameAndCountry(String language, Country country);
    List<CountryLanguage> getLanguagesByPercentRate(Double percentage, boolean less);
    CountryLanguage save(CountryLanguage language);
}
