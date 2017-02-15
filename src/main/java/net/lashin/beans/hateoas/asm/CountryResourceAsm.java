package net.lashin.beans.hateoas.asm;

import net.lashin.beans.Country;
import net.lashin.beans.hateoas.CountryResource;
import net.lashin.controllers.CountryController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

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
        return resource;
    }
}
