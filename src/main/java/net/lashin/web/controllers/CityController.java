package net.lashin.web.controllers;

import net.lashin.core.beans.City;
import net.lashin.core.beans.hateoas.CityResource;
import net.lashin.core.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lashi on 09.02.2017.
 */
@RestController
@RequestMapping(value = "/city")
public class CityController {

    private final CityService service;
    private final ResourceAssemblerSupport<City, CityResource> assembler;

    @Autowired
    public CityController(CityService service, ResourceAssemblerSupport<City, CityResource> assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public CityResource getCity(@PathVariable long id){
        return assembler.toResource(service.getCityById(id));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<CityResource> getAllCities(){
        return service.getAllCities()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/capitals", method = RequestMethod.GET)
    public List<CityResource> getAllCapitals(){
        return service.getWorldCapitals()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public List<CityResource> getCitiesByName(@RequestParam(value = "name") String name){
        return service.getCitiesByName(name)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public CityResource saveCity(@RequestBody CityResource cityResource){
        return assembler.toResource(service.save(cityResource.toCity(), cityResource.getCountryCode()));
    }
}
