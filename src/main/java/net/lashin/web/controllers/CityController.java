package net.lashin.web.controllers;

import net.lashin.core.beans.City;
import net.lashin.core.filters.CityFilter;
import net.lashin.core.hateoas.CityResource;
import net.lashin.core.hateoas.asm.ResourceHandler;
import net.lashin.core.services.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/city")
public class CityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityController.class);
    private final CityService service;
    private final ResourceHandler<City, CityResource> assembler;

    @Autowired
    public CityController(CityService service, ResourceHandler<City, CityResource> assembler) {
        LOGGER.debug("Instantiating CityController");
        this.service = service;
        this.assembler = assembler;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public CityResource getCity(@PathVariable Long id){
        LOGGER.debug("Get city with id={}", id);
        return assembler.toResource(service.getById(id));
    }

    @RequestMapping(value = "/butch/all", method = RequestMethod.GET)
    public List<CityResource> getAllCities(){
        LOGGER.debug("Get all cities in batch");
        return service.getAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public PagedResources<CityResource> getAllCities(Pageable pageable, PagedResourcesAssembler<City> pagesAsm) {
        LOGGER.debug("Get {} cities of page #{}", pageable.getPageSize(), pageable.getPageNumber());
        Page<City> cities = service.getAll(pageable);
        return pagesAsm.toResource(cities, assembler);
    }

    @RequestMapping(value = "/butch/all/{countryCode}", method = RequestMethod.GET)
    public List<CityResource> getAllCitiesOfCountry(@PathVariable String countryCode) {
        LOGGER.debug("Get all cities of country {} in batch", countryCode);
        return service.getByCountryCode(countryCode)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/all/{countryCode}")
    public PagedResources<CityResource> getAllCitiesOfCountry(@PathVariable String countryCode, Pageable pageable, PagedResourcesAssembler<City> pagesAsm) {
        LOGGER.debug("Get {} cities of country {}, page #{}", pageable.getPageSize(), countryCode, pageable.getPageNumber());
        Page<City> cities = service.getByCountryCode(countryCode, pageable);
        return pagesAsm.toResource(cities, assembler);
    }

    @RequestMapping(value = "/butch/capitals", method = RequestMethod.GET)
    public List<CityResource> getAllCapitals(){
        LOGGER.debug("Get all world capitals in batch");
        return service.getWorldCapitals()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/capitals", method = RequestMethod.GET)
    public PagedResources<CityResource> getAllCapitals(Pageable pageable, PagedResourcesAssembler<City> pageAsm) {
        LOGGER.debug("Get {} capitals of page #{}", pageable.getPageSize(), pageable.getPageNumber());
        Page<City> capitals = service.getWorldCapitals(pageable);
        return pageAsm.toResource(capitals, assembler);
    }

    @RequestMapping(value = "/butch/find", method = RequestMethod.GET)
    public List<CityResource> getCitiesByName(@RequestParam(value = "name") String name){
        LOGGER.debug("Get cities with name {} in batch", name);
        return service.getByName(name)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public PagedResources<CityResource> getCitiesByName(@RequestParam(value = "name") String name, Pageable pageable, PagedResourcesAssembler<City> pageAsm) {
        LOGGER.debug("Get {} cities with name {} page#{}", pageable.getPageSize(), name, pageable.getPageNumber());
        Page<City> cities = service.getByName(name, pageable);
        return pageAsm.toResource(cities, assembler);
    }

    @RequestMapping(method = RequestMethod.POST)
    public CityResource saveCity(@RequestBody @Valid CityResource cityResource) {
        LOGGER.debug("Saving city {}", cityResource);
        return assembler.toResource(service.save(assembler.toEntity(cityResource), cityResource.getCountryCode()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public CityResource editCity(@RequestBody @Valid CityResource city, @PathVariable Long id) {
        LOGGER.debug("Editing city {} with id {}", city, id);
        return assembler.toResource(service.edit(assembler.toEntity(city), id, city.getCountryCode()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCity(@PathVariable Long id){
        LOGGER.debug("Removing city id={}", id);
        service.remove(id);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public PagedResources<CityResource> filterCities(@RequestBody CityFilter filter, Pageable pageable, PagedResourcesAssembler<City> pageAsm) {
        LOGGER.debug("Filtering cities with filter {}", filter);
        Page<City> cities = service.filter(filter, pageable);
        return pageAsm.toResource(cities, assembler);
    }

    @RequestMapping(value = "/butch/filter", method = RequestMethod.POST)
    public List<CityResource> filterCities(CityFilter filter) {
        LOGGER.debug("Filtering cities with filter {} in batch", filter);
        return service.filter(filter).stream().map(assembler::toResource).collect(Collectors.toList());
    }
}
