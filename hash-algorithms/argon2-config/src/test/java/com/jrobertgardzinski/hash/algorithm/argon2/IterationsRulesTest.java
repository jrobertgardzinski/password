package com.jrobertgardzinski.hash.algorithm.argon2;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

@Epic("Hash Algorithms")
@Feature("Argon2 Configuration - Iterations")
class IterationsRulesTest extends BoundedIntRulesTest {

    @Override
    protected int min() {
        return Iterations.BOUNDARY;
    }

    @Override
    protected Object create(int value) {
        return new Iterations(value);
    }

    @Override
    protected int getValue(Object instance) {
        return ((Iterations) instance).value();
    }
}
