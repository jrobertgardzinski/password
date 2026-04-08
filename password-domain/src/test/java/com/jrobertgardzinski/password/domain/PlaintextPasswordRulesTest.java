package com.jrobertgardzinski.password.domain;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Epic("Domain")
@Feature("PlaintextPassword")
class PlaintextPasswordRulesTest {

    @Test
    @Story("Creation")
    @DisplayName("rejects null value")
    @Description("Null would propagate silently through the domain — must be rejected at construction.")
    void rejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> PlaintextPassword.of(null));
    }

    @Test
    @Story("Creation")
    @DisplayName("rejects blank value")
    @Description("A blank password carries no information — whitespace-only input is treated as absent.")
    void rejectsBlank() {
        assertThrows(IllegalArgumentException.class, () -> PlaintextPassword.of("   "));
    }

    @Test
    @Story("Security")
    @DisplayName("toString does not reveal plaintext")
    void toStringDoesNotRevealPlaintext() {
        assertThat(PlaintextPassword.of("secret").toString()).doesNotContain("secret");
    }
}
