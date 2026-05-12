package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.HashAlgorithmPort;
import com.jrobertgardzinski.password.domain.HashedPassword;
import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.util.constraint.Constraints;
import com.jrobertgardzinski.util.constraint.Decision;
import com.jrobertgardzinski.util.constraint.ErrorConstraint;

import java.util.List;

public class CreatePasswordHash {

    private final HashAlgorithmPort hashAlgorithm;
    private final Constraints<PlaintextPassword> constraints;

    public CreatePasswordHash(HashAlgorithmPort hashAlgorithm, PasswordPolicy policy) {
        this(hashAlgorithm, policy.constraints());
    }

    CreatePasswordHash(HashAlgorithmPort hashAlgorithm, List<ErrorConstraint<PlaintextPassword>> constraints) {
        this.hashAlgorithm = hashAlgorithm;
        this.constraints = new Constraints<>(constraints);
    }

    public PasswordHashCreation create(PlaintextPassword password) {
        return switch (constraints.decide(password)) {
            case Decision.Rejected r -> new PasswordHashCreation.Rejected(r.errorCodes());
            case Decision.Allowed ignored -> new PasswordHashCreation.Created(hashAlgorithm.hash(password));
            case Decision.AllowedWithWarning ignored -> new PasswordHashCreation.Created(hashAlgorithm.hash(password));
        };
    }

    public sealed interface PasswordHashCreation {
        record Created(HashedPassword hashedPassword) implements PasswordHashCreation {}
        record Rejected(List<String> errorCodes) implements PasswordHashCreation {}
    }
}
