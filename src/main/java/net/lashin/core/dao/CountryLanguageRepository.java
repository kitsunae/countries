package net.lashin.core.dao;

import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.beans.CountryLanguageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by lashi on 11.02.2017.
 */
public interface CountryLanguageRepository extends JpaRepository<CountryLanguage, CountryLanguageId> {
    List<CountryLanguage> findByCountry_Code(String countryCode);

    @Query("select distinct l.countryLanguageId.language from CountryLanguage l")
    List<String> findAllLanguageNames();
}
