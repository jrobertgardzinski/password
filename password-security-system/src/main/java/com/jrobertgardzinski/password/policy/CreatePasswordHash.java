package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.HashAlgorithmPort;
import com.jrobertgardzinski.password.domain.HashedPassword;
import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.util.constraint.Constraint;

import java.util.List;

public class CreatePasswordHash {

    private final HashAlgorithmPort hashAlgorithm;
    private final PasswordPolicy policy;

    public CreatePasswordHash(HashAlgorithmPort hashAlgorithm, PasswordPolicy policy) {
        this.hashAlgorithm = hashAlgorithm;
        this.policy = policy;
    }

    public PasswordHashCreation create(PlaintextPassword password) {
        List<String> codes = policy.constraints().stream()
                .filter(c -> !c.isSatisfied(password))
                .map(Constraint::code)
                .toList();
        if (!codes.isEmpty()) {
            return new PasswordHashCreation.Rejected(codes);
        }
        return new PasswordHashCreation.Created(hashAlgorithm.hash(password));
    }

    public sealed interface PasswordHashCreation {
        record Created(HashedPassword hashedPassword) implements PasswordHashCreation {}
        record Rejected(List<String> errorCodes) implements PasswordHashCreation {}
    }
}
