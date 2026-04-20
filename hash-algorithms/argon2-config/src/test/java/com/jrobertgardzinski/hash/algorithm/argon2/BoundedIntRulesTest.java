package com.jrobertgardzinski.hash.algorithm.argon2;

import io.qameta.allure.Allure;
import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class BoundedIntRulesTest {

    protected abstract int min();
    protected abstract Object create(int value);
    protected abstract int getValue(Object instance);

    @Property
    @Label("Invariant: accepts valid values")
    void acceptsValidValues(@ForAll("validValues") Tuple.Tuple2<String, Integer> boundary) {
        Allure.parameter(boundary.get1(), boundary.get2());
        int value = boundary.get2();
        Object instance = create(value);
        assertThat(getValue(instance)).isEqualTo(value);
    }

    @Property
    @Label("Invariant: rejects invalid values")
    void rejectsInvalidValues(@ForAll("invalidValues") Tuple.Tuple2<String, Integer> boundary) {
        Allure.parameter(boundary.get1(), boundary.get2());
        int value = boundary.get2();
        assertThrows(IllegalArgumentException.class, () -> create(value));
    }

    @Provide
    Arbitrary<Tuple.Tuple2<String, Integer>> validValues() {
        return Arbitraries.of(
                Tuple.of("MIN", min())
        );
    }

    @Provide
    Arbitrary<Tuple.Tuple2<String, Integer>> invalidValues() {
        return Arbitraries.of(
                Tuple.of("MIN - 1", min() - 1)
        );
    }
}
