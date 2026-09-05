package com.jrobertgardzinski.password.config;

/**
 * The fewest characters a password may have; never below {@value #BOUNDARY}.
 */
public record MinLength(int value) {

    /** The name this rule goes by on every level of a deployment's configuration ladder. */
    public static final String KEY = "security.password.policy.min.length";

    public static final int BOUNDARY = 5;
    public static final MinLength DEFAULT = new MinLength(BOUNDARY);

    public MinLength {
        if (value < BOUNDARY)
            throw new IllegalArgumentException("minLength must be at least " + BOUNDARY);
    }
}
