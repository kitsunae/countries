package net.lashin.core.dao;

import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;
import net.lashin.core.filters.CountryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Collectors;

public interface CountryRepository extends JpaRepository<Country, String> {
    List<Country> findByName(String name);
    Page<Country> findByName(String name, Pageable pageable);
    List<Country> findByContinent(Continent continent);
    Page<Country> findByContinent(Continent continent, Pageable pageable);
    List<Country> findByCapitalId(Long id);
    Page<Country> findByCapitalId(Long cityId, Pageable pageable);
    List<Country> findByCapitalName(String name);
    Page<Country> findByCapitalName(String capitalName, Pageable pageable);

    @Query("select c.name from Country c")
    List<String> findAllCountryNames();

    @Query("select c.name from Country c")
    Page<String> findAllCountryNames(Pageable pageable);

    @Query("select c from Country c " +
            "where (c.surfaceArea between ?1 and ?2) " +
            "and (c.indepYear between ?3 and ?4 or c.indepYear is null ) " +
            "and (c.population between ?5 and ?6) " +
            "and (c.lifeExpectancy between ?7 and ?8 or c.lifeExpectancy is null ) " +
            "and (c.gnp between ?9 and ?10 or c.gnp is null) " +
            "and (c.gnpOld between ?11 and ?12 or c.gnpOld is null)")
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
