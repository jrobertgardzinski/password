package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.security.config.SpecialChars;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import net.jqwik.api.Example;
import net.jqwik.api.Label;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.jrobertgardzinski.password.policy.ContainsSpecialCharConstraintRulesTest.CONFIG;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Password")
@Feature("Constraints")
@Story("Special char (for instance: \"" + CONFIG + "\")")
class ContainsSpecialCharConstraintRulesTest {

    public static final String CONFIG = "!";
    private final _ContainsSpecialCharConstraint constraint = new _ContainsSpecialCharConstraint(new SpecialChars(CONFIG));

    final String REJECTS = "Password1";

    @Example
    @Label("rejects \"" + REJECTS + "\" (no special char)")
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
    @Label("error code is " + _ContainsSpecialCharConstraint.CODE)
    void errorCode() {
        assertThat(constraint.code()).isEqualTo(_ContainsSpecialCharConstraint.CODE);
    }
}
