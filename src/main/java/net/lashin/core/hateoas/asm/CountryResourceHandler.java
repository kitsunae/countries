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
        resource.setName(country.getName());
        resource.setCode(country.getCode());
        resource.setCode2(country.getCode2());
        resource.setContinent(country.getContinent().toString());
        resource.setRegion(country.getRegion());
        resource.setSurfaceArea(country.getSurfaceArea());
        resource.setIndepYear(country.getIndepYear());
        resource.setPopulation(country.getPopulation());
        resource.setLifeExpectancy(country.getLifeExpectancy());
        resource.setGnp(country.getGnp());
        resource.setGnpOld(country.getGnpOld());
        resource.setLocalName(country.getLocalName());
        resource.setGovernmentForm(country.getGovernmentForm());
        resource.setHeadOfState(country.getHeadOfState());
        resource.setCapital(cityResourceHandler.toResource(country.getCapital()));
        List<ImageResource> imageResources = country.getImages().stream().map(imageResourceHandler::toResource).collect(Collectors.toList());
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
        Country country = new Country(resource.getCode(), resource.getName(), Continent.fromString(resource.getContinent()),
                resource.getRegion(), resource.getSurfaceArea(), resource.getIndepYear(), resource.getPopulation(),
                resource.getLifeExpectancy(), resource.getGnp(), resource.getGnpOld(), resource.getLocalName(), resource.getGovernmentForm(),
                resource.getHeadOfState(), resource.getCode2());
        country.setCapital(cityResourceHandler.toEntity(resource.getCapital()));
        Set<CountryImage> collect = resource.getImages().stream()
                .map(ir -> {
                    Image image = imageResourceHandler.toEntity(ir);
                    //// TODO: 08.04.2017 throw exception?
                    return image instanceof CountryImage ? (CountryImage) image : null;
                })
                .filter(image -> image != null)
                .collect(Collectors.toSet());
        country.setImages(collect);
        country.setDescription(resource.getDescription());
        return country;
    }
}
