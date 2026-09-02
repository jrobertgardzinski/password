package com.jrobertgardzinski.password.policy.ladder;

import com.jrobertgardzinski.password.policy.PasswordPolicy;

@FunctionalInterface
public interface PasswordPolicyInForce {

    PasswordPolicy current();
}
