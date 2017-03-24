package net.lashin.core.services;

import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.filters.LanguageFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by lashi on 15.02.2017.
 */
public interface LanguageService {
    List<CountryLanguage> getAllLanguages();
    Page<CountryLanguage> getAllLanguages(Pageable pageRequest);
    List<CountryLanguage> getLanguagesByCountryCode(String countryId);
    Page<CountryLanguage> getLanguagesByCountryCode(String countryCode, Pageable pageRequest);
    List<CountryLanguage> getLanguagesByCountryAndOfficialty(String countryCode, boolean isOfficial);
    Page<CountryLanguage> getLanguagesByCountryAndOfficialty(String countryCode, boolean isOfficial, Pageable pageRequest);
    CountryLanguage getLanguageByNameAndCountry(String language, String countryCode);
    CountryLanguage save(CountryLanguage language);
    void remove(String language, String countryCode);
    List<String> getAllLanguageNames();
    Page<String> getAllLanguageNames(Pageable pageRequest);
    List<CountryLanguage> filterLanguages(LanguageFilter filter);
    Page<CountryLanguage> filterLanguages(LanguageFilter filter, Pageable pageRequest);
}
