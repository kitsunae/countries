package net.lashin.web.controllers;

import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.hateoas.CountryLanguageResource;
import net.lashin.core.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lashi on 15.02.2017.
 */
@RestController
@RequestMapping(value = "/language")
public class LanguageController {

    private final LanguageService service;
    private final ResourceAssemblerSupport<CountryLanguage, CountryLanguageResource> assembler;

    @Autowired
    public LanguageController(LanguageService service, ResourceAssemblerSupport<CountryLanguage, CountryLanguageResource> assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @RequestMapping(value = "/country/{countryCode}", method = RequestMethod.GET)
    public List<CountryLanguageResource> getLanguagesByCountry(@PathVariable String countryCode){
        return service.getLanguagesByCountryCode(countryCode)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/official/{countryCode}", method = RequestMethod.GET)
    public List<CountryLanguageResource> getOfficialLanguagesOfCountry(@PathVariable String countryCode){
        return getLanguagesByCountryAndOfficialty(countryCode, true);
    }

    @RequestMapping(value = "/unofficial/{countryCode}", method = RequestMethod.GET)
    public List<CountryLanguageResource> getUnofficialLanguagesOfCountry(@PathVariable String countryCode){
        return getLanguagesByCountryAndOfficialty(countryCode, false);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<CountryLanguageResource> getAllLanguages(){
        return service.getAllLanguages()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CountryLanguageResource getLanguage(@RequestParam(value = "languageName") String languageName,
                                               @RequestParam(value = "countryCode") String countryCode){
        return assembler.toResource(service.getLanguageByNameAndCountry(languageName, countryCode));
    }

    @RequestMapping(method = RequestMethod.POST)
    public CountryLanguageResource save(@RequestBody CountryLanguageResource language){
        return assembler.toResource(service.save(language.toCountryLanguage()));
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void delete(@RequestParam(value = "languageName") String languageName,
                       @RequestParam(value = "countryCode") String countryCode){
        service.remove(languageName, countryCode);
    }

    //TODO editing language method (think about changing other languages in case of changing percentage)

    private List<CountryLanguageResource> getLanguagesByCountryAndOfficialty(String countryCode, boolean isOfficial){
        return service.getLanguagesByCountryAndOfficialty(countryCode, isOfficial)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }
}
