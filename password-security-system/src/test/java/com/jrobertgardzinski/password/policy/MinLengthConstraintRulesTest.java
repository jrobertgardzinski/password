package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.security.config.MinLength;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Constraints")
@Feature("Minimum length")
class MinLengthConstraintRulesTest {

    private static final int MIN_LENGTH = 8;
    private final _MinLengthConstraint constraint = new _MinLengthConstraint(new MinLength(MIN_LENGTH));

    @Property(tries = 10)
    @Label("any password shorter than minLength is not satisfied")
    void passwordShorterThanMinLengthIsNotSatisfied(@ForAll("belowMinLength") String value) {
        Allure.parameter("password", value);
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isFalse();
    }

    @Property(tries = 10)
    @Label("any password of at least minLength chars is satisfied")
    void passwordAtLeastMinLengthIsSatisfied(@ForAll("atLeastMinLength") String value) {
        Allure.parameter("password", value);
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isTrue();
    }

    @Example
    @Label("error code is MIN_LENGTH_NOT_MET")
    void errorCode() {
        assertThat(constraint.code()).isEqualTo("MIN_LENGTH_NOT_MET");
    }

    @Provide
    Arbitrary<String> belowMinLength() {
        return Arbitraries.strings().withChars(PRINTABLE).ofMinLength(1).ofMaxLength(MIN_LENGTH - 1);
    }

    @Provide
    Arbitrary<String> atLeastMinLength() {
        return Arbitraries.strings().withChars(PRINTABLE).ofMinLength(MIN_LENGTH);
    }

    private static final String PRINTABLE =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*-_";
}
