package net.lashin.controllers;

import net.lashin.beans.Continent;
import net.lashin.beans.Country;
import net.lashin.beans.hateoas.CountryResource;
import net.lashin.beans.hateoas.asm.CountryResourceAsm;
import net.lashin.services.CountryService;
import net.lashin.services.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lashi on 09.02.2017.
 */
@RestController
@RequestMapping(value = "/country")
public class CountryController {

    private final WorldService service;

    @Autowired
    public CountryController(WorldService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{countryCode}", method = RequestMethod.GET)
    public CountryResource getCountry(@PathVariable String countryCode){
        Country country = service.getCountryByCode(countryCode);
        return new CountryResourceAsm().toResource(country);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<CountryResource> getAllCountries(){
        return service.getAllCountries().stream().map(country -> new CountryResourceAsm().toResource(country)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/names", method = RequestMethod.GET)
    public List<String> getAllCountryNames(){
        return service.getAllCountryNames();
    }

    @RequestMapping (value = "/remove", method = RequestMethod.POST)
    public void remove(@RequestBody CountryResource countryResource){
        service.remove(countryResource.toCountry());
    }

    @RequestMapping(value = "/continent", method = RequestMethod.GET)
    public List<CountryResource> getCountriesByContinent(@RequestBody Continent continent){
        return service.getCountriesByContinent(continent)
                .stream()
                .map(country -> new CountryResourceAsm().toResource(country))
                .collect(Collectors.toList());
    }
}
