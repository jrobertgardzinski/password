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

    final String REJECTS = "PASSWORD1!";

    @Example
    @Label("rejects \"" + REJECTS + "\" (no lowercase)")
    void rejection() {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(REJECTS))).isFalse();
    }

    final String ACCEPTS = "Password1!";

    @Example
    @Label("accepts \"" + ACCEPTS + "\"")
    void acceptance() {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(ACCEPTS))).isTrue();
    }

    @Example
    @Label("error code is " + _ContainsLowercaseConstraint.CODE)
    void errorCode() {
        assertThat(constraint.code()).isEqualTo(_ContainsLowercaseConstraint.CODE);
    }
}
