package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.Example;
import net.jqwik.api.Label;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Constraints")
@Feature("Digit")
class ContainsDigitConstraintRulesTest {

    private final _ContainsDigitConstraint constraint = new _ContainsDigitConstraint();

    @DisplayName("rejects ")
    @ParameterizedTest(name = "\"{0}\" (no digit)")
    @ValueSource(strings = {"Password!", "Secret#", "NoDigit!"})
    void rejectsPasswordWithNoDigit(String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isFalse();
    }

    @DisplayName("accepts ")
    @ParameterizedTest(name = "\"{0}\" (has digit)")
    @ValueSource(strings = {"Password1", "Secret1#", "abc123"})
    void acceptsPasswordWithDigit(String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isTrue();
    }

    @Example
    @Label("error code is DIGIT_REQUIRED")
    void errorCode() {
        assertThat(constraint.code()).isEqualTo("DIGIT_REQUIRED");
    }
}
