package com.demente.ideas.learnwords.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author 1987diegog
 */
public abstract class SimpleGrantedAuthorityMixIn {
    @JsonCreator
    public SimpleGrantedAuthorityMixIn(@JsonProperty("authority") String role) {
    }
}
