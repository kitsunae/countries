package net.lashin.core.dao;

import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by lashi on 09.02.2017.
 */
public interface CountryRepository extends JpaRepository<Country, String> {
    List<Country> findByName(String name);
    List<Country> findByContinent(Continent continent);
    List<Country> findByCapitalId(Long id);

    @Query("select c from Country c " +
            "where (c.surfaceArea between ?1 and ?2) " +
            "and (c.indepYear between ?3 and ?4 or c.indepYear is null ) " +
            "and (c.population between ?5 and ?6) " +
            "and (c.lifeExpectancy between ?7 and ?8 or c.lifeExpectancy is null ) " +
            "and (c.gnp between ?9 and ?10 or c.gnp is null) " +
            "and (c.gnpOld between ?11 and ?12 or c.gnpOld is null)")
    List<Country> filterCountries(double minSurface, double maxSurface, int minYear, int maxYear, int minPopulation, int maxPopulation,
                                  double minLifeExpect, double maxLifeExpect, double minGnp, double maxGnp, double minGnpOld, double maxGnpOld);
}
