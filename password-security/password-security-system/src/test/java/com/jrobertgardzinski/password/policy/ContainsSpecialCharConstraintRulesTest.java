package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.security.config.SpecialChars;
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

import static com.jrobertgardzinski.password.policy.ContainsSpecialCharConstraintRulesTest.CONFIG;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Password")
@Feature("Constraints")
@Story("Special char (for instance: \"" + CONFIG + "\")")
class ContainsSpecialCharConstraintRulesTest {

    public static final String CONFIG = "!";
    private final _ContainsSpecialCharConstraint constraint = new _ContainsSpecialCharConstraint(new SpecialChars(CONFIG));

    @Property
    @Label("rejects passwords with no special character")
    void rejects(@ForAll("rejected") String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isFalse();
    }

    @Provide
    Arbitrary<String> rejected() {
        return Arbitraries.of("Password1", "abcABC123", "NoSpecial9");
    }

    @Property
    @Label("accepts passwords with a special character")
    void accepts(@ForAll("accepted") String value) {
        assertThat(constraint.isSatisfied(PlaintextPassword.of(value))).isTrue();
    }

    @Provide
    Arbitrary<String> accepted() {
        return Arbitraries.of("Password1!", "abc!9", "x!Y7");
    }

    @Example
    @Label("error code is " + _ContainsSpecialCharConstraint.CODE)
    void errorCode() {
        assertThat(constraint.code()).isEqualTo(_ContainsSpecialCharConstraint.CODE);
    }
}
