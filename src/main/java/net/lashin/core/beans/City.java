package net.lashin.core.beans;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 35)
    @NotNull
    private String name;
    @Size(max = 20)
    private String district;
    private Integer population;
    @ManyToOne
    @JoinColumn(name = "countryCode", nullable = false)
    private Country country;

    protected City() {
    }

    public City(Long id, String name, String district, int population, Country country){
        this(name, district, population,country);
        this.id = id;
    }

    public City(String name, String district, int population) {
        this.name = name;
        this.district = district;
        this.population = population;
    }

    public City(Long id, String name, String district, int population) {
        this(name, district, population);
        this.id = id;
    }

    public City(String name, String district, Integer population, Country country) {
        this(name, district, population);
        country.addCity(this);
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        if (getName() != null ? !getName().equals(city.getName()) : city.getName() != null) return false;
        if (getDistrict() != null ? !getDistrict().equals(city.getDistrict()) : city.getDistrict() != null)
            return false;
        return getCountry() != null ? getCountry().equals(city.getCountry()) : city.getCountry() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getDistrict() != null ? getDistrict().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        return result;
    }
}
