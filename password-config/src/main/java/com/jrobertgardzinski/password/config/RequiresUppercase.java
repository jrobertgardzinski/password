package com.jrobertgardzinski.password.config;

/**
 * Whether a password must contain an uppercase letter.
 */
public record RequiresUppercase(boolean value) {

    /** The name this rule goes by on every level of a deployment's configuration ladder. */
    public static final String KEY = "security.password.policy.requires.uppercase";

    public static final RequiresUppercase DEFAULT = new RequiresUppercase(true);
}
