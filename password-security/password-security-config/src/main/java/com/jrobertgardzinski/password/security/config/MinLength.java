package com.jrobertgardzinski.password.security.config;

public record MinLength(int value) {

    public static final int BOUNDARY = 5;
    public static final MinLength DEFAULT = new MinLength(BOUNDARY);

    public MinLength {
        if (value < BOUNDARY)
            throw new IllegalArgumentException("minLength must be at least " + BOUNDARY);
    }
}
