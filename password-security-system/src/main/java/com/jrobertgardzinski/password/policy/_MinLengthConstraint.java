package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.security.config.MinLength;
import com.jrobertgardzinski.util.constraint.ErrorConstraint;

public class _MinLengthConstraint extends ErrorConstraint<PlaintextPassword> {

    private final MinLength minLength;

    public _MinLengthConstraint(MinLength minLength) {
        this.minLength = minLength;
    }

    @Override
    public boolean isSatisfied(PlaintextPassword candidate) {
        return candidate.value().length() >= minLength.value();
    }

    @Override
    public String code() {
        return "MIN_LENGTH_NOT_MET";
    }
}
