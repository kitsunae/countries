package net.lashin.web.controllers;

import net.lashin.core.hateoas.ImageResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndexPage(){
        return "index";
    }

    //TODO WTF?
    @RequestMapping(value = "/", method = RequestMethod.HEAD)
    public ImageResource getImage() {
        return null;
    }
}
