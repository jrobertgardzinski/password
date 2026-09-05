package com.jrobertgardzinski.password.config;

/**
 * Whether a password must contain a lowercase letter.
 */
public record RequiresLowercase(boolean value) {

    /** The name this rule goes by on every level of a deployment's configuration ladder. */
    public static final String KEY = "security.password.policy.requires.lowercase";

    public static final RequiresLowercase DEFAULT = new RequiresLowercase(true);
}
