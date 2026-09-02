package com.jrobertgardzinski.password.config;

public record RequiresUppercase(boolean value) {

    public static final RequiresUppercase DEFAULT = new RequiresUppercase(true);
}
