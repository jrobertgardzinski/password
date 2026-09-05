package com.jrobertgardzinski.password.config;

/**
 * Whether a password must contain a digit.
 */
public record RequiresDigit(boolean value) {

    /** The name this rule goes by on every level of a deployment's configuration ladder. */
    public static final String KEY = "security.password.policy.requires.digit";

    public static final RequiresDigit DEFAULT = new RequiresDigit(true);
}
