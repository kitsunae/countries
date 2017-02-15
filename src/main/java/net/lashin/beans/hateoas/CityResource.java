package net.lashin.beans.hateoas;

import net.lashin.beans.City;
import net.lashin.beans.Country;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by lashi on 15.02.2017.
 */
public class CityResource extends ResourceSupport{

    private String name;
    private String countryCode;
    private String district;
    private Integer population;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public City toCity(Country country){
        City city = new City(name, district, population);
        country.addCity(city);
        return city;
    }
}
