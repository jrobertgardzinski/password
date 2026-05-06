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
@Feature("Special char")
class ContainsSpecialCharConstraintRulesTest {

    private final _ContainsSpecialCharConstraint constraint = new _ContainsSpecialCharConstraint();

    @DisplayName("rejects ")
    @ParameterizedTest(name = "\"{0}\" (no special char)")
    @ValueSource(strings = {"Password1", "Secret123", "NoSpecial"})
    void rejectsPasswordWithNoSpecialChar(String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isFalse();
    }

    @DisplayName("accepts ")
    @ParameterizedTest(name = "\"{0}\" (has special char)")
    @ValueSource(strings = {"Password1!", "Secret1@", "abc1#"})
    void acceptsPasswordWithSpecialChar(String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isTrue();
    }

    @Example
    @Label("error code is SPECIAL_CHAR_REQUIRED")
    void errorCode() {
        assertThat(constraint.code()).isEqualTo("SPECIAL_CHAR_REQUIRED");
    }
}
