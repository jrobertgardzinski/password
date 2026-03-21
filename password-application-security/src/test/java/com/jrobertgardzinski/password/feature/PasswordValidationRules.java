package com.jrobertgardzinski.password.feature;

import com.jrobertgardzinski.password.domain.HashAlgorithmPort;
import com.jrobertgardzinski.password.domain.HashedPassword;
import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.policy.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

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

    @Given("the default password policy is active")
    public void theDefaultPasswordPolicyIsActive() {
        createPasswordHash = new CreatePasswordHash(STUB, List.of(
                new _MinLengthConstraint(12),
                new _ContainsLowercaseConstraint(),
                new _ContainsUppercaseConstraint(),
                new _ContainsDigitConstraint(),
                new _ContainsSpecialCharConstraint("#?!")
        ));
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
