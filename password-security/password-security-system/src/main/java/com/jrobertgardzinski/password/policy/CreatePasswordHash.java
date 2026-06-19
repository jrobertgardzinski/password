package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.HashAlgorithmPort;
import com.jrobertgardzinski.password.domain.HashedPassword;
import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.util.constraint.Constraints;
import com.jrobertgardzinski.util.constraint.ErrorConstraint;
import com.jrobertgardzinski.util.constraint.Outcome;

import java.util.List;
import java.util.function.Supplier;

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

    public Outcome<HashedPassword> create(Supplier<PlaintextPassword> password) {
        Outcome<PlaintextPassword> result = constraints.validate(password);
        return result.map(hashAlgorithm::hash);
    }
}
