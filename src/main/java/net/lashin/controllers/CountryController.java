package net.lashin.controllers;

import net.lashin.beans.Country;
import net.lashin.services.CountryService;
import net.lashin.services.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lashi on 09.02.2017.
 */
@Controller
@RequestMapping(value = "/getCountry")
public class CountryController {
    @Autowired
    private CountryService service;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Country getCountry(){
        return service.getCountryByName("Russian Federation");
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody List<Country> getAllCountries(){
        return service.getAllCountries();
    }

    @RequestMapping(value = "/names", method = RequestMethod.GET)
    public @ResponseBody List<String> getAllCountryNames(){
        return service.getAllCountryNames();
    }
}
