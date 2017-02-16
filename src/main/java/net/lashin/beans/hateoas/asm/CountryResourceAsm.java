package net.lashin.beans.hateoas.asm;

import net.lashin.beans.Country;
import net.lashin.beans.hateoas.CountryResource;
import net.lashin.controllers.CityController;
import net.lashin.controllers.CountryController;
import net.lashin.controllers.LanguageController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by lashi on 15.02.2017.
 */
public class CountryResourceAsm extends ResourceAssemblerSupport<Country, CountryResource> {

    public CountryResourceAsm(){
        super(CountryController.class, CountryResource.class);
    }

    @Override
    public CountryResource toResource(Country country) {
        CountryResource resource = new CountryResource();
        resource.setName(country.getName());
        resource.setCode(country.getCode());
        resource.setCode2(country.getCode2());
        resource.setContinent(country.getContinent());
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
        resource.setCapital(new CityResourceAsm().toResource(country.getCapital()));
        Link self = linkTo(methodOn(CountryController.class).getCountry(country.getCode())).withSelfRel();
        resource.add(self);
        Link cities = linkTo(methodOn(CityController.class).getCitiesOfCountry(country.getCode())).withRel("cities");
        resource.add(cities);
        Link languages = linkTo(methodOn(LanguageController.class).getLanguagesByCountry(country.getCode())).withRel("languages");
        resource.add(languages);
        return resource;
    }


}
