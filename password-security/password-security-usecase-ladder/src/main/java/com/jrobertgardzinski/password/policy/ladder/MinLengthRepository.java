package com.jrobertgardzinski.password.policy.ladder;

import com.jrobertgardzinski.password.security.config.MinLength;

/**
 * The write side of the password settings: lays an ADMIN's decision on the live rung. Write-only
 * on purpose — reading goes through the ladder's own port as a raw number, so the gate can refuse
 * a row instead of this repository blowing up on it. Only a
 * {@link MinLength} gets in, so every value written here is one the ladder's gate will accept.
 * Its mirror, the read side, is {@link PasswordPolicyInForce}; see there for why they are two.
 */
public interface MinLengthRepository {

    void save(MinLength minLength);
}
