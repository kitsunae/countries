package net.lashin.beans.hateoas.asm;

import net.lashin.beans.City;
import net.lashin.beans.hateoas.CityResource;
import net.lashin.controllers.CityController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

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
        res.setCountryCode(city.getCountryCode());
        res.setDistrict(city.getDistrict());
        res.setName(city.getName());
        res.setPopulation(city.getPopulation());
        return res;
    }
}
