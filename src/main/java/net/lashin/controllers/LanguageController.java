package net.lashin.controllers;

import net.lashin.beans.hateoas.CountryLanguageResource;
import net.lashin.beans.hateoas.CountryResource;
import net.lashin.beans.hateoas.asm.CountryLanguageResourceAsm;
import net.lashin.beans.hateoas.asm.CountryResourceAsm;
import net.lashin.services.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lashi on 15.02.2017.
 */
@RestController
@RequestMapping(value = "/language")
public class LanguageController {

    @Autowired
    private WorldService service;

    @RequestMapping(value = "/{countryId}", method = RequestMethod.GET)
    public List<CountryLanguageResource> getLanguagesByCountry(@PathVariable String countryId){
        return service.getLanguagesByCountryCode(countryId)
                .stream()
                .map(countryLanguage -> new CountryLanguageResourceAsm().toResource(countryLanguage))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "{countryId}/{languageName}", method = RequestMethod.GET)
    public CountryLanguageResource getLanguage(@PathVariable String languageName, @PathVariable("countryId") String countryId){
        return new CountryLanguageResourceAsm().toResource(service.getLanguageByNameAndCountry(languageName, countryId));
    }

    @RequestMapping(value = "/countries/{language}", method = RequestMethod.GET)
    public List<CountryResource> getCountriesWithSameLanguage(@PathVariable String language){
        return service.getCountriesByLanguage(language)
                .stream()
                .map(country -> new CountryResourceAsm().toResource(country))
                .collect(Collectors.toList());
    }
}
