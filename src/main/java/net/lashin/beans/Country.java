package net.lashin.beans;

import net.lashin.beans.converters.ContinentConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lashi on 24.01.2017.
 */
@Entity
public class Country {
    @Id
    @Size(max = 3)
    @NotNull
    private String code;
    @Size(max=52)
    private String name;
    @Convert(converter = ContinentConverter.class)
    private Continent continent;
    @Size(max=26)
    private String region;
    private Double surfaceArea;
    private Integer indepYear;
    private Integer population;
    private Double lifeExpectancy;
    private Double gnp;
    private Double gnpOld;
    @Size(max = 45)
    private String localName;
    @Size(max = 45)
    private String governmentForm;
    @Size(max = 60)
    private String headOfState;
    @Size(max = 2)
    private String code2;
    private Integer capital;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<City> cities = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CountryLanguage> languages = new ArrayList<>();

    protected Country() {
    }

    public Country(String code, String name, Continent continent, String region, Double surfaceArea, Integer indepYear, Integer population, Double lifeExpectancy, Double gnp, Double gnpOld, String localName, String governmentForm, String headOfState, String code2) {
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.surfaceArea = surfaceArea;
        this.indepYear = indepYear;
        this.population = population;
        this.lifeExpectancy = lifeExpectancy;
        this.gnp = gnp;
        this.gnpOld = gnpOld;
        this.localName = localName;
        this.governmentForm = governmentForm;
        this.headOfState = headOfState;
        this.code2 = code2;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<CountryLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(List<CountryLanguage> languages) {
        this.languages = languages;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Continent getContinent() {
        return this.continent;
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

    public Double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(Double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public Integer getIndepYear() {
        return indepYear;
    }

    public void setIndepYear(Integer indepYear) {
        this.indepYear = indepYear;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(Double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public Double getGnp() {
        return gnp;
    }

    public void setGnp(Double gnp) {
        this.gnp = gnp;
    }

    public Double getGnpOld() {
        return gnpOld;
    }

    public void setGnpOld(Double gnpOld) {
        this.gnpOld = gnpOld;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getGovernmentForm() {
        return governmentForm;
    }

    public void setGovernmentForm(String governmentForm) {
        this.governmentForm = governmentForm;
    }

    public String getHeadOfState() {
        return headOfState;
    }

    public void setHeadOfState(String headOfState) {
        this.headOfState = headOfState;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public City getCapital() {
        for (City city : cities){
            if (city.getId()==capital)
                return city;
        }
        return null;
    }

    public void setCapital(City capital) {
        addCity(capital);
        this.capital = capital.getId();
        capital.setCountry(this);
        capital.setCountryCode(this.code);
    }

    public void addCity(City city){
        if (!this.cities.contains(city)) {
            this.cities.add(city);
        }
        city.setCountry(this);
        city.setCountryCode(this.getCode());
    }

    public void addLanguage(CountryLanguage language){
        if (!this.languages.contains(language))
            this.languages.add(language);
        language.setCountryCode(this.getCode());
        language.setCountry(this);
    }

    public void addCities(List<City> cities){
        for (City city : cities){
            addCity(city);
        }
    }

    public void addLanguages(List<CountryLanguage> languages){
        for (CountryLanguage language : languages){
            addLanguage(language);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        return code.equals(country.getCode());

    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
