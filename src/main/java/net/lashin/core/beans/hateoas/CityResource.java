package net.lashin.core.beans.hateoas;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
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

    public City toCity(){
        return new City(name, district, population);
    }

    public City toCity(Country country){
        return new City(name, district, population, country);
    }
}
