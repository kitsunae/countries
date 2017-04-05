package net.lashin.web.controllers;

import net.lashin.core.beans.City;
import net.lashin.core.hateoas.CityResource;
import net.lashin.core.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public CityResource getCity(@PathVariable Long id){
        return assembler.toResource(service.getById(id));
    }

    @RequestMapping(value = "/butch/all", method = RequestMethod.GET)
    public List<CityResource> getAllCities(){
        return service.getAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public PagedResources<CityResource> getAllCities(Pageable pageable, PagedResourcesAssembler<City> pagesAsm) {
        Page<City> cities = service.getAll(pageable);
        return pagesAsm.toResource(cities, assembler);
    }

    @RequestMapping(value = "/butch/all/{countryCode}", method = RequestMethod.GET)
    public List<CityResource> getAllCitiesOfCountry(@PathVariable String countryCode) {
        return service.getByCountryCode(countryCode)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/all/{countryCode}")
    public PagedResources<CityResource> getAllCitiesOfCountry(@PathVariable String countryCode, Pageable pageable, PagedResourcesAssembler<City> pagesAsm) {
        Page<City> cities = service.getByCountryCode(countryCode, pageable);
        return pagesAsm.toResource(cities, assembler);
    }

    @RequestMapping(value = "/butch/capitals", method = RequestMethod.GET)
    public List<CityResource> getAllCapitals(){
        return service.getWorldCapitals()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/capitals", method = RequestMethod.GET)
    public PagedResources<CityResource> getAllCapitals(Pageable pageable, PagedResourcesAssembler<City> pageAsm) {
        Page<City> capitals = service.getWorldCapitals(pageable);
        return pageAsm.toResource(capitals, assembler);
    }

    @RequestMapping(value = "/butch/find", method = RequestMethod.GET)
    public List<CityResource> getCitiesByName(@RequestParam(value = "name") String name){
        return service.getByName(name)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public PagedResources<CityResource> getCitiesByName(String name, Pageable pageable, PagedResourcesAssembler<City> pageAsm) {
        Page<City> cities = service.getByName(name, pageable);
        return pageAsm.toResource(cities, assembler);
    }

    @RequestMapping(method = RequestMethod.POST)
    public CityResource saveCity(@RequestBody CityResource cityResource){
        return assembler.toResource(service.save(cityResource.toCity(), cityResource.getCountryCode()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public CityResource editCity(@RequestBody CityResource city, @PathVariable Long id){
        return assembler.toResource(service.edit(city.toCity(), id, city.getCountryCode()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCity(@PathVariable Long id){
        service.remove(id);
    }
}
