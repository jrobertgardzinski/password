package com.jrobertgardzinski.password.security.config;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Password")
@Feature("Password Security Configuration - RequiresUppercase")
class RequiresUppercaseRulesTest {

    @Property(tries = 10)
    @Label("value is retained")
    void valueIsRetained(@ForAll boolean value) {
        Allure.parameter("value", value);
        assertThat(new RequiresUppercase(value).value()).isEqualTo(value);
    }

    @Example
    @Label("default is true")
    void defaultIsTrue() {
        assertThat(RequiresUppercase.DEFAULT.value()).isTrue();
    }
}
