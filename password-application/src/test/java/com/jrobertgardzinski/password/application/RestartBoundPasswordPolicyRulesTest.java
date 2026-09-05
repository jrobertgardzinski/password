package com.jrobertgardzinski.password.application;

import com.jrobertgardzinski.config.ladder.Level;
import com.jrobertgardzinski.config.source.restart.RestartConfigPort;
import com.jrobertgardzinski.password.config.MinLength;
import com.jrobertgardzinski.password.config.RequiresDigit;
import com.jrobertgardzinski.password.config.RequiresLowercase;
import com.jrobertgardzinski.password.config.RequiresUppercase;
import com.jrobertgardzinski.password.config.SpecialChars;
import com.jrobertgardzinski.password.policy.PasswordPolicy;
import io.qameta.allure.Allure;
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

import java.util.Map;

import static com.jrobertgardzinski.password.application.PasswordPolicyProperties.MIN_LENGTH;
import static com.jrobertgardzinski.password.application.PasswordPolicyProperties.REQUIRES_DIGIT;
import static com.jrobertgardzinski.password.application.PasswordPolicyProperties.REQUIRES_LOWERCASE;
import static com.jrobertgardzinski.password.application.PasswordPolicyProperties.REQUIRES_UPPERCASE;
import static com.jrobertgardzinski.password.application.PasswordPolicyProperties.SPECIAL_CHARS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Epic("Password")
@Feature("Application")
@Story("The restart-bound policy: every rule from the deployment's property over the library default")
class RestartBoundPasswordPolicyRulesTest {

    private static PasswordPolicyProperties properties(Map<String, String> text) {
        RestartConfigPort<String> port = text::get;
        return new PasswordPolicyProperties(port);
    }

    @Example
    @Label("no property at all → the library defaults, rule by rule")
    void defaults() {
        PasswordPolicy policy = new RestartBoundPasswordPolicy(properties(Map.of())).current();
        assertThat(policy).isEqualTo(PasswordPolicy.withDefaults());
    }

    @Example
    @Label("every rule has a key of its own, and each property replaces only its rule")
    void everyRuleFromItsProperty() {
        PasswordPolicy policy = new RestartBoundPasswordPolicy(properties(Map.of(
                MIN_LENGTH, "12",
                SPECIAL_CHARS, "#?!",
                REQUIRES_UPPERCASE, "false",
                REQUIRES_LOWERCASE, "FALSE",
                REQUIRES_DIGIT, " true "))).current();
        assertThat(policy.minLength()).isEqualTo(new MinLength(12));
        assertThat(policy.specialChars()).isEqualTo(new SpecialChars("#?!"));
        assertThat(policy.requiresUppercase()).isEqualTo(new RequiresUppercase(false));
        assertThat(policy.requiresLowercase()).isEqualTo(new RequiresLowercase(false));
        assertThat(policy.requiresDigit()).isEqualTo(new RequiresDigit(true));
    }

    @Example
    @Label("one property set, the other four rules stay on their defaults")
    void oneRuleMoves() {
        PasswordPolicy policy = new RestartBoundPasswordPolicy(properties(Map.of(MIN_LENGTH, "12"))).current();
        assertThat(policy).isEqualTo(PasswordPolicy.defaultsExcept(new MinLength(12), SpecialChars.DEFAULT));
    }

    @Property
    @Label("a length property below the boundary refuses the policy where it is built, naming the key")
    void illegalLengthRefusesAtStart(@ForAll("belowBoundary") int length) {
        Allure.parameter("property", length);
        assertThatThrownBy(() -> new RestartBoundPasswordPolicy(properties(Map.of(MIN_LENGTH, Integer.toString(length)))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(MIN_LENGTH)
                .hasMessageContaining(Level.RESTART.label());
    }

    @Provide
    Arbitrary<Integer> belowBoundary() {
        return Arbitraries.integers().between(-10, MinLength.BOUNDARY - 1);
    }

    @Example
    @Label("a property that is not its type is illegal at the restart level: the start fails, the rule is not silently dropped")
    void wrongTypeRefusesAtStart() {
        assertThatThrownBy(() -> new RestartBoundPasswordPolicy(properties(Map.of(MIN_LENGTH, "twelve"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(MIN_LENGTH)
                .hasMessageContaining("integer");
        assertThatThrownBy(() -> new RestartBoundPasswordPolicy(properties(Map.of(REQUIRES_DIGIT, "yes"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(REQUIRES_DIGIT)
                .hasMessageContaining("true or false");
        assertThatThrownBy(() -> new RestartBoundPasswordPolicy(properties(Map.of(SPECIAL_CHARS, "abc"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(SPECIAL_CHARS)
                .hasMessageContaining(Level.RESTART.label());
    }

    @Example
    @Label("current() answers the same policy every time: properties are bound at start")
    void boundAtStart() {
        Map<String, String> text = new java.util.HashMap<>(Map.of(MIN_LENGTH, "12"));
        RestartBoundPasswordPolicy inForce = new RestartBoundPasswordPolicy(properties(text));
        text.put(MIN_LENGTH, "16");
        assertThat(inForce.current().minLength()).isEqualTo(new MinLength(12));
    }
}
