package net.lashin.core.hateoas;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class CountryResource extends ResourceSupport {

    private String code;
    private String name;
    private Integer indepYear;
    private String localName;
    private String code2;
    private CityResource capital;
    private Geography geography;
    private Demography demography;
    private Economy economy;
    private Policy policy;
    private String description;
    private List<ImageResource> images;


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

    public CityResource getCapital() {
        return capital;
    }

    public void setCapital(CityResource capital) {
        this.capital = capital;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Geography getGeography() {
        return geography;
    }

    public void setGeography(Geography geography) {
        this.geography = geography;
    }

    public Demography getDemography() {
        return demography;
    }

    public void setDemography(Demography demography) {
        this.demography = demography;
    }

    public Economy getEconomy() {
        return economy;
    }

    public void setEconomy(Economy economy) {
        this.economy = economy;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public List<ImageResource> getImages() {
        return images;
    }

    public void setImages(List<ImageResource> images) {
        this.images = images;
    }


    public static class Geography {

        private String continent;
        private Double surfaceArea;
        private String region;

        public String getContinent() {
            return continent;
        }

        public void setContinent(String continent) {
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
    }

    public static class Demography {

        private Integer population;
        private Double lifeExpectancy;

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

    }

    public static class Economy {

        private Double gnp;
        private Double gnpOld;

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
    }

    public static class Policy {

        private String governmentForm;
        private String headOfState;

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
    }
}
