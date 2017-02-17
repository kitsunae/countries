package net.lashin.beans.hateoas.asm;

import net.lashin.beans.CountryLanguage;
import net.lashin.beans.hateoas.CountryLanguageResource;
import net.lashin.controllers.CountryController;
import net.lashin.controllers.LanguageController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by lashi on 15.02.2017.
 */
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
        Link allCountries = linkTo(methodOn(LanguageController.class).getCountriesWithSameLanguage(language.getLanguage())).withRel("allCountries");
        resource.add(allCountries);
        return resource;
    }
}
