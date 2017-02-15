package net.lashin.beans.hateoas.asm;

import net.lashin.beans.CountryLanguage;
import net.lashin.beans.hateoas.CountryLanguageResource;
import net.lashin.controllers.LanguageController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

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
        return resource;
    }
}
