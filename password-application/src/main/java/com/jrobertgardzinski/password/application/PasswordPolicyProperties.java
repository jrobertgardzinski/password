package com.jrobertgardzinski.password.application;

import com.jrobertgardzinski.config.source.restart.RestartConfigPort;

import java.util.Locale;

/**
 * The deployment's primitives for the five rules: one key per rule, read as text and translated
 * here into the type the value object takes. A property that is not the type it claims is
 * illegal at the level it is bound to, so the translation refuses it and the start fails, as a
 * property below the value object's floor would.
 */
public final class PasswordPolicyProperties {

    public static final String MIN_LENGTH = "security.password.policy.min.length";
    public static final String SPECIAL_CHARS = "security.password.policy.special.chars";
    public static final String REQUIRES_UPPERCASE = "security.password.policy.requires.uppercase";
    public static final String REQUIRES_LOWERCASE = "security.password.policy.requires.lowercase";
    public static final String REQUIRES_DIGIT = "security.password.policy.requires.digit";

    private final RestartConfigPort<String> text;

    public PasswordPolicyProperties(RestartConfigPort<String> text) {
        this.text = text;
    }

    public RestartConfigPort<String> text() {
        return text;
    }

    public RestartConfigPort<Integer> integers() {
        return name -> {
            String raw = text.find(name);
            if (raw == null) {
                return null;
            }
            try {
                return Integer.valueOf(raw.trim());
            } catch (NumberFormatException notANumber) {
                throw new IllegalArgumentException(
                        "property '" + name + "' must be an integer, not '" + raw + "'", notANumber);
            }
        };
    }

    public RestartConfigPort<Boolean> booleans() {
        return name -> {
            String raw = text.find(name);
            if (raw == null) {
                return null;
            }
            return switch (raw.trim().toLowerCase(Locale.ROOT)) {
                case "true" -> Boolean.TRUE;
                case "false" -> Boolean.FALSE;
                default -> throw new IllegalArgumentException(
                        "property '" + name + "' must be true or false, not '" + raw + "'");
            };
        };
    }
}
