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

    @Property
    @Label("Applies defaults when properties are absent")
    void appliesDefaultsWhenPropertiesAbsent() {
        assertThat(Argon2Config.builder().build()).isEqualTo(Argon2Config.withDefaults());
    }

}
