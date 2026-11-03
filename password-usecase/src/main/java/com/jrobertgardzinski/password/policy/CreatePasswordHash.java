package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.HashAlgorithmPort;
import com.jrobertgardzinski.password.domain.HashedPassword;
import com.jrobertgardzinski.password.domain.PasswordError;
import com.jrobertgardzinski.password.domain.PlaintextPassword;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Judges a plaintext password against a policy and, if it passes, hashes it. There is no way to
 * obtain the hash of a password that failed: a refused one comes back as {@link Result.Failure}
 * with every broken rule at once, in policy order, so a form can show all of them in one round.
 */
public class CreatePasswordHash {

    private static final Pattern LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern DIGIT = Pattern.compile("\\d");

    private final HashAlgorithmPort hashAlgorithm;

    public CreatePasswordHash(HashAlgorithmPort hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public sealed interface Result {
        record Success(HashedPassword hashedPassword) implements Result {}
        record Failure(List<PasswordError> errors) implements Result {
            public Failure {
                errors = List.copyOf(errors);
            }
        }
    }

    public Result create(PlaintextPassword plaintextPassword, PasswordPolicy policy) {
        List<PasswordError> errors = new LinkedList<>();
        String password = plaintextPassword.value();
        if (password.length() < policy.minLength().value()) {
            errors.add(PasswordError.MIN_LENGTH_NOT_MET);
        }
        if (policy.requiresLowercase().value() && !LOWERCASE.matcher(password).find()) {
            errors.add(PasswordError.LOWERCASE_REQUIRED);
        }
        if (policy.requiresUppercase().value() && !UPPERCASE.matcher(password).find()) {
            errors.add(PasswordError.UPPERCASE_REQUIRED);
        }
        if (policy.requiresDigit().value() && !DIGIT.matcher(password).find()) {
            errors.add(PasswordError.DIGIT_REQUIRED);
        }
        String specialChars = policy.specialChars().value();
        if (password.chars().noneMatch(c -> specialChars.indexOf(c) >= 0)) {
            errors.add(PasswordError.SPECIAL_CHAR_REQUIRED);
        }

        if (errors.isEmpty()) {
            return new Result.Success(hashAlgorithm.hash(plaintextPassword));
        }
        else {
            return new Result.Failure(errors);
        }
    }
}
