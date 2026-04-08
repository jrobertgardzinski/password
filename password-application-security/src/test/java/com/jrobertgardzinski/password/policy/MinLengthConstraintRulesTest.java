package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Application")
@Feature("MinLengthConstraint")
class MinLengthConstraintRulesTest {

    @Test
    @Story("Configuration")
    @DisplayName("4 is invalid minimum length — boundary: 5 is least valid")
    @Description("Minimum allowed minLength is 5. Boundary: 4 invalid, 5 least valid.")
    void configurationBoundary() {
        int leastValid = 5;
        assertThrows(IllegalArgumentException.class, () -> new _MinLengthConstraint(leastValid - 1));
        assertDoesNotThrow(() -> new _MinLengthConstraint(leastValid));
    }

    @Test
    @Story("Validation")
    @DisplayName("password at exact minimum length is satisfied")
    void passwordAtExactMinimumLengthIsSatisfied() {
        _MinLengthConstraint constraint = new _MinLengthConstraint(8);
        assertThat(constraint.isSatisfied(PlaintextPassword.of("12345678"))).isTrue();
    }

    @Test
    @Story("Validation")
    @DisplayName("password below minimum length is not satisfied")
    void passwordBelowMinimumLengthIsNotSatisfied() {
        _MinLengthConstraint constraint = new _MinLengthConstraint(8);
        assertThat(constraint.isSatisfied(PlaintextPassword.of("short"))).isFalse();
    }

    @Test
    @Story("Validation")
    @DisplayName("error code is MIN_LENGTH_NOT_MET")
    void errorCode() {
        assertThat(new _MinLengthConstraint(8).code()).isEqualTo("MIN_LENGTH_NOT_MET");
    }
}
