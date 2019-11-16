package com.paw.controller;

import act.aaa.LoginUser;
import com.paw.model.User;

public abstract class AuthenticatedController {
    @LoginUser
    protected User me;
}
