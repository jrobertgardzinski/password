package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.security.config.MinLength;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Password")
@Feature("Constraints")
@Story("Minimum length (for instance: " + MinLengthConstraintRulesTest.MIN_LENGTH + " characters long)")
class MinLengthConstraintRulesTest {

    static final int MIN_LENGTH = 8;
    private final _MinLengthConstraint constraint = new _MinLengthConstraint(new MinLength(MIN_LENGTH));

    @Property
    @Label("rejects passwords shorter than the minimum")
    void rejects(@ForAll("rejected") String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isFalse();
    }

    @Provide
    Arbitrary<String> rejected() {
        return Arbitraries.of("Sh0rt!", "Pa1!", "Abc1!");
    }

    @Property
    @Label("accepts passwords of at least the minimum length")
    void accepts(@ForAll("accepted") String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isTrue();
    }

    @Provide
    Arbitrary<String> accepted() {
        return Arbitraries.of("LongPass", "Password1", "Secure12#");
    }

    @Example
    @Label("error code is " + _MinLengthConstraint.CODE)
    void errorCode() {
        assertThat(constraint.code()).isEqualTo(_MinLengthConstraint.CODE);
    }
}
