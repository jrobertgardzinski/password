package com.jrobertgardzinski.password.policy.ladder;

import com.jrobertgardzinski.password.config.MinLength;

public interface MinLengthRepository {

    void save(MinLength minLength);
}
