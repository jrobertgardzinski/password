package com.jrobertgardzinski.password.config;

/**
 * The special characters a password may use: a non-empty subset of {@link #ALLOWED}, without repeats.
 */
public record SpecialChars(String value) {

    public static final String ALLOWED = "!\"#$%&'()*+,-./:;<=>?@[\\]^_{|}~";
    public static final SpecialChars DEFAULT = new SpecialChars("!@#$%^&*");

    public SpecialChars {
        if (value == null || value.isEmpty())
            throw new IllegalArgumentException("specialChars must not be empty");
        for (char c : value.toCharArray()) {
            if (ALLOWED.indexOf(c) < 0)
                throw new IllegalArgumentException("character not in allowed set: '" + c + "'");
        }
        if (value.chars().distinct().count() != value.length())
            throw new IllegalArgumentException("specialChars must not contain duplicate characters");
    }
}
