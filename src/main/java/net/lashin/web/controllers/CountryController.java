package net.lashin.web.controllers;

import net.lashin.core.beans.Country;
import net.lashin.core.filters.CountryFilter;
import net.lashin.core.hateoas.CountryResource;
import net.lashin.core.hateoas.asm.ResourceHandler;
import net.lashin.core.services.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/country")
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);
    private final CountryService service;
    private final ResourceHandler<Country, CountryResource> assembler;

    @Autowired
    public CountryController(CountryService service, ResourceHandler<Country, CountryResource> assembler) {
        LOGGER.debug("Instantiating country controller");
        this.service = service;
        this.assembler = assembler;
    }

    @RequestMapping(value = "/{countryCode}", method = RequestMethod.GET)
    public CountryResource getCountry(@PathVariable String countryCode){
        LOGGER.debug("Get country {}", countryCode);
        Country country = service.getByCode(countryCode);
        return assembler.toResource(country);
    }

    @RequestMapping(value = "/butch/all", method = RequestMethod.GET)
    public List<CountryResource> getAllCountries(){
        LOGGER.debug("Get all countries in batch");
        return service.getAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public PagedResources<CountryResource> getAllCountries(Pageable pageable, PagedResourcesAssembler<Country> pagedResourcesAssembler) {
        LOGGER.debug("Get {} countries from page {}", pageable.getPageSize(), pageable.getPageNumber());
        Page<Country> page = service.getAll(pageable);
        return pagedResourcesAssembler.toResource(page, assembler);
    }

    @RequestMapping(value = "/butch/names", method = RequestMethod.GET)
    public List<String> getAllCountryNames(){
        LOGGER.debug("Get all country names");
        return service.getAllCountryNames();
    }

    @RequestMapping(value = "/names", method = RequestMethod.GET)
    public PagedResources<Resource<String>> getAllCountryNames(Pageable pageable, PagedResourcesAssembler<String> pagedResourcesAssembler) {
        LOGGER.debug("Get {} country names page#{}", pageable.getPageSize(), pageable.getPageNumber());
        return pagedResourcesAssembler.toResource(service.getAllCountryNames(pageable));
    }

    @RequestMapping(value = "/butch/continent/{name}", method = RequestMethod.GET)
    public List<CountryResource> getCountriesByContinent(@PathVariable String name){
        LOGGER.debug("Get all countries of continent {}", name);
        return service.getByContinentName(name)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/continent/{name}", method = RequestMethod.GET)
    public PagedResources<CountryResource> getCountriesByContinent(@PathVariable String name, Pageable pageable, PagedResourcesAssembler<Country> pagedResourcesAssembler) {
        LOGGER.debug("Get {} countries of continent {} page#{}", pageable.getPageSize(), name, pageable.getPageNumber());
        return pagedResourcesAssembler.toResource(service.getByContinentName(name, pageable), assembler);
    }

    @RequestMapping(value = "/butch/language/{name}", method = RequestMethod.GET)
    public List<CountryResource> getCountriesWithSameLanguage(@PathVariable String name){
        LOGGER.debug("Get all countries with language {}", name);
        return service.getByLanguage(name)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/language/{name}", method = RequestMethod.GET)
    public PagedResources<CountryResource> getCountriesWithSameLanguage(@PathVariable String name, Pageable pageable, PagedResourcesAssembler<Country> pagedResourcesAssembler) {
        LOGGER.debug("Get {} countries with language {} page#{}", pageable.getPageSize(), name, pageable.getPageNumber());
        return pagedResourcesAssembler.toResource(service.getByLanguage(name, pageable), assembler);
    }

    @RequestMapping(value = "/butch/capital/{cityName}", method = RequestMethod.GET)
    public List<CountryResource> getCountriesByCapital(@PathVariable String cityName){
        LOGGER.debug("Get all countries with capital named {}", cityName);
        return service.getByCapital(cityName)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/capital/{cityName}", method = RequestMethod.GET)
    public PagedResources<CountryResource> getCountriesByCapital(@PathVariable String cityName, Pageable pageable, PagedResourcesAssembler<Country> pagedResourcesAssembler) {
        LOGGER.debug("Get {} countries with capital named {} page#{}", pageable.getPageSize(), cityName, pageable.getPageNumber());
        return pagedResourcesAssembler.toResource(service.getByCapital(cityName, pageable), assembler);
    }

    @RequestMapping(method = RequestMethod.POST)
    public CountryResource save(@RequestBody CountryResource country){
        LOGGER.debug("Saving country {}", country);
        return assembler.toResource(service.save(assembler.toEntity(country)));
    }

    @RequestMapping(value = "/{countryCode}", method = RequestMethod.PUT)
    public CountryResource edit(@RequestBody CountryResource country, @PathVariable String countryCode){
        LOGGER.debug("Editing country {} with code {}", country, countryCode);
        return assembler.toResource(service.edit(assembler.toEntity(country), countryCode));
    }

    @RequestMapping(value = "/{countryCode}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String countryCode){
        LOGGER.debug("Deleting country {}", countryCode);
        service.remove(countryCode);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public PagedResources<CountryResource> filterCountries(@RequestBody CountryFilter filter, Pageable pageable, PagedResourcesAssembler<Country> pageAsm) {
        LOGGER.debug("Filter countries with filter {}, get {} of page#{}", filter, pageable.getPageSize(), pageable.getPageNumber());
        Page<Country> countries = service.filter(filter, pageable);
        return pageAsm.toResource(countries, assembler);
    }

    @RequestMapping(value = "/butch/filter", method = RequestMethod.POST)
    public List<CountryResource> filterCountries(@RequestBody CountryFilter filter) {
        LOGGER.debug("Filter all countries with filter {}", filter);
        return service.filter(filter).stream().map(assembler::toResource).collect(Collectors.toList());
    }
}
