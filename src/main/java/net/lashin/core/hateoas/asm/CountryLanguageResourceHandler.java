package net.lashin.core.hateoas.asm;

import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.hateoas.CountryLanguageResource;
import net.lashin.web.controllers.CountryController;
import net.lashin.web.controllers.LanguageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CountryLanguageResourceHandler extends ResourceHandler<CountryLanguage, CountryLanguageResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryLanguageResourceHandler.class);

    public CountryLanguageResourceHandler() {
        super(LanguageController.class, CountryLanguageResource.class);
    }

    @Override
    public CountryLanguageResource toResource(CountryLanguage language) {
        LOGGER.trace("Translating {} to resource", language);
        CountryLanguageResource resource = new CountryLanguageResource();
        resource.setCountryCode(language.getCountryCode());
        resource.setLanguage(language.getLanguage());
        resource.setOfficial(language.isOfficial());
        resource.setPercentage(language.getPercentage());
        resource.setDescription(language.getDescription());
        Link self = linkTo(methodOn(LanguageController.class).getLanguage(language.getLanguage(), language.getCountryCode())).withSelfRel();
        resource.add(self);
        Link country = linkTo(methodOn(CountryController.class).getCountry(language.getCountryCode())).withRel("country");
        resource.add(country);
        Link allCountries = linkTo(methodOn(CountryController.class).getCountriesWithSameLanguage(language.getLanguage(), null, null)).withRel("allCountries");
        resource.add(allCountries);
        return resource;
    }

    @Override
    public CountryLanguage toEntity(CountryLanguageResource resource) {
        LOGGER.trace("Translating to entity resource {}", resource);
        CountryLanguage language = new CountryLanguage(resource.getCountryCode(), resource.getLanguage(), resource.isOfficial(), resource.getPercentage());
        language.setDescription(resource.getDescription());
        return language;
    }
}
