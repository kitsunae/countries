package net.lashin.core.hateoas;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by lashi on 15.02.2017.
 */
public class CityResource extends ResourceSupport{

    private Long identity;
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

    public Long getIdentity() {
        return identity;
    }

    public void setIdentity(Long identity) {
        this.identity = identity;
    }

    public City toCity(){
        return new City(identity, name, district, population);
    }

    public City toCity(Country country){
        return new City(identity, name, district, population, country);
    }
}
