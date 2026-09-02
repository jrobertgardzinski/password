package com.jrobertgardzinski.password.config;

/**
 * Whether a password must contain an uppercase letter.
 */
public record RequiresUppercase(boolean value) {

    public static final RequiresUppercase DEFAULT = new RequiresUppercase(true);
}
