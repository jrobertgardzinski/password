package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Constraints")
@Feature("Uppercase")
class ContainsUppercaseConstraintRulesTest {

    private final _ContainsUppercaseConstraint constraint = new _ContainsUppercaseConstraint();

    @Property(tries = 10)
    @Label("any password without uppercase is not satisfied")
    void passwordWithoutUppercaseIsNotSatisfied(@ForAll("withoutUppercase") String value) {
        Allure.parameter("password", value);
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isFalse();
    }

    @Property(tries = 10)
    @Label("any password with at least one uppercase is satisfied")
    void passwordWithUppercaseIsSatisfied(@ForAll("withUppercase") String value) {
        Allure.parameter("password", value);
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isTrue();
    }

    @Example
    @Label("error code is UPPERCASE_REQUIRED")
    void errorCode() {
        assertThat(constraint.code()).isEqualTo("UPPERCASE_REQUIRED");
    }

    @Provide
    Arbitrary<String> withoutUppercase() {
        return Arbitraries.strings().withChars("abcdefghijklmnopqrstuvwxyz0123456789").ofMinLength(1);
    }

    @Provide
    Arbitrary<String> withUppercase() {
        return Arbitraries.strings().withChars("ABCDEFGHIJKLMNOPQRSTUVWXYZ").ofMinLength(1);
    }
}
