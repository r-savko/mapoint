package net.mapoint.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntegrationController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String serverInfo() {
        return "{\"server\":\"integration-server\"}";
    }

}
