package com.jrobertgardzinski.password.security.config;

public record RequiresDigit(boolean value) {

    public static final RequiresDigit DEFAULT = new RequiresDigit(true);
}
