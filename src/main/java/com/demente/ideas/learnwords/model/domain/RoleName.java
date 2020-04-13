package com.demente.ideas.learnwords.model.domain;

/**
 * @author 1987diegog
 */
public enum RoleName {
    ROLE_USER,
    ROLE_ADMIN;

    public static RoleName get(String rol) {

        if (ROLE_ADMIN.name().equals(rol)) {
            return ROLE_ADMIN;
        } else if (ROLE_USER.name().equals(rol)) {
            return ROLE_USER;
        }
        return null;
    }
}
