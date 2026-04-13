package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.security.config.SpecialChars;
import com.jrobertgardzinski.util.constraint.ErrorConstraint;

import java.util.regex.Pattern;

public class _ContainsSpecialCharConstraint extends ErrorConstraint<PlaintextPassword> {

    private final Pattern pattern;

    public _ContainsSpecialCharConstraint() {
        this(SpecialChars.DEFAULT);
    }

    public _ContainsSpecialCharConstraint(SpecialChars specialChars) {
        this.pattern = Pattern.compile("[" + Pattern.quote(specialChars.value()) + "]");
    }

    @Override
    public boolean isSatisfied(PlaintextPassword candidate) {
        return pattern.matcher(candidate.value()).find();
    }

    @Override
    public String code() {
        return "SPECIAL_CHAR_REQUIRED";
    }
}
