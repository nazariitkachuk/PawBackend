package com.paw.controller;

import act.app.ActionContext;
import com.paw.model.User;
import com.paw.response.CustomResponse;
import org.osgl.aaa.NoAuthentication;
import org.osgl.http.H;
import org.osgl.mvc.annotation.PostAction;

import javax.inject.Inject;

import static act.controller.Controller.Util.redirect;

@NoAuthentication
public class LoginController {

    @Inject
    private User.Dao userDao;


    @PostAction("/login")
    public CustomResponse login(String email, String password, H.Flash flash, ActionContext context) {
        User user = userDao.authenticate(email, password);
        if (null == user) {
            flash.error("cannot find user by email and password combination");
            return new CustomResponse(400, "cannot find user by email and password combination");
        }
        context.login(email);
        return new CustomResponse(200, "Logged");
    }

    @PostAction("/register")
    public CustomResponse signUp(User user, ActionContext context) {
            if (userDao.exists(user.email)) {
                context.flash().error("User already exists");
                return new CustomResponse(400, "User already exists");
            }
        user.activated = true;
        userDao.save(user);
        return new CustomResponse(201, "Account created");
    }

}
