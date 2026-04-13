package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Constraints")
@Feature("Lowercase")
class ContainsLowercaseConstraintRulesTest {

    private final _ContainsLowercaseConstraint constraint = new _ContainsLowercaseConstraint();

    @Property(tries = 10)
    @Label("any password without lowercase is not satisfied")
    void passwordWithoutLowercaseIsNotSatisfied(@ForAll("withoutLowercase") String value) {
        Allure.parameter("password", value);
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isFalse();
    }

    @Property(tries = 10)
    @Label("any password with at least one lowercase is satisfied")
    void passwordWithLowercaseIsSatisfied(@ForAll("withLowercase") String value) {
        Allure.parameter("password", value);
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isTrue();
    }

    @Example
    @Label("error code is LOWERCASE_REQUIRED")
    void errorCode() {
        assertThat(constraint.code()).isEqualTo("LOWERCASE_REQUIRED");
    }

    @Provide
    Arbitrary<String> withoutLowercase() {
        return Arbitraries.strings().withChars("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789").ofMinLength(1);
    }

    @Provide
    Arbitrary<String> withLowercase() {
        return Arbitraries.strings().withChars("abcdefghijklmnopqrstuvwxyz").ofMinLength(1);
    }
}
