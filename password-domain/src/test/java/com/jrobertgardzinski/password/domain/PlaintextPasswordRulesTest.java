package com.jrobertgardzinski.password.domain;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import net.jqwik.api.*;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.StringLength;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    @Story("Creation")
    @DisplayName("accepts non-blank password")
    @Description("Strength rules live in the application layer — the domain only enforces presence.")
    void acceptsNonBlankPassword() {
        assertDoesNotThrow(() -> PlaintextPassword.of("hello"));
    }

    @Property
    @Label("any non-blank alpha string is accepted")
    void anyNonBlankAlphaStringIsAccepted(@ForAll @StringLength(min = 1) @AlphaChars String value) {
        assertDoesNotThrow(() -> PlaintextPassword.of(value));
    }

    @Test
    @Story("Equality")
    @DisplayName("equal when same value")
    void equalWhenSameValue() {
        assertEquals(PlaintextPassword.of("hello"), PlaintextPassword.of("hello"));
    }

    @Test
    @Story("Equality")
    @DisplayName("not equal when different value")
    void notEqualWhenDifferentValue() {
        assertNotEquals(PlaintextPassword.of("hello"), PlaintextPassword.of("world"));
    }

    @Test
    @Story("Security")
    @DisplayName("toString does not reveal plaintext")
    void toStringDoesNotRevealPlaintext() {
        assertThat(PlaintextPassword.of("secret").toString()).doesNotContain("secret");
    }
}
