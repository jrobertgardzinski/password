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
@Feature("Uppercase")
class ContainsUppercaseConstraintRulesTest {

    private final _ContainsUppercaseConstraint constraint = new _ContainsUppercaseConstraint();

    @DisplayName("rejects ")
    @ParameterizedTest(name = "\"{0}\" (no uppercase)")
    @ValueSource(strings = {"password1!", "abc123#", "nouppercase"})
    void rejectsPasswordWithNoUppercase(String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isFalse();
    }

    @DisplayName("accepts ")
    @ParameterizedTest(name = "\"{0}\" (has uppercase)")
    @ValueSource(strings = {"Password1!", "Abc123", "STRONG"})
    void acceptsPasswordWithUppercase(String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isTrue();
    }

    @Example
    @Label("error code is UPPERCASE_REQUIRED")
    void errorCode() {
        assertThat(constraint.code()).isEqualTo("UPPERCASE_REQUIRED");
    }
}
