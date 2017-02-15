package net.lashin.controllers;

import net.lashin.beans.City;
import net.lashin.services.CityService;
import net.lashin.services.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lashi on 09.02.2017.
 */
@Controller
@RequestMapping(value = "/getCity")
public class CityController {

    @Autowired
    private CityService service;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody City getCity(){
        return service.getCityByName("Moscow");
    }
}
