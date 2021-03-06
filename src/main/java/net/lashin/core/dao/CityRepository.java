package net.lashin.core.dao;

import net.lashin.core.beans.City;
import net.lashin.core.filters.CityFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Collectors;

public interface CityRepository extends JpaRepository<City, Long> {

    @EntityGraph(value = City.CITY_WITH_IMAGES, type = EntityGraph.EntityGraphType.LOAD)
    List<City> findByName(String name);

    @EntityGraph(value = City.CITY_WITH_IMAGES, type = EntityGraph.EntityGraphType.LOAD)
    Page<City> findByName(String name, Pageable pageRequest);

    @EntityGraph(value = City.CITY_WITH_IMAGES, type = EntityGraph.EntityGraphType.LOAD)
    List<City> findByCountryCode(String code);

    @EntityGraph(value = City.CITY_WITH_IMAGES, type = EntityGraph.EntityGraphType.LOAD)
    Page<City> findByCountryCode(String code, Pageable pageable);

    @Query("select c from City c where c.population between ?1 and ?2")
    List<City> filterCities(int minPopulation, int maxPopulation);

    default List<City> filterCities(CityFilter filter) {
        return filterCities(filter.getMinPopulation(), filter.getMaxPopulation())
                .stream()
                .filter(filter)
                .collect(Collectors.toList());
    }
}
