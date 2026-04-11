package com.jrobertgardzinski.hash.algorithm;

import com.jrobertgardzinski.password.domain.HashAlgorithmPort;
import com.jrobertgardzinski.password.domain.HashedPassword;
import com.jrobertgardzinski.password.domain.PlaintextPassword;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Epic("Contract - password verification")
public abstract class HashAlgorithmPortTest {

    private static final String INPUT = "StrongPassword1!";

    protected abstract HashAlgorithmPort hashAlgorithm();

    private HashedPassword hash;

    @BeforeEach
    void init() {
        hash = hashAlgorithm().hash(PlaintextPassword.of(INPUT));
    }

    @Test
    @DisplayName("Correct password verifies")
    void correctPasswordVerifies() {
        assertTrue(hashAlgorithm().verify(hash, PlaintextPassword.of(INPUT)));
    }

    @Test
    @DisplayName("Wrong password does not verify")
    void wrongPasswordDoesNotVerify() {
        assertFalse(hashAlgorithm().verify(hash, PlaintextPassword.of(INPUT + "23")));
    }
}
