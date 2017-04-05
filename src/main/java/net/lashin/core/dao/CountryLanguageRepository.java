package net.lashin.core.dao;

import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.beans.CountryLanguageId;
import net.lashin.core.filters.LanguageFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Collectors;

public interface CountryLanguageRepository extends JpaRepository<CountryLanguage, CountryLanguageId> {
    List<CountryLanguage> findByCountry_Code(String countryCode);
    Page<CountryLanguage> findByCountry_Code(String countryCode, Pageable pageRequest);

    @Query("select distinct l.countryLanguageId.language from CountryLanguage l")
    List<String> findAllLanguageNames();

    @Query("select distinct l.countryLanguageId.language from CountryLanguage l")
    Page<String> findAllLanguageNames(Pageable pageRequest);

    @Query("select l from CountryLanguage l where l.countryLanguageId.language=?1")
    List<CountryLanguage> findByLanguage(String language);

    @Query("select l from CountryLanguage l where l.percentage between ?1 and ?2")
    List<CountryLanguage> filterLanguages(double minPercentage, double maxPercentage);

    default List<CountryLanguage> filterLanguages(LanguageFilter filter) {
        List<CountryLanguage> languages = filterLanguages(filter.getMinPercentage(), filter.getMaxPercentage());
        return languages.stream().filter(filter).collect(Collectors.toList());
    }
}
