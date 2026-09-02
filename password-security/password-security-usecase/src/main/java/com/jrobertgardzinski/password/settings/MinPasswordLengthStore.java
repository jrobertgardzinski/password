package com.jrobertgardzinski.password.settings;

import com.jrobertgardzinski.password.security.config.MinLength;

/**
 * The write side of the password settings: lays an ADMIN's decision on the live rung. Only a
 * {@link MinLength} gets in, so every value written here is one the ladder's gate will accept.
 * Its mirror, the read side, is {@link PasswordPolicyInForce}; see there for why they are two.
 */
public interface MinPasswordLengthStore {

    void save(MinLength minLength);
}
