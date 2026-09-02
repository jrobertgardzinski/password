package com.jrobertgardzinski.password.config;

/**
 * Whether a password must contain a digit.
 */
public record RequiresDigit(boolean value) {

    public static final RequiresDigit DEFAULT = new RequiresDigit(true);
}
