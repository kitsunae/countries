package net.lashin.web.controllers;

import net.lashin.core.hateoas.ImageResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndexPage(){
        LOGGER.debug("Accessing index page");
        return "index";
    }

    //TODO WTF?
    @RequestMapping(value = "/", method = RequestMethod.HEAD)
    public ImageResource getImage() {
        return null;
    }
}
