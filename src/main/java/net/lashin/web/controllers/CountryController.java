package net.lashin.web.controllers;

import net.lashin.core.beans.Country;
import net.lashin.core.hateoas.CountryResource;
import net.lashin.core.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lashi on 09.02.2017.
 */
@RestController
@RequestMapping(value = "/country")
public class CountryController {

    private final CountryService service;
    private final ResourceAssemblerSupport<Country, CountryResource> assembler;

    @Autowired
    public CountryController(CountryService service, ResourceAssemblerSupport<Country, CountryResource> assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @RequestMapping(value = "/{countryCode}", method = RequestMethod.GET)
    public CountryResource getCountry(@PathVariable String countryCode){
        Country country = service.getCountryByCode(countryCode);
        return assembler.toResource(country);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<CountryResource> getAllCountries(){
        return service.getAllCountries()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/names", method = RequestMethod.GET)
    public List<String> getAllCountryNames(){
        return service.getAllCountryNames();
    }


    @RequestMapping(value = "/continent/{name}", method = RequestMethod.GET)
    public List<CountryResource> getCountriesByContinent(@PathVariable String name){
        return service.getCountriesByContinentName(name)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/language/{name}", method = RequestMethod.GET)
    public List<CountryResource> getCountriesWithSameLanguage(@PathVariable String name){
        return service.getCountriesByLanguage(name)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/capital/{name}", method = RequestMethod.GET)
    public List<CountryResource> getCountriesByCapital(@PathVariable String cityName){
        return service.getCountriesByCapital(cityName)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public CountryResource save(@RequestBody CountryResource country){
        return assembler.toResource(service.save(country.toCountry()));
    }

    @RequestMapping(value = "/{countryCode}", method = RequestMethod.PUT)
    public CountryResource edit(@RequestBody CountryResource country, @PathVariable String countryCode){
        return assembler.toResource(service.edit(country.toCountry(), countryCode));
    }

    @RequestMapping(value = "/{countryCode}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String countryCode){
        service.remove(countryCode);
    }
}
