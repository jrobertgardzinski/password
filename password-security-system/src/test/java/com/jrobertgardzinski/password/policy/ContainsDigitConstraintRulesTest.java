package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Constraints")
@Feature("Mininum Length")
class ContainsDigitConstraintRulesTest {

    private final _ContainsDigitConstraint constraint = new _ContainsDigitConstraint();

    @Property(tries = 10)
    @Label("any password without digit is not satisfied")
    void passwordWithoutDigitIsNotSatisfied(@ForAll("withoutDigit") String value) {
        Allure.parameter("password", value);
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isFalse();
    }

    @Property(tries = 10)
    @Label("any password with at least one digit is satisfied")
    void passwordWithDigitIsSatisfied(@ForAll("withDigit") String value) {
        Allure.parameter("password", value);
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isTrue();
    }

    @Example
    @Label("error code is DIGIT_REQUIRED")
    void errorCode() {
        assertThat(constraint.code()).isEqualTo("DIGIT_REQUIRED");
    }

    @Provide
    Arbitrary<String> withoutDigit() {
        return Arbitraries.strings().withChars("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").ofMinLength(1);
    }

    @Provide
    Arbitrary<String> withDigit() {
        return Arbitraries.strings().withChars("0123456789").ofMinLength(1);
    }
}
