package net.lashin.dao;

import net.lashin.beans.City;
import net.lashin.beans.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lashi on 09.02.2017.
 */
public interface CityRepository extends JpaRepository<City, Integer> {
    City findByName(String name);
    List<City> findByCountry(Country country);
}
