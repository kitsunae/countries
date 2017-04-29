package net.lashin.core.hateoas;

import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class CityResource extends ResourceSupport{

    private Long identity;
    @NotNull
    @Size(max = 35)
    private String name;
    @NotNull
    private String countryCode;
    @NotNull
    @Size(max = 20)
    private String district;
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Integer population;
    private String description;
    private List<ImageResource> images = new ArrayList<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ImageResource> getImages() {
        return images;
    }

    public void setImages(List<ImageResource> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "CityResource{" +
                "identity=" + identity +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", district='" + district + '\'' +
                ", population=" + population +
                ", description='" + description + '\'' +
                '}';
    }
}
