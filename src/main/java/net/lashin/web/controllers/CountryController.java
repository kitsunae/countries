package net.lashin.web.controllers;

import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;
import net.lashin.core.beans.hateoas.CountryResource;
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


    @RequestMapping(value = "/continent", method = RequestMethod.GET)
    public List<CountryResource> getCountriesByContinent(@RequestBody Continent continent){
        return service.getCountriesByContinent(continent)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }
}
