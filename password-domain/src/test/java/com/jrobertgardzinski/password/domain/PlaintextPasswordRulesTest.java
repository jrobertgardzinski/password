package com.jrobertgardzinski.password.domain;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.*;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.StringLength;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Epic("Domain")
@Feature("PlaintextPassword")
class PlaintextPasswordRulesTest {

    @Example
    @Label("Invariant: rejects null")
    void rejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> PlaintextPassword.of(null));
    }

    @Property(tries = 20)
    @Label("Security: toString() does not reveal plaintext")
    void toStringDoesNotRevealPlaintext(@ForAll @StringLength(min = 1) @AlphaChars String value) {
        PlaintextPassword password = PlaintextPassword.of(value);
        Allure.parameter("password", value);
        Allure.parameter("toString()", password.toString());
        assertThat(password.toString()).contains(PlaintextPassword.REDACTED);
    }
}
