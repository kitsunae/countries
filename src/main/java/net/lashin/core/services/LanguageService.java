package net.lashin.core.services;

import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.filters.LanguageFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LanguageService {
    List<CountryLanguage> getAll();

    Page<CountryLanguage> getAll(Pageable pageRequest);

    List<CountryLanguage> getByCountryCode(String countryId);

    Page<CountryLanguage> getByCountryCode(String countryCode, Pageable pageRequest);

    List<CountryLanguage> getByCountryAndOfficialty(String countryCode, boolean isOfficial);

    Page<CountryLanguage> getByCountryAndOfficialty(String countryCode, boolean isOfficial, Pageable pageRequest);

    CountryLanguage getByNameAndCountry(String language, String countryCode);
    CountryLanguage save(CountryLanguage language);
    void remove(String language, String countryCode);
    List<String> getAllLanguageNames();
    Page<String> getAllLanguageNames(Pageable pageRequest);

    List<CountryLanguage> filter(LanguageFilter filter);

    Page<CountryLanguage> filter(LanguageFilter filter, Pageable pageRequest);
}
