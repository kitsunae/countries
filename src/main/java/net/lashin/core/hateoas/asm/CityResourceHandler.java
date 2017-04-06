package net.lashin.core.hateoas.asm;

import net.lashin.core.beans.City;
import net.lashin.core.beans.CityImage;
import net.lashin.core.hateoas.CityImageResource;
import net.lashin.core.hateoas.CityResource;
import net.lashin.web.controllers.CityController;
import net.lashin.web.controllers.CountryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CityResourceHandler extends ResourceHandler<City, CityResource> {

    private final ResourceHandler<CityImage, CityImageResource> imageHandler;

    @Autowired
    public CityResourceHandler(ResourceHandler<CityImage, CityImageResource> imageHandler) {
        super(CityController.class, CityResource.class);
        this.imageHandler = imageHandler;
    }

    @Override
    public CityResource toResource(City city) {
        if (city==null) return null;
        CityResource res = new CityResource();
        res.setIdentity(city.getId());
        res.setCountryCode(city.getCountry().getCode());
        res.setDistrict(city.getDistrict());
        res.setName(city.getName());
        res.setPopulation(city.getPopulation());
        res.setDescription(city.getDescription());
        Set<CityImage> images = city.getImages();
        List<CityImageResource> resourceImages = images.stream().map(imageHandler::toResource).collect(Collectors.toList());
        res.setImages(resourceImages);
        Link selfRel = linkTo(methodOn(CityController.class).getCity(city.getId())).withSelfRel();
        res.add(selfRel);
        Link cities = linkTo(methodOn(CityController.class).getAllCitiesOfCountry(city.getCountry().getCode(), null, null)).withRel("allCities");
        res.add(cities);
        Link country = linkTo(methodOn(CountryController.class).getCountry(city.getCountry().getCode())).withRel("country");
        res.add(country);
        return res;
    }

    @Override
    public City toEntity(CityResource resource) {
        Set<CityImage> images = resource.getImages().stream().map(imageHandler::toEntity).collect(Collectors.toSet());
        City city = new City(resource.getIdentity(), resource.getName(), resource.getDistrict(), resource.getPopulation());
        city.setImages(images);
        city.setDescription(resource.getDescription());
        return city;
    }
}
