package com.jrobertgardzinski.password.domain;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.*;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.StringLength;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Epic("Domain")
@Feature("PlaintextPassword")
class PlaintextPasswordRulesTest {

    @Test
    @DisplayName("Invariant: Rejects null")
    void rejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> PlaintextPassword.of(null));
    }

    @Property(tries = 20)
    @Label("Security: toString() does not reveal plaintext")
    void toStringDoesNotRevealPlaintext(@ForAll @StringLength(min = 1) @AlphaChars String value) {
        assertThat(PlaintextPassword.of(value).toString()).contains(PlaintextPassword.REDACTED);
    }
}
