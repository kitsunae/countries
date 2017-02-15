package net.lashin.beans;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by lashi on 24.01.2017.
 */
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    @Size(max = 35)
    @NotNull
    private String name;
    @Size(max = 3)
    @NotNull
    private String countryCode;
    @Size(max = 20)
    private String district;
    private Integer population;
    @ManyToOne
    @JoinColumn(name = "countryCode", insertable = false, updatable = false)//TODO check other abilities
    private Country country;

    protected City() {
    }

    public City(String name, String countryCode, String district, int population) {
        this.name = name;
        this.countryCode = countryCode;
        this.district = district;
        this.population = population;
    }

    public City(String name, String district, Integer population, Country country) {
        this.name = name;
        this.district = district;
        this.population = population;
        country.addCity(this);
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (!name.equals(city.getName())) return false;
        if (!countryCode.equals(city.getCountryCode())) return false;
        if (district != null ? !district.equals(city.getDistrict()) : city.getDistrict() != null) return false;
        return population != null ? population.equals(city.getPopulation()) : city.getPopulation() == null;

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + countryCode.hashCode();
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (population != null ? population.hashCode() : 0);
        return result;
    }
}
