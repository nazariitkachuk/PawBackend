package com.paw.controller;

import act.controller.Controller;
import org.osgl.mvc.annotation.GetAction;

public class HealthController extends Controller.Util {

    @GetAction("/health")
    public String health () {
        return "Running";
    }
}
