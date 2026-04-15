package com.jrobertgardzinski.password.security.config;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Password")
@Feature("Password Security Configuration - RequiresUppercase")
class RequiresUppercaseRulesTest {
    @Example
    @Label("default is true")
    void defaultIsTrue() {
        assertThat(RequiresUppercase.DEFAULT.value()).isTrue();
    }
}
