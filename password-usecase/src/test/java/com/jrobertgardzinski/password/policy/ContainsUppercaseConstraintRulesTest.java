package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
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
@Story("Uppercase")
class ContainsUppercaseConstraintRulesTest {

    private final _ContainsUppercaseConstraint constraint = new _ContainsUppercaseConstraint();

    @Property
    @Label("rejects passwords with no uppercase letter")
    void rejects(@ForAll("rejected") String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isFalse();
    }

    @Provide
    Arbitrary<String> rejected() {
        return Arbitraries.of("password1!", "abc123", "lower#9");
    }

    @Property
    @Label("accepts passwords with an uppercase letter")
    void accepts(@ForAll("accepted") String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isTrue();
    }

    @Provide
    Arbitrary<String> accepted() {
        return Arbitraries.of("Password1!", "Abc9", "xY7");
    }

    @Example
    @Label("error code is " + _ContainsUppercaseConstraint.CODE)
    void errorCode() {
        assertThat(constraint.code()).isEqualTo(_ContainsUppercaseConstraint.CODE);
    }
}
