package com.jrobertgardzinski.hash.algorithm.argon2;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Epic("Hash Algorithms")
@Feature("Argon2 Configuration")
class Argon2ConfigRulesTest {

    @Test
    @Story("Properties loading")
    @DisplayName("loads parameters from application.properties")
    void loadsFromProperties() {
        Map<String, String> props = Map.of(
            "argon2.iterations", "10",
            "argon2.memLimit", "32768",
            "argon2.parallelism", "2"
        );

        Argon2Config config = Argon2Config.from(props::get);

        assertThat(config.iterations()).isEqualTo(10);
        assertThat(config.memLimit()).isEqualTo(32768);
        assertThat(config.parallelism()).isEqualTo(2);
    }

    @Test
    @Story("Properties loading")
    @DisplayName("applies defaults when properties are absent")
    void appliesDefaultsWhenPropertiesAbsent() {
        Argon2Config config = Argon2Config.from(name -> null);

        assertThat(config.iterations()).isEqualTo(20);
        assertThat(config.memLimit()).isEqualTo(66536);
        assertThat(config.parallelism()).isEqualTo(1);
    }

    @ParameterizedTest(name = "{0} must be >= 1")
    @ValueSource(strings = {"iterations", "memLimit", "parallelism"})
    @Story("Validation")
    @Description("Minimum value is 1. Boundary: 0 is invalid, 1 is the least valid value.")
    void parameterMustBeAtLeastOne(String parameter) {
        int leastValid = 1;
        assertThrows(IllegalArgumentException.class,
            () -> buildWith(parameter, leastValid - 1));
        buildWith(parameter, leastValid);
    }

    private Argon2Config buildWith(String parameter, int value) {
        Argon2Config.Builder builder = Argon2Config.builder();
        switch (parameter) {
            case "iterations"  -> builder.iterations(value);
            case "memLimit"    -> builder.memLimit(value);
            case "parallelism" -> builder.parallelism(value);
        }
        return builder.build();
    }
}
