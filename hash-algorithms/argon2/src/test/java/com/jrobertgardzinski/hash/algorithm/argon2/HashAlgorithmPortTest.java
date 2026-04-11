package com.jrobertgardzinski.hash.algorithm.argon2;

import com.jrobertgardzinski.password.domain.HashAlgorithmPort;
import com.jrobertgardzinski.password.domain.HashedPassword;
import com.jrobertgardzinski.password.domain.PlaintextPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class HashAlgorithmPortTest {

    private static final String INPUT = "StrongPassword1!";

    protected abstract HashAlgorithmPort hashAlgorithm();

    private HashedPassword hash;

    @BeforeEach
    void init() {
        hash = hashAlgorithm().hash(PlaintextPassword.of(INPUT));
    }

    @Test
    void correctPasswordVerifies() {
        assertTrue(hashAlgorithm().verify(hash, PlaintextPassword.of(INPUT)));
    }

    @Test
    void wrongPasswordDoesNotVerify() {
        assertFalse(hashAlgorithm().verify(hash, PlaintextPassword.of(INPUT + "23")));
    }
}
