package net.lashin.dao;

import net.lashin.beans.City;
import net.lashin.beans.Continent;
import net.lashin.beans.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lashi on 09.02.2017.
 */
public interface CountryRepository extends JpaRepository<Country, String> {
    Country findByName(String name);
    Country findByCapital(Integer capital);
    List<Country> findByContinent(Continent continent);

}
