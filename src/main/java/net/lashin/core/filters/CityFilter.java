package net.lashin.core.filters;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Continent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Predicate;

public class CityFilter implements Predicate<City> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityFilter.class);

    private String district;
    private int minPopulation;
    private int maxPopulation;
    private Continent continent;
    private String region;
    private String country;

    public CityFilter() {
        minPopulation = 0;
        maxPopulation = Integer.MAX_VALUE;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getMinPopulation() {
        return minPopulation;
    }

    public void setMinPopulation(int minPopulation) {
        this.minPopulation = minPopulation;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean test(City city) {
        LOGGER.trace("Filtering city {}", city);
        return (continent == null || city.getCountry().getGeography().getContinent() == continent) && (district == null || district.equals(city.getDistrict())) &&
                (region == null || region.equals(city.getCountry().getGeography().getRegion())) && (country == null || country.equals(city.getCountry().getCode()));
    }

    @Override
    public String toString() {
        return "CityFilter{" +
                "district='" + district + '\'' +
                ", minPopulation=" + minPopulation +
                ", maxPopulation=" + maxPopulation +
                ", continent=" + continent +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
