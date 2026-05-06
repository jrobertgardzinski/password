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
@Feature("Lowercase")
class ContainsLowercaseConstraintRulesTest {

    private final _ContainsLowercaseConstraint constraint = new _ContainsLowercaseConstraint();

    @DisplayName("rejects ")
    @ParameterizedTest(name = "\"{0}\" (no lowercase)")
    @ValueSource(strings = {"PASSWORD1!", "ABC123#", "NOLOWER"})
    void rejectsPasswordWithNoLowercase(String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isFalse();
    }

    @DisplayName("accepts ")
    @ParameterizedTest(name = "\"{0}\" (has lowercase)")
    @ValueSource(strings = {"Password1!", "aBC123", "strong"})
    void acceptsPasswordWithLowercase(String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isTrue();
    }

    @Example
    @Label("error code is LOWERCASE_REQUIRED")
    void errorCode() {
        assertThat(constraint.code()).isEqualTo("LOWERCASE_REQUIRED");
    }
}
