package net.lashin.controllers;

import net.lashin.beans.hateoas.CountryLanguageResource;
import net.lashin.beans.hateoas.CountryResource;
import net.lashin.beans.hateoas.asm.CountryLanguageResourceAsm;
import net.lashin.services.LanguageService;
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
    private LanguageService service;

    @RequestMapping(value = "/{countryId}", method = RequestMethod.GET)
    public List<CountryLanguageResource> getLanguagesByCountry(@PathVariable String countryId){
        return service.getLanguagesByCountry(countryId)
                .stream()
                .map(countryLanguage -> new CountryLanguageResourceAsm().toResource(countryLanguage))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "{countryId}/{languageName}", method = RequestMethod.GET)
    public CountryLanguageResource getLanguage(@PathVariable String languageName, @PathVariable("countryId") String countryId){
        return new CountryLanguageResourceAsm().toResource(service.getLanguageByNameAndCountry(languageName, countryId));
    }
}
