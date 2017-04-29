package net.lashin.core.hateoas.asm;

import net.lashin.core.beans.*;
import net.lashin.core.hateoas.CityResource;
import net.lashin.core.hateoas.CountryResource;
import net.lashin.core.hateoas.ImageResource;
import net.lashin.web.controllers.CityController;
import net.lashin.web.controllers.CountryController;
import net.lashin.web.controllers.LanguageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CountryResourceHandler extends ResourceHandler<Country, CountryResource> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryResourceHandler.class);

    private final ResourceHandler<City, CityResource> cityResourceHandler;
    private final ResourceHandler<Image, ImageResource> imageResourceHandler;

    @Autowired
    public CountryResourceHandler(ResourceHandler<City, CityResource> cityResourceHandler, ResourceHandler<Image, ImageResource> imageResourceHandler) {
        super(CountryController.class, CountryResource.class);
        this.cityResourceHandler = cityResourceHandler;
        this.imageResourceHandler = imageResourceHandler;
    }

    @Override
    public CountryResource toResource(Country country) {
        LOGGER.trace("Translating {} to resource", country);
        CountryResource resource = new CountryResource();
        CountryResource.Demography demography = new CountryResource.Demography();
        demography.setLifeExpectancy(country.getDemography().getLifeExpectancy());
        demography.setPopulation(country.getDemography().getPopulation());
        resource.setDemography(demography);
        CountryResource.Geography geography = new CountryResource.Geography();
        geography.setContinent(country.getGeography().getContinent().toString());
        geography.setRegion(country.getGeography().getRegion());
        geography.setSurfaceArea(country.getGeography().getSurfaceArea());
        resource.setGeography(geography);
        CountryResource.Economy economy = new CountryResource.Economy();
        economy.setGnp(country.getEconomy().getGnp());
        economy.setGnpOld(country.getEconomy().getGnpOld());
        resource.setEconomy(economy);
        CountryResource.Policy policy = new CountryResource.Policy();
        policy.setGovernmentForm(country.getPolicy().getGovernmentForm());
        policy.setHeadOfState(country.getPolicy().getHeadOfState());
        resource.setPolicy(policy);
        resource.setName(country.getName());
        resource.setCode(country.getCode());
        resource.setCode2(country.getCode2());
        resource.setIndepYear(country.getIndepYear());
        resource.setLocalName(country.getLocalName());
        resource.setCapital(cityResourceHandler.toResource(country.getCapital()));
        Set<CountryImage> images = country.getImages();
        List<ImageResource> imageResources = images instanceof HashSet ? images.stream().map(imageResourceHandler::toResource).collect(Collectors.toList()) : null;
        resource.setImages(imageResources);
        resource.setDescription(country.getDescription());
        Link self = linkTo(methodOn(CountryController.class).getCountry(country.getCode())).withSelfRel();
        resource.add(self);
        Link cities = linkTo(methodOn(CityController.class).getAllCitiesOfCountry(country.getCode(), null, null)).withRel("cities");
        resource.add(cities);
        Link languages = linkTo(methodOn(LanguageController.class).getLanguagesByCountry(country.getCode(), null, null)).withRel("languages");
        resource.add(languages);
        return resource;
    }

    @Override
    public Country toEntity(CountryResource resource) {
        LOGGER.trace("Translating to entity resource {}", resource);
        CountryResource.Geography resGeography = resource.getGeography();
        CountryResource.Policy resPolicy = resource.getPolicy();
        CountryResource.Demography resDemography = resource.getDemography();
        CountryResource.Economy resEconomy = resource.getEconomy();
        CountryGeography geography = new CountryGeography(Continent.fromString(resGeography.getContinent()), resGeography.getRegion(), resGeography.getSurfaceArea());
        CountryPolicy policy = new CountryPolicy(resPolicy.getGovernmentForm(), resPolicy.getHeadOfState());
        CountryDemography demography = new CountryDemography(resDemography.getPopulation(), resDemography.getLifeExpectancy());
        CountryEconomy economy = new CountryEconomy(resEconomy.getGnp(), resEconomy.getGnpOld());
        Country country = new Country(resource.getCode(), resource.getName(), resource.getLocalName(), resource.getCode2(), geography, demography, policy, economy);
        country.setCapital(cityResourceHandler.toEntity(resource.getCapital()));
        Set<CountryImage> collect = resource.getImages().stream()
                .map(ir -> {
                    Image image = imageResourceHandler.toEntity(ir);
                    return image instanceof CountryImage ? (CountryImage) image : null;
                })
                .filter(image -> image != null)
                .collect(Collectors.toSet());
        country.setImages(collect);
        country.setDescription(resource.getDescription());
        return country;
    }
}
