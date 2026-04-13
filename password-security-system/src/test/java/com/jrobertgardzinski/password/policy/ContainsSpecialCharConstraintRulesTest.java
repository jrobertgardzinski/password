package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.security.config.SpecialChars;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Constraints")
@Feature("Special char")
class ContainsSpecialCharConstraintRulesTest {

    private final _ContainsSpecialCharConstraint constraint = new _ContainsSpecialCharConstraint();

    @Property(tries = 10)
    @Label("any password without special char is not satisfied")
    void passwordWithoutSpecialCharIsNotSatisfied(@ForAll("withoutSpecialChar") String value) {
        Allure.parameter("password", value);
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isFalse();
    }

    @Property(tries = 10)
    @Label("any password with at least one special char is satisfied")
    void passwordWithSpecialCharIsSatisfied(@ForAll("withSpecialChar") String value) {
        Allure.parameter("password", value);
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isTrue();
    }

    @Example
    @Label("error code is SPECIAL_CHAR_REQUIRED")
    void errorCode() {
        assertThat(constraint.code()).isEqualTo("SPECIAL_CHAR_REQUIRED");
    }

    @Provide
    Arbitrary<String> withoutSpecialChar() {
        return Arbitraries.strings().withChars("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789").ofMinLength(1);
    }

    @Provide
    Arbitrary<String> withSpecialChar() {
        return Arbitraries.strings().withChars(SpecialChars.DEFAULT.value()).ofMinLength(1);
    }
}
