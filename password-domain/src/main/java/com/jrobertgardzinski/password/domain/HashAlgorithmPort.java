package com.jrobertgardzinski.password.domain;

public interface HashAlgorithmPort {
    HashedPassword hash(PlaintextPassword plaintextPassword);
    boolean verify(HashedPassword hashedPassword, PlaintextPassword plaintextPassword);
}
