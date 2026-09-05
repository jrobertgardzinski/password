package com.jrobertgardzinski.password.application;

import com.jrobertgardzinski.config.ladder.ConfigLadder;
import com.jrobertgardzinski.config.ladder.Rung;
import com.jrobertgardzinski.password.config.MinLength;
import com.jrobertgardzinski.password.config.RequiresDigit;
import com.jrobertgardzinski.password.config.RequiresLowercase;
import com.jrobertgardzinski.password.config.RequiresUppercase;
import com.jrobertgardzinski.password.config.SpecialChars;
import com.jrobertgardzinski.password.policy.PasswordPolicy;
import com.jrobertgardzinski.password.policy.PasswordPolicyInForce;

import static com.jrobertgardzinski.password.application.PasswordPolicyProperties.MIN_LENGTH;
import static com.jrobertgardzinski.password.application.PasswordPolicyProperties.REQUIRES_DIGIT;
import static com.jrobertgardzinski.password.application.PasswordPolicyProperties.REQUIRES_LOWERCASE;
import static com.jrobertgardzinski.password.application.PasswordPolicyProperties.REQUIRES_UPPERCASE;
import static com.jrobertgardzinski.password.application.PasswordPolicyProperties.SPECIAL_CHARS;

/**
 * The policy a deployment gets without any order of its own: every rule from its property (restart)
 * over the library default (rebuild). The five ladders are declared once, so an illegal property
 * refuses the policy where it is built and never on a password.
 */
public final class RestartBoundPasswordPolicy implements PasswordPolicyInForce {

    private final ConfigLadder<Integer> minLength;
    private final ConfigLadder<String> specialChars;
    private final ConfigLadder<Boolean> requiresUppercase;
    private final ConfigLadder<Boolean> requiresLowercase;
    private final ConfigLadder<Boolean> requiresDigit;

    public RestartBoundPasswordPolicy(PasswordPolicyProperties properties) {
        minLength = ConfigLadder.of(MIN_LENGTH, MinLength::new,
                Rung.restart(properties.integers()), Rung.rebuild(MinLength.DEFAULT.value()));
        specialChars = ConfigLadder.of(SPECIAL_CHARS, SpecialChars::new,
                Rung.restart(properties.text()), Rung.rebuild(SpecialChars.DEFAULT.value()));
        requiresUppercase = ConfigLadder.of(REQUIRES_UPPERCASE, RequiresUppercase::new,
                Rung.restart(properties.booleans()), Rung.rebuild(RequiresUppercase.DEFAULT.value()));
        requiresLowercase = ConfigLadder.of(REQUIRES_LOWERCASE, RequiresLowercase::new,
                Rung.restart(properties.booleans()), Rung.rebuild(RequiresLowercase.DEFAULT.value()));
        requiresDigit = ConfigLadder.of(REQUIRES_DIGIT, RequiresDigit::new,
                Rung.restart(properties.booleans()), Rung.rebuild(RequiresDigit.DEFAULT.value()));
    }

    @Override
    public PasswordPolicy current() {
        return new PasswordPolicy(
                new MinLength(minLength.resolve()),
                new SpecialChars(specialChars.resolve()),
                new RequiresUppercase(requiresUppercase.resolve()),
                new RequiresLowercase(requiresLowercase.resolve()),
                new RequiresDigit(requiresDigit.resolve()));
    }
}
