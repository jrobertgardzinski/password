package com.jrobertgardzinski.hash.algorithm.argon2;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Hash Algorithms")
@Feature("Argon2 Configuration")
class Argon2ConfigRulesTest {

    @Example
    @Label("Loads parameters from application.properties")
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

    @Property
    @Label("Applies OWASP defaults when properties are absent")
    void appliesDefaultsWhenPropertiesAbsent() {
        assertThat(Argon2Config.builder().build()).isEqualTo(Argon2Config.withDefaults());
    }

}
