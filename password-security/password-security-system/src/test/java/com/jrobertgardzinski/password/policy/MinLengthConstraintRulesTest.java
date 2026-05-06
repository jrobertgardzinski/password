package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.security.config.MinLength;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.Example;
import net.jqwik.api.Label;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Constraints")
@Feature("Minimum length")
class MinLengthConstraintRulesTest {

    private static final int MIN_LENGTH = 8;
    private final _MinLengthConstraint constraint = new _MinLengthConstraint(new MinLength(MIN_LENGTH));

    @DisplayName("rejects ")
    @ParameterizedTest(name = "\"{0}\" (too short)")
    @ValueSource(strings = {"Sh0rt!", "Pa1!", "Abc1!"})
    void rejectsPasswordTooShort(String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isFalse();
    }

    @DisplayName("accepts ")
    @ParameterizedTest(name = "\"{0}\" (at least " + MIN_LENGTH + " chars)")
    @ValueSource(strings = {"LongPass", "Password", "Secure1#"})
    void acceptsPasswordLongEnough(String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isTrue();
    }

    @Example
    @Label("error code is MIN_LENGTH_NOT_MET")
    void errorCode() {
        assertThat(constraint.code()).isEqualTo("MIN_LENGTH_NOT_MET");
    }
}
