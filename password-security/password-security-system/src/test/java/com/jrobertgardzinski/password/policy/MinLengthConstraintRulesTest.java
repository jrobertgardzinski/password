package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.security.config.MinLength;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import net.jqwik.api.Example;
import net.jqwik.api.Label;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Password")
@Feature("Constraints")
@Story("Minimum length (for instance: " + MinLengthConstraintRulesTest.MIN_LENGTH + " characters long)")
class MinLengthConstraintRulesTest {

    static final int MIN_LENGTH = 8;
    private final _MinLengthConstraint constraint = new _MinLengthConstraint(new MinLength(MIN_LENGTH));

    final String REJECTS = "Sh0rt!";

    @Example
    @Label("rejects \"" + REJECTS + "\" (at least " + MIN_LENGTH + " chars)")
    void rejection() {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(REJECTS))).isFalse();
    }

    final String ACCEPTS = "LongPass";

    @Example
    @Label("accepts \"" + ACCEPTS + "\"")
    void acceptance() {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(ACCEPTS))).isTrue();
    }

    @Example
    @Label("error code is " + _MinLengthConstraint.CODE)
    void errorCode() {
        assertThat(constraint.code()).isEqualTo(_MinLengthConstraint.CODE);
    }
}
