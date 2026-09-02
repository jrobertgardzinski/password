package com.jrobertgardzinski.password.policy.ladder;

import com.jrobertgardzinski.config.ladder.ConfigLadder;
import com.jrobertgardzinski.password.policy.PasswordPolicy;
import com.jrobertgardzinski.password.config.MinLength;
import com.jrobertgardzinski.password.config.RequiresDigit;
import com.jrobertgardzinski.password.config.RequiresLowercase;
import com.jrobertgardzinski.password.config.RequiresUppercase;
import com.jrobertgardzinski.password.config.SpecialChars;

public final class LadderedPasswordPolicy implements PasswordPolicyInForce {

    private final ConfigLadder<Integer> minLength;

    public LadderedPasswordPolicy(ConfigLadder<Integer> minLength) {
        this.minLength = minLength;
    }

    @Override
    public PasswordPolicy current() {
        return new PasswordPolicy(
                new MinLength(minLength.resolve()),
                SpecialChars.DEFAULT,
                RequiresUppercase.DEFAULT,
                RequiresLowercase.DEFAULT,
                RequiresDigit.DEFAULT);
    }
}
