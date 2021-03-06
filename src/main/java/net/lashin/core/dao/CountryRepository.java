package net.lashin.core.dao;

import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;
import net.lashin.core.filters.CountryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Collectors;

public interface CountryRepository extends JpaRepository<Country, String> {

    @EntityGraph(value = Country.COUNTRY_FULL, type = EntityGraph.EntityGraphType.LOAD)
    List<Country> findByName(String name);

    @EntityGraph(value = Country.COUNTRY_FULL, type = EntityGraph.EntityGraphType.LOAD)
    Page<Country> findByName(String name, Pageable pageable);

    @Query("select c from Country c left join fetch c.capital")
    List<Country> findWithCapitals();

    List<Country> findByGeographyContinent(Continent continent);

    Page<Country> findByGeographyContinent(Continent continent, Pageable pageable);

    @EntityGraph(value = Country.COUNTRY_WITH_IMAGES, type = EntityGraph.EntityGraphType.LOAD)
    List<Country> findByCapitalId(Long id);

    @EntityGraph(value = Country.COUNTRY_WITH_IMAGES, type = EntityGraph.EntityGraphType.LOAD)
    Page<Country> findByCapitalId(Long cityId, Pageable pageable);

    @EntityGraph(value = Country.COUNTRY_FULL, type = EntityGraph.EntityGraphType.LOAD)
    List<Country> findByCapitalName(String name);

    @EntityGraph(value = Country.COUNTRY_FULL, type = EntityGraph.EntityGraphType.LOAD)
    Page<Country> findByCapitalName(String capitalName, Pageable pageable);

    @EntityGraph(value = Country.COUNTRY_FULL, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT c from Country c where c.code=:code")
    Country findCountryWithImages(@Param("code") String code);

    @Query("select c.name from Country c")
    List<String> findAllCountryNames();

    @Query("select c.name from Country c")
    Page<String> findAllCountryNames(Pageable pageable);

    @Query("select c from Country c " +
            "where (c.geography.surfaceArea between ?1 and ?2) " +
            "and (c.indepYear between ?3 and ?4 or c.indepYear is null ) " +
            "and (c.demography.population between ?5 and ?6) " +
            "and (c.demography.lifeExpectancy between ?7 and ?8 or c.demography.lifeExpectancy is null ) " +
            "and (c.economy.gnp between ?9 and ?10 or c.economy.gnp is null) " +
            "and (c.economy.gnpOld between ?11 and ?12 or c.economy.gnpOld is null)")
    List<Country> filterCountries(double minSurface, double maxSurface, int minYear, int maxYear, int minPopulation, int maxPopulation,
                                  double minLifeExpect, double maxLifeExpect, double minGnp, double maxGnp, double minGnpOld, double maxGnpOld);

    default List<Country> filterCountries(CountryFilter filter) {
        List<Country> queryResults = filterCountries(filter.getMinSurfaceArea(), filter.getMaxSurfaceArea(),
                filter.getMinIndepYear(), filter.getMaxIndepYear(),
                filter.getMinPopulation(), filter.getMaxPopulation(),
                filter.getMinLifeExpectancy(), filter.getMaxLifeExpectancy(),
                filter.getMinGnp(), filter.getMaxGnp(),
                filter.getMinGnpOld(), filter.getMaxGnpOld());
        return queryResults.stream().filter(filter).collect(Collectors.toList());
    }
}
