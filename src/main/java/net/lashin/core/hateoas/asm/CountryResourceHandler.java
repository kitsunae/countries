package net.lashin.core.hateoas.asm;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;
import net.lashin.core.beans.CountryImage;
import net.lashin.core.hateoas.CityResource;
import net.lashin.core.hateoas.CountryImageResource;
import net.lashin.core.hateoas.CountryResource;
import net.lashin.web.controllers.CityController;
import net.lashin.web.controllers.CountryController;
import net.lashin.web.controllers.LanguageController;
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

    private final ResourceHandler<City, CityResource> cityResourceHandler;
    private final ResourceHandler<CountryImage, CountryImageResource> imageResourceHandler;

    @Autowired
    public CountryResourceHandler(ResourceHandler<City, CityResource> cityResourceHandler, ResourceHandler<CountryImage, CountryImageResource> imageResourceHandler) {
        super(CountryController.class, CountryResource.class);
        this.cityResourceHandler = cityResourceHandler;
        this.imageResourceHandler = imageResourceHandler;
    }

    @Override
    public CountryResource toResource(Country country) {
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
        List<CountryImageResource> imageResources = country.getImages().stream().map(imageResourceHandler::toResource).collect(Collectors.toList());
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
        Country country = new Country(resource.getCode(), resource.getName(), Continent.fromString(resource.getContinent()),
                resource.getRegion(), resource.getSurfaceArea(), resource.getIndepYear(), resource.getPopulation(),
                resource.getLifeExpectancy(), resource.getGnp(), resource.getGnpOld(), resource.getLocalName(), resource.getGovernmentForm(),
                resource.getHeadOfState(), resource.getCode2());
        country.setCapital(cityResourceHandler.toEntity(resource.getCapital()));
        Set<CountryImage> collect = resource.getImages().stream().map(imageResourceHandler::toEntity).collect(Collectors.toSet());
        country.setImages(collect);
        country.setDescription(resource.getDescription());
        return country;
    }
}