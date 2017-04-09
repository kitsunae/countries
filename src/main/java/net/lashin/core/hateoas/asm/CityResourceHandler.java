package net.lashin.core.hateoas.asm;

import net.lashin.core.beans.City;
import net.lashin.core.beans.CityImage;
import net.lashin.core.beans.Image;
import net.lashin.core.hateoas.CityResource;
import net.lashin.core.hateoas.ImageResource;
import net.lashin.web.controllers.CityController;
import net.lashin.web.controllers.CountryController;
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
public class CityResourceHandler extends ResourceHandler<City, CityResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityResourceHandler.class);

    private final ResourceHandler<Image, ImageResource> imageHandler;

    @Autowired
    public CityResourceHandler(ResourceHandler<Image, ImageResource> imageHandler) {
        super(CityController.class, CityResource.class);
        this.imageHandler = imageHandler;
    }

    @Override
    public CityResource toResource(City city) {
        LOGGER.trace("Translating {} to resource", city);
        if (city==null) return null;
        CityResource res = new CityResource();
        res.setIdentity(city.getId());
        res.setCountryCode(city.getCountry().getCode());
        res.setDistrict(city.getDistrict());
        res.setName(city.getName());
        res.setPopulation(city.getPopulation());
        res.setDescription(city.getDescription());
        Set<CityImage> images = city.getImages();
        List<ImageResource> resourceImages = images.stream().map(imageHandler::toResource).collect(Collectors.toList());
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
        LOGGER.trace("Translating to entity resource {}", resource);
        Set<CityImage> images = resource.getImages()
                .stream()
                .map(ir -> {
                    Image image = imageHandler.toEntity(ir);
                    //// TODO: 08.04.2017 throw exception?
                    return image instanceof CityImage ? (CityImage) image : null;
                })
                .filter(image -> image != null)
                .collect(Collectors.toSet());
        City city = new City(resource.getIdentity(), resource.getName(), resource.getDistrict(), resource.getPopulation());
        city.setImages(images);
        city.setDescription(resource.getDescription());
        return city;
    }
}
