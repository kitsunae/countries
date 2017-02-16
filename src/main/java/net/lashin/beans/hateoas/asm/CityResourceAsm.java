package net.lashin.beans.hateoas.asm;

import net.lashin.beans.City;
import net.lashin.beans.hateoas.CityResource;
import net.lashin.controllers.CityController;
import net.lashin.controllers.CountryController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by lashi on 15.02.2017.
 */
public class CityResourceAsm extends ResourceAssemblerSupport<City, CityResource> {

    public CityResourceAsm(){
        super(CityController.class, CityResource.class);
    }

    @Override
    public CityResource toResource(City city) {
        CityResource res = new CityResource();
        res.setCountryCode(city.getCountry().getCode());
        res.setDistrict(city.getDistrict());
        res.setName(city.getName());
        res.setPopulation(city.getPopulation());
        Link selfRel = linkTo(methodOn(CityController.class).getCity(city.getName())).withSelfRel();
        res.add(selfRel);
        Link cities = linkTo(methodOn(CityController.class).getCitiesOfCountry(city.getCountry().getCode())).withRel("allCities");
        res.add(cities);
        Link country = linkTo(methodOn(CountryController.class).getCountry(city.getCountry().getCode())).withRel("country");
        res.add(country);
        return res;
    }
}
