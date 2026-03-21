package com.jrobertgardzinski.hash.algorithm.argon2;

import com.jrobertgardzinski.password.domain.HashAlgorithmPort;
import com.jrobertgardzinski.password.domain.HashedPassword;
import com.jrobertgardzinski.password.domain.PlaintextPassword;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Argon2HashAlgorithm implements HashAlgorithmPort {

    static final int ITERATIONS = 20;
    static final int MEM_LIMIT = 66536;
    static final int PARALLELISM = 1;
    static final Argon2 argon2 = Argon2Factory.create();

    @Override
    public HashedPassword hash(PlaintextPassword plaintextPassword) {
        String hash = argon2.hash(ITERATIONS, MEM_LIMIT, PARALLELISM, plaintextPassword.value().getBytes());
        return new HashedPassword(hash);
    }

    @Override
    public boolean verify(HashedPassword hashedPassword, PlaintextPassword plaintextPassword) {
        return argon2.verify(
                hashedPassword.value(),
                plaintextPassword.value().getBytes()
        );
    }
}
