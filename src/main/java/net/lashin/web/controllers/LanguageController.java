package net.lashin.web.controllers;

import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.filters.LanguageFilter;
import net.lashin.core.hateoas.CountryLanguageResource;
import net.lashin.core.hateoas.asm.ResourceHandler;
import net.lashin.core.services.LanguageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/language")
public class LanguageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageController.class);
    private final LanguageService service;
    private final ResourceHandler<CountryLanguage, CountryLanguageResource> assembler;

    @Autowired
    public LanguageController(LanguageService service, ResourceHandler<CountryLanguage, CountryLanguageResource> assembler) {
        LOGGER.debug("Instantiating LanguageController");
        this.service = service;
        this.assembler = assembler;
    }

    @RequestMapping(value = "/butch/country/{countryCode}", method = RequestMethod.GET)
    public List<CountryLanguageResource> getLanguagesByCountry(@PathVariable String countryCode){
        LOGGER.debug("Get all languages by country {}", countryCode);
        return service.getByCountryCode(countryCode)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/country/{countryCode}", method = RequestMethod.GET)
    public PagedResources<CountryLanguageResource> getLanguagesByCountry(@PathVariable String countryCode, Pageable pageable, PagedResourcesAssembler<CountryLanguage> pageAsm) {
        LOGGER.debug("Get {} languages of country {} page#{}", pageable.getPageSize(), countryCode, pageable.getPageNumber());
        Page<CountryLanguage> languages = service.getByCountryCode(countryCode, pageable);
        return pageAsm.toResource(languages, assembler);
    }

    @RequestMapping(value = "/butch/official/{countryCode}", method = RequestMethod.GET)
    public List<CountryLanguageResource> getOfficialLanguagesOfCountry(@PathVariable String countryCode){
        LOGGER.debug("Get all official languages of country {}", countryCode);
        return getLanguagesByCountryAndOfficialty(countryCode, true);
    }

    @RequestMapping(value = "/official/{countryCode}", method = RequestMethod.GET)
    public PagedResources<CountryLanguageResource> getOfficialLanguagesOfCountry(@PathVariable String countryCode, Pageable pageable, PagedResourcesAssembler<CountryLanguage> pageAsm) {
        LOGGER.debug("Get {} official languages of country {} page#{}", pageable.getPageSize(), countryCode, pageable.getPageNumber());
        Page<CountryLanguage> languages = getPagedByCountryAndOfficialty(countryCode, true, pageable);
        return pageAsm.toResource(languages, assembler);
    }

    @RequestMapping(value = "/butch/unofficial/{countryCode}", method = RequestMethod.GET)
    public List<CountryLanguageResource> getUnofficialLanguagesOfCountry(@PathVariable String countryCode){
        LOGGER.debug("Get all unofficial languages of country {}", countryCode);
        return getLanguagesByCountryAndOfficialty(countryCode, false);
    }

    @RequestMapping(value = "/unofficial/{countryCode}", method = RequestMethod.GET)
    public PagedResources<CountryLanguageResource> getUnofficialLanguagesOfCountry(@PathVariable String countryCode, Pageable pageable, PagedResourcesAssembler<CountryLanguage> pageAsm) {
        LOGGER.debug("Get {} unofficial languages of country {} page#{}", pageable.getPageSize(), countryCode, pageable.getPageNumber());
        Page<CountryLanguage> languages = getPagedByCountryAndOfficialty(countryCode, false, pageable);
        return pageAsm.toResource(languages, assembler);
    }

    @RequestMapping(value = "/butch/all", method = RequestMethod.GET)
    public List<CountryLanguageResource> getAllLanguages(){
        LOGGER.debug("Get all languages in world");
        return service.getAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public PagedResources<CountryLanguageResource> getAllLanguages(Pageable pageable, PagedResourcesAssembler<CountryLanguage> pageAsm) {
        LOGGER.debug("Get {} languages in world page#{}", pageable.getPageSize(), pageable.getPageNumber());
        Page<CountryLanguage> languages = service.getAll(pageable);
        return pageAsm.toResource(languages, assembler);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CountryLanguageResource getLanguage(@RequestParam(value = "languageName") String languageName,
                                               @RequestParam(value = "countryCode") String countryCode){
        LOGGER.debug("Get language {} of country {}", languageName, countryCode);
        return assembler.toResource(service.getByNameAndCountry(languageName, countryCode));
    }

    @RequestMapping(method = RequestMethod.POST)
    public CountryLanguageResource save(@RequestBody CountryLanguageResource language){
        LOGGER.debug("Saving language {}", language);
        return assembler.toResource(service.save(assembler.toEntity(language)));
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void delete(@RequestParam(value = "languageName") String languageName,
                       @RequestParam(value = "countryCode") String countryCode){
        LOGGER.debug("Deleting language {} of country {}", languageName, countryCode);
        service.remove(languageName, countryCode);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public PagedResources<CountryLanguageResource> filterLanguages(@RequestBody LanguageFilter filter, Pageable pageable, PagedResourcesAssembler<CountryLanguage> pageAsm) {
        LOGGER.debug("Filter languages with filter {} amount {} page#{}", filter, pageable.getPageSize(), pageable.getPageNumber());
        Page<CountryLanguage> languages = service.filter(filter, pageable);
        return pageAsm.toResource(languages, assembler);
    }

    @RequestMapping(value = "/butch/filter", method = RequestMethod.POST)
    public List<CountryLanguageResource> filterLAnguages(@RequestBody LanguageFilter filter) {
        LOGGER.debug("Filter all languages with filter {}", filter);
        return service.filter(filter).stream().map(assembler::toResource).collect(Collectors.toList());
    }

    //TODO editing language method (think about changing other languages in case of changing percentage)

    private List<CountryLanguageResource> getLanguagesByCountryAndOfficialty(String countryCode, boolean isOfficial){
        LOGGER.debug("Get official={} languages of country {}", isOfficial, countryCode);
        return service.getByCountryAndOfficialty(countryCode, isOfficial)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    }

    private Page<CountryLanguage> getPagedByCountryAndOfficialty(String countryCode, boolean isOfficial, Pageable pageable) {
        LOGGER.debug("Get pageable official={} languages of country {}", isOfficial, countryCode);
        return service.getByCountryAndOfficialty(countryCode, isOfficial, pageable);
    }
}
