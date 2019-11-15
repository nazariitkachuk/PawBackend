package com.paw.security;

import act.aaa.ActAAAService;
import act.aaa.DynamicPermissionCheckHelperBase;
import com.paw.model.User;
import com.paw.model.UserLinked;
import org.osgl.aaa.Principal;
import org.osgl.util.C;
import org.osgl.util.S;

import java.util.Set;

public class Security extends ActAAAService.Base<User> {


    /**
     * In this simple Todo app every signed up user get granted
     * all of the following permissions
     */
    private static final Set<String> DEFAULT_PERMS = C.set(
            Permission.ROLE_USER
    );

    /**
     * We tell act-aaa `username` is the key to extract the user from database
     */
    @Override
    protected String userKey() {
        return "username";
    }

    /**
     * Just return the default permission set
     */
    @Override
    protected Set<String> permissionsOf(User user) {
        return DEFAULT_PERMS;
    }

    /**
     * inject the logic of password verification into act-aaa
     */
    @Override
    protected boolean verifyPassword(User user, char[] password) {
        return user.verifyPassword(password);
    }

    /**
     * This will help to check a protected resource against the current logged in user
     * if the permission been authorised is dynamic
     */
    public static class DynamicPermissionChecker extends DynamicPermissionCheckHelperBase<UserLinked> {
        @Override
        public boolean isAssociated(UserLinked userLinked, Principal principal) {
            return S.eq(userLinked.email(), principal.getName());
        }
    }

}
