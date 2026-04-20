package com.jrobertgardzinski.hash.algorithm.argon2;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

@Epic("Hash Algorithms")
@Feature("Argon2 Configuration - Parallelism")
class ParallelismRulesTest extends BoundedIntRulesTest {

    @Override
    protected int min() {
        return Parallelism.MIN;
    }

    @Override
    protected Object create(int value) {
        return new Parallelism(value);
    }

    @Override
    protected int getValue(Object instance) {
        return ((Parallelism) instance).value();
    }
}
