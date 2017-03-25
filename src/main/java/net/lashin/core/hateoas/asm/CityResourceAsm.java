package net.lashin.core.hateoas.asm;

import net.lashin.core.beans.City;
import net.lashin.core.hateoas.CityResource;
import net.lashin.web.controllers.CityController;
import net.lashin.web.controllers.CountryController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CityResourceAsm extends ResourceAssemblerSupport<City, CityResource> {

    public CityResourceAsm(){
        super(CityController.class, CityResource.class);
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
        Link selfRel = linkTo(methodOn(CityController.class).getCity(city.getId())).withSelfRel();
        res.add(selfRel);
        Link cities = linkTo(methodOn(CityController.class).getAllCitiesOfCountry(city.getCountry().getCode())).withRel("allCities");
        res.add(cities);
        Link country = linkTo(methodOn(CountryController.class).getCountry(city.getCountry().getCode())).withRel("country");
        res.add(country);
        return res;
    }
}
