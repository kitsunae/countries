package net.lashin.core.beans;

import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Max;
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
    @NotNull
    private String name;
    @Max(999999)
    private Integer indepYear;
    @Size(max = 45)
    @NotNull
    private String localName;
    @Size(max = 2)
    @NotNull
    private String code2;
    private String description;
    @Embedded
    private CountryGeography geography;
    @Embedded
    private CountryDemography demography;
    @Embedded
    private CountryPolicy policy;
    @Embedded
    private CountryEconomy economy;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "capital", referencedColumnName = "id")
    @org.hibernate.annotations.Fetch(FetchMode.JOIN)
    private City capital;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<City> cities = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CountryLanguage> languages = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "country_image", joinColumns = @JoinColumn(name = "CountryCode"))
    private Set<CountryImage> images = new HashSet<>();

    protected Country() {
    }

    public Country(String code, String name, Continent continent, String region, double surfaceArea, int population, String localName, String governmentForm, String code2) {
        this(code, name, continent, region, localName, governmentForm, code2);
        this.geography.setSurfaceArea(surfaceArea);
        this.demography.setPopulation(population);

    }

    public Country(String code, String name, Continent continent, String region, String localName, String governmentForm, String code2) {
        this.demography = new CountryDemography();
        this.economy = new CountryEconomy();
        this.geography = new CountryGeography();
        this.policy = new CountryPolicy();
        this.code = code;
        this.name = name;
        this.geography.setContinent(continent);
        this.geography.setRegion(region);
        this.localName = localName;
        this.policy.setGovernmentForm(governmentForm);
        this.code2 = code2;
    }

    public Country(String code, String name, Continent continent, String region, double surfaceArea, Integer indepYear, int population, Double lifeExpectancy, Double gnp, Double gnpOld, String localName, String governmentForm, String headOfState, String code2) {
        this(code, name, continent,region, surfaceArea,population, localName, governmentForm, code2);
        this.indepYear = indepYear;
        this.demography.setLifeExpectancy(lifeExpectancy);
        this.economy.setGnp(gnp);
        this.economy.setGnpOld(gnpOld);
        this.policy.setHeadOfState(headOfState);
    }

    public Country(String code, String name, String localName, String code2, CountryGeography geography, CountryDemography demography, CountryPolicy policy, CountryEconomy economy) {
        this.code = code;
        this.name = name;
        this.localName = localName;
        this.code2 = code2;
        this.geography = geography;
        this.demography = demography;
        this.policy = policy;
        this.economy = economy;
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

    public Integer getIndepYear() {
        return indepYear;
    }

    public void setIndepYear(Integer indepYear) {
        this.indepYear = indepYear;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
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
        if (capital != null) {
            capital.setCountry(this);
        }
        this.capital = capital;
    }

    public CountryGeography getGeography() {
        return geography;
    }

    public void setGeography(CountryGeography geography) {
        this.geography = geography;
    }

    public CountryDemography getDemography() {
        return demography;
    }

    public void setDemography(CountryDemography demography) {
        this.demography = demography;
    }

    public CountryPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(CountryPolicy policy) {
        this.policy = policy;
    }

    public CountryEconomy getEconomy() {
        return economy;
    }

    public void setEconomy(CountryEconomy economy) {
        this.economy = economy;
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
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        //noinspection SimplifiableIfStatement
        if (getCode() != null ? !getCode().equals(country.getCode()) : country.getCode() != null) return false;
        return getName() != null ? getName().equals(country.getName()) : country.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = getCode() != null ? getCode().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
