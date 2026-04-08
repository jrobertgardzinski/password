package com.jrobertgardzinski.password.domain;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Domain")
@Feature("HashedPassword")
class HashedPasswordRulesTest {

    @Test
    @Story("Security")
    @DisplayName("toString does not reveal hash value")
    void toStringDoesNotRevealHash() {
        assertThat(new HashedPassword("super-secret-hash").toString())
                .doesNotContain("super-secret-hash");
    }

    @Test
    @Story("Equality")
    @DisplayName("equal when same hash")
    void equalWhenSameHash() {
        assertEquals(new HashedPassword("abc"), new HashedPassword("abc"));
    }

    @Test
    @Story("Equality")
    @DisplayName("not equal when different hash")
    void notEqualWhenDifferentHash() {
        assertNotEquals(new HashedPassword("abc"), new HashedPassword("xyz"));
    }
}
