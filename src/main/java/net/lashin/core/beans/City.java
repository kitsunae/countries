package net.lashin.core.beans;


import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = City.CITY_WITH_IMAGES, attributeNodes = {@NamedAttributeNode("images")}),
})
public class City {

    public static final String CITY_WITH_IMAGES = "City.images";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 35)
    @NotNull
    private String name;
    @Size(max = 20)
    @NotNull
    private String district;
    @Max(Integer.MAX_VALUE)
    @Min(0)
    private int population;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "countryCode", nullable = false)
    @org.hibernate.annotations.Fetch(FetchMode.JOIN)
    private Country country;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "city_image", joinColumns = @JoinColumn(name = "city_id"))
    private Set<CityImage> images = new HashSet<>();

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
        this.country = country;
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

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CityImage> getImages() {
        return images;
    }

    public void setImages(Set<CityImage> images) {
        this.images = images;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (getName() != null ? !getName().equals(city.getName()) : city.getName() != null) return false;
        if (getDistrict() != null ? !getDistrict().equals(city.getDistrict()) : city.getDistrict() != null)
            return false;
        if (getPopulation() != city.getPopulation())
            return false;
        if (getDescription() != null ? !getDescription().equals(city.getDescription()) : city.getDescription() != null)
            return false;
        return getCountry() != null ? getCountry().equals(city.getCountry()) : city.getCountry() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getDistrict() != null ? getDistrict().hashCode() : 0);
        result = 31 * result + getPopulation();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", district='" + district + '\'' +
                ", population=" + population +
                ", country=" + country +
                '}';
    }
}
