package com.jrobertgardzinski.password.config;

/**
 * Whether a password must contain a lowercase letter.
 */
public record RequiresLowercase(boolean value) {

    public static final RequiresLowercase DEFAULT = new RequiresLowercase(true);
}
