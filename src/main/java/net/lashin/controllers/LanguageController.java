package net.lashin.controllers;

import net.lashin.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lashi on 15.02.2017.
 */
@Controller
@RequestMapping(value = "/language")
public class LanguageController {

    @Autowired
    private LanguageService service;
}
