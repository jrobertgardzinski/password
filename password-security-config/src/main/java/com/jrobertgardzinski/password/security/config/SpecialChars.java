package com.jrobertgardzinski.password.security.config;

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
    }
}
