package com.paw.controller;

import act.controller.Controller;
import org.osgl.mvc.annotation.GetAction;

@Controller
public class HomeController {

    @GetAction("/")
    public String home() {
        return "Home";
    }
}
