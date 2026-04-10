package com.jrobertgardzinski.hash.algorithm.argon2;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Positive;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Hash Algorithms")
@Feature("Argon2 Configuration")
class Argon2ConfigRulesTest {

    @Property
    @Label("Config built from valid positive values holds all three VOs")
    void configHoldsVOs(
            @ForAll @Positive int iterations,
            @ForAll @IntRange(min = MemLimitInKB.MIN, max = MemLimitInKB.MAX) int memLimit,
            @ForAll @IntRange(min = Parallelism.MIN, max = Parallelism.MAX) int parallelism
    ) {
        Argon2Config config = Argon2Config.builder()
                .iterations(iterations)
                .memLimit(memLimit)
                .parallelism(parallelism)
                .build();

        assertThat(config.iterations().value()).isEqualTo(iterations);
        assertThat(config.memLimit().value()).isEqualTo(memLimit);
        assertThat(config.parallelism().value()).isEqualTo(parallelism);
    }

    @Example
    @DisplayName("Loads parameters from application.properties")
    void loadsFromProperties() {
        Map<String, String> props = Map.of(
            "argon2.iterations", "10",
            "argon2.memLimit", "32768",
            "argon2.parallelism", "2"
        );

        Argon2Config config = Argon2Config.from(props::get);

        assertThat(config.iterations().value()).isEqualTo(10);
        assertThat(config.memLimit().value()).isEqualTo(32768);
        assertThat(config.parallelism().value()).isEqualTo(2);
    }

    @Example
    @DisplayName("Applies defaults when properties are absent")
    void appliesDefaultsWhenPropertiesAbsent() {
        Argon2Config config = Argon2Config.from(name -> null);

        assertThat(config.iterations().value()).isEqualTo(20);
        assertThat(config.memLimit().value()).isEqualTo(66536);
        assertThat(config.parallelism().value()).isEqualTo(1);
    }
}
