package net.lashin.core.dao;

import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lashi on 09.02.2017.
 */
public interface CountryRepository extends JpaRepository<Country, String> {
    List<Country> findByName(String name);
    List<Country> findByContinent(Continent continent);
    List<Country> findByCapitalId(Long id);
}
