package net.lashin.core.hateoas.asm;

import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.hateoas.CountryLanguageResource;
import net.lashin.web.controllers.CountryController;
import net.lashin.web.controllers.LanguageController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CountryLanguageResourceAsm extends ResourceAssemblerSupport<CountryLanguage, CountryLanguageResource> {

    public CountryLanguageResourceAsm(){
        super(LanguageController.class, CountryLanguageResource.class);
    }

    @Override
    public CountryLanguageResource toResource(CountryLanguage language) {
        CountryLanguageResource resource = new CountryLanguageResource();
        resource.setCountryCode(language.getCountryCode());
        resource.setLanguage(language.getLanguage());
        resource.setOfficial(language.isOfficial());
        resource.setPercentage(language.getPercentage());
        Link self = linkTo(methodOn(LanguageController.class).getLanguage(language.getLanguage(), language.getCountryCode())).withSelfRel();
        resource.add(self);
        Link country = linkTo(methodOn(CountryController.class).getCountry(language.getCountryCode())).withRel("country");
        resource.add(country);
        Link allCountries = linkTo(methodOn(CountryController.class).getCountriesWithSameLanguage(language.getLanguage())).withRel("allCountries");
        resource.add(allCountries);
        return resource;
    }
}
