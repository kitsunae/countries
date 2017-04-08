package net.lashin.core.beans;

import net.lashin.core.beans.converters.ContinentConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private double surfaceArea;
    private Integer indepYear;
    private int population;
    private Double lifeExpectancy;
    private Double gnp;
    private Double gnpOld;
    @Size(max = 45)
    private String localName;
    @Size(max = 45)
    @NotNull
    private String governmentForm;
    @Size(max = 60)
    private String headOfState;
    @Size(max = 2)
    @NotNull
    private String code2;
    private String description;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "capital", referencedColumnName = "id")
    private City capital;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<City> cities = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CountryLanguage> languages = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CountryImage> images = new HashSet<>();

    protected Country() {
    }

    public Country(String code, String name, Continent continent, String region, double surfaceArea, int population, String localName, String governmentForm, String code2) {
        this(code, name, continent, region, localName, governmentForm, code2);
        this.surfaceArea = surfaceArea;
        this.population = population;

    }

    public Country(String code, String name, Continent continent, String region, String localName, String governmentForm, String code2) {
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.localName = localName;
        this.governmentForm = governmentForm;
        this.code2 = code2;
    }

    public Country(String code, String name, Continent continent, String region, double surfaceArea, Integer indepYear, int population, Double lifeExpectancy, Double gnp, Double gnpOld, String localName, String governmentForm, String headOfState, String code2) {
        this(code, name, continent,region, surfaceArea,population, localName, governmentForm, code2);
        this.indepYear = indepYear;
        this.lifeExpectancy = lifeExpectancy;
        this.gnp = gnp;
        this.gnpOld = gnpOld;
        this.headOfState = headOfState;
    }

    public List<City> getCities() {
        return cities;
    }

    public List<CountryLanguage> getLanguages() {
        return languages;
    }

    public String getCode() {
        return code;
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

    public double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public Integer getIndepYear() {
        return indepYear;
    }

    public void setIndepYear(Integer indepYear) {
        this.indepYear = indepYear;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City getCapital() {
        return this.capital;
    }

    public void setCapital(City capital) {
        this.capital = capital;
    }

    public void addCity(City city){
        if (!this.cities.contains(city)) {
            this.cities.add(city);
        }
        city.setCountry(this);
    }

    @SuppressWarnings("WeakerAccess")
    public void addLanguage(CountryLanguage language){
        if (!this.languages.contains(language))
            this.languages.add(language);
        language.setCountryCode(this.getCode());
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

    public Set<CountryImage> getImages() {
        return images;
    }

    public void setImages(Set<CountryImage> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                '}';
    }


    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        if (Double.compare(country.getSurfaceArea(), getSurfaceArea()) != 0) return false;
        if (getPopulation() != country.getPopulation()) return false;
        if (getCode() != null ? !getCode().equals(country.getCode()) : country.getCode() != null) return false;
        if (getName() != null ? !getName().equals(country.getName()) : country.getName() != null) return false;
        if (getContinent() != country.getContinent()) return false;
        if (getRegion() != null ? !getRegion().equals(country.getRegion()) : country.getRegion() != null) return false;
        if (getIndepYear() != null ? !getIndepYear().equals(country.getIndepYear()) : country.getIndepYear() != null)
            return false;
        if (getLifeExpectancy() != null ? !getLifeExpectancy().equals(country.getLifeExpectancy()) : country.getLifeExpectancy() != null)
            return false;
        if (getGnp() != null ? !getGnp().equals(country.getGnp()) : country.getGnp() != null) return false;
        if (getGnpOld() != null ? !getGnpOld().equals(country.getGnpOld()) : country.getGnpOld() != null) return false;
        if (getLocalName() != null ? !getLocalName().equals(country.getLocalName()) : country.getLocalName() != null)
            return false;
        if (getGovernmentForm() != null ? !getGovernmentForm().equals(country.getGovernmentForm()) : country.getGovernmentForm() != null)
            return false;
        if (getHeadOfState() != null ? !getHeadOfState().equals(country.getHeadOfState()) : country.getHeadOfState() != null)
            return false;
        if (getCode2() != null ? !getCode2().equals(country.getCode2()) : country.getCode2() != null) return false;
        return getDescription() != null ? getDescription().equals(country.getDescription()) : country.getDescription() == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getCode() != null ? getCode().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getContinent() != null ? getContinent().hashCode() : 0);
        result = 31 * result + (getRegion() != null ? getRegion().hashCode() : 0);
        temp = Double.doubleToLongBits(getSurfaceArea());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getIndepYear() != null ? getIndepYear().hashCode() : 0);
        result = 31 * result + getPopulation();
        result = 31 * result + (getLifeExpectancy() != null ? getLifeExpectancy().hashCode() : 0);
        result = 31 * result + (getGnp() != null ? getGnp().hashCode() : 0);
        result = 31 * result + (getGnpOld() != null ? getGnpOld().hashCode() : 0);
        result = 31 * result + (getLocalName() != null ? getLocalName().hashCode() : 0);
        result = 31 * result + (getGovernmentForm() != null ? getGovernmentForm().hashCode() : 0);
        result = 31 * result + (getHeadOfState() != null ? getHeadOfState().hashCode() : 0);
        result = 31 * result + (getCode2() != null ? getCode2().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
