package com.jrobertgardzinski.password.feature;

import com.jrobertgardzinski.password.domain.HashAlgorithmPort;
import com.jrobertgardzinski.password.domain.HashedPassword;
import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.policy.CreatePasswordHash;
import com.jrobertgardzinski.password.policy.PasswordPolicy;
import com.jrobertgardzinski.password.security.config.MinLength;
import com.jrobertgardzinski.password.security.config.SpecialChars;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordValidationRules {

    private static final HashAlgorithmPort STUB = new HashAlgorithmPort() {
        @Override
        public HashedPassword hash(PlaintextPassword plaintextPassword) {
            return new HashedPassword("stub");
        }

        @Override
        public boolean verify(HashedPassword hashedPassword, PlaintextPassword plaintextPassword) {
            return false;
        }
    };

    private CreatePasswordHash createPasswordHash;
    private CreatePasswordHash.PasswordHashCreation result;

    @Given("the default password policy is active:")
    public void theDefaultPasswordPolicyIsActive(DataTable table) {
        PasswordPolicy defaults = PasswordPolicy.withDefaults();
        Map<String, String> settings = table.asMap(String.class, String.class);
        assertEquals(String.valueOf(defaults.minLength().value()),        settings.get("minLength"));
        assertEquals(defaults.specialChars().value(),                     settings.get("specialChars"));
        assertEquals(String.valueOf(defaults.requiresUppercase().value()), settings.get("requiresUppercase"));
        assertEquals(String.valueOf(defaults.requiresLowercase().value()), settings.get("requiresLowercase"));
        assertEquals(String.valueOf(defaults.requiresDigit().value()),     settings.get("requiresDigit"));
        createPasswordHash = new CreatePasswordHash(STUB, defaults);
    }

    @Given("a custom password policy with minimum length {int} and special chars {string}")
    public void aCustomPasswordPolicyWithMinimumLengthAndSpecialChars(int minLengthValue, String specialCharsValue) {
        createPasswordHash = new CreatePasswordHash(STUB,
                new PasswordPolicy(new MinLength(minLengthValue), new SpecialChars(specialCharsValue)));
    }

    @When("the user provides password {string}")
    public void theUserProvidesPassword(String raw) {
        result = createPasswordHash.create(PlaintextPassword.of(raw));
    }

    @Then("the password is accepted")
    public void thePasswordIsAccepted() {
        assertInstanceOf(CreatePasswordHash.PasswordHashCreation.Created.class, result);
    }

    @Then("the password is rejected with an error containing {string}")
    public void thePasswordIsRejectedWithAnErrorContaining(String fragment) {
        assertInstanceOf(CreatePasswordHash.PasswordHashCreation.Rejected.class, result);
        CreatePasswordHash.PasswordHashCreation.Rejected rejected =
                (CreatePasswordHash.PasswordHashCreation.Rejected) result;
        assertTrue(
                rejected.errorCodes().stream().anyMatch(code -> code.contains(fragment)),
                String.format("Expected error code containing '%s' but got: %s", fragment, rejected.errorCodes())
        );
    }
}
