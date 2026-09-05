package com.jrobertgardzinski.password.config;

import com.jrobertgardzinski.config.ConfigValue;

import java.util.Objects;

/**
 * Whether a password must contain a lowercase letter.
 */
public record RequiresLowercase(Boolean value) implements ConfigValue<Boolean> {

    /** The name this rule goes by on every level of a deployment's configuration ladder. */
    public static final String KEY = "security.password.policy.requires.lowercase";

    public RequiresLowercase {
        Objects.requireNonNull(value, "value");
    }

    public static final RequiresLowercase DEFAULT = new RequiresLowercase(true);

    @Override
    public String key() {
        return KEY;
    }

    @Override
    public Boolean defaultValue() {
        return DEFAULT.value();
    }
}
