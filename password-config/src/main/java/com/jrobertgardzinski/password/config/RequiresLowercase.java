package com.jrobertgardzinski.password.config;

public record RequiresLowercase(boolean value) {

    public static final RequiresLowercase DEFAULT = new RequiresLowercase(true);
}
