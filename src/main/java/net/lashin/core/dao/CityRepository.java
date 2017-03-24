package net.lashin.core.dao;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Continent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by lashi on 09.02.2017.
 */
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByName(String name);
    Page<City> findByName(String name, Pageable pageRequest);
    List<City> findByCountryCode(String code);
    Page<City> findByCountryCode(String code, Pageable pageable);
    List<City> findByCountryName(String name);
    List<City> findByCountry_Continent(Continent continent);
    List<City> findByCountry_Region(String region);

    @Query("select c from City c where c.population between ?1 and ?2")
    List<City> filterCities(int minPopulation, int maxPopulation);
}
