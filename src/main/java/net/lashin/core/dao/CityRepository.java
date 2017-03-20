package net.lashin.core.dao;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Continent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lashi on 09.02.2017.
 */
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByName(String name);
    List<City> findByCountryCode(String code);
    List<City> findByCountryName(String name);
    List<City> findByCountry_Continent(Continent continent);
    List<City> findByCountry_Region(String region);
}
