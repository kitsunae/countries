package net.lashin.web.controllers;

import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.beans.hateoas.CountryLanguageResource;
import net.lashin.core.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
