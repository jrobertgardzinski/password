package com.jrobertgardzinski.password.security.config;

public record RequiresUppercase(boolean value) {

    public static final RequiresUppercase DEFAULT = new RequiresUppercase(true);
}
