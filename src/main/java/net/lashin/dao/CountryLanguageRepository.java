package net.lashin.dao;

import net.lashin.beans.Country;
import net.lashin.beans.CountryLanguage;
import net.lashin.beans.CountryLanguageId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lashi on 11.02.2017.
 */
public interface CountryLanguageRepository extends JpaRepository<CountryLanguage, CountryLanguageId> {
    List<CountryLanguage> findByCountry(Country country);
    List<CountryLanguage> findByIsOfficial(boolean isOfficial);
}
