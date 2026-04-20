package com.jrobertgardzinski.hash.algorithm.argon2;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

@Epic("Hash Algorithms")
@Feature("Argon2 Configuration - Memory Limit")
class MemLimitInKBRulesTest extends BoundedIntRulesTest {

    @Override
    protected int min() {
        return MemLimitInKB.MIN;
    }

    @Override
    protected Object create(int value) {
        return new MemLimitInKB(value);
    }

    @Override
    protected int getValue(Object instance) {
        return ((MemLimitInKB) instance).value();
    }
}
