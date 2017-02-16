package net.lashin.controllers;

import net.lashin.beans.hateoas.CityResource;
import net.lashin.beans.hateoas.asm.CityResourceAsm;
import net.lashin.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lashi on 09.02.2017.
 */
@RestController
@RequestMapping(value = "/city")
public class CityController {

    @Autowired
    private CityService service;

    @RequestMapping(value="/{name}", method = RequestMethod.GET)
    public CityResource getCity(@PathVariable String name){
        return new CityResourceAsm().toResource(service.getCityByName(name));
    }

    @RequestMapping(value = "/country/{countryCode}", method = RequestMethod.GET)
    public List<CityResource> getCitiesOfCountry(@PathVariable String countryCode){
        return service.getCitiesByCountryCode(countryCode).stream().map(city -> new CityResourceAsm().toResource(city)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<CityResource> getAllCities(){
        return service.getAllCities().stream().map(city -> new CityResourceAsm().toResource(city)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/capitals", method = RequestMethod.GET)
    public List<CityResource> getAllCapitals(){
        return service.getWorldCapitals().stream().map(city -> new CityResourceAsm().toResource(city)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public CityResource saveCity(@RequestBody CityResource cityResource){
        return new CityResourceAsm().toResource(service.save(cityResource.toCity(), cityResource.getCountryCode()));
    }
}
