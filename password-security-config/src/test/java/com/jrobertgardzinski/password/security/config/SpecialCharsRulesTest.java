package com.jrobertgardzinski.password.security.config;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Epic("Password")
@Feature("Password Security Configuration - SpecialChars")
class SpecialCharsRulesTest {

    @Property
    @Label("Invariant: accepts valid values")
    void acceptsValidValues(@ForAll("validValues") Tuple.Tuple2<String, String> example) {
        Allure.parameter(example.get1(), example.get2());
        String value = example.get2();
        assertThat(new SpecialChars(value).value()).isEqualTo(value);
    }

    @Property
    @Label("Invariant: rejects invalid values")
    void rejectsInvalidValues(@ForAll("invalidValues") Tuple.Tuple2<String, String> example) {
        Allure.parameter(example.get1(), example.get2());
        assertThrows(IllegalArgumentException.class, () -> new SpecialChars(example.get2()));
    }

    @Example
    @Label("Default value is defined and accepted")
    void defaultValueIsDefinedAndAccepted() {
        assertThat(SpecialChars.DEFAULT.value()).isNotEmpty();
        assertThat(new SpecialChars(SpecialChars.DEFAULT.value()).value()).isEqualTo(SpecialChars.DEFAULT.value());
    }

    @Provide
    Arbitrary<Tuple.Tuple2<String, String>> validValues() {
        return Arbitraries.of(
                Tuple.of("single char from ALLOWED", "#"),
                Tuple.of("multiple chars from ALLOWED", "!@#"),
                Tuple.of("DEFAULT value", SpecialChars.DEFAULT.value())
        );
    }

    @Provide
    Arbitrary<Tuple.Tuple2<String, String>> invalidValues() {
        return Arbitraries.of(
                Tuple.of("empty string", ""),
                Tuple.of("null", null),
                Tuple.of("letter — not a special char", "a"),
                Tuple.of("digit — not a special char", "1")
        );
    }
}
