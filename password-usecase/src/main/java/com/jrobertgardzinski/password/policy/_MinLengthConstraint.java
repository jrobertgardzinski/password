package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.config.MinLength;
import com.jrobertgardzinski.util.constraint.ErrorConstraint;

/**
 * Fails a password shorter than the policy's minimum length.
 */
class _MinLengthConstraint extends ErrorConstraint<PlaintextPassword> {

    static final String CODE = "MIN_LENGTH_NOT_MET";
    private final MinLength minLength;

    public _MinLengthConstraint(MinLength minLength) {
        this.minLength = minLength;
    }

    public _MinLengthConstraint() {
        this(MinLength.DEFAULT);
    }

    @Override
    public boolean isSatisfied(PlaintextPassword candidate) {
        return candidate.value().length() >= minLength.value();
    }

    @Override
    public String code() {
        return CODE;
    }
}
