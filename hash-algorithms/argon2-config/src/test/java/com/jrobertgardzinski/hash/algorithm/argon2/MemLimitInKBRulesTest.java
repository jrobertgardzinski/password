package com.jrobertgardzinski.hash.algorithm.argon2;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Epic("Hash Algorithms")
@Feature("Argon2 Configuration - MemLimitInKB")
class MemLimitInKBRulesTest {

    @Property
    @Label("Invariant: accepts valid values")
    void acceptsValidValues(@ForAll("validValues") Tuple.Tuple2<String, Integer> boundary) {
        Allure.parameter(boundary.get1(), boundary.get2());
        int value = boundary.get2();
        assertThat(new MemLimitInKB(value).value()).isEqualTo(value);
    }

    @Property
    @Label("Invariant: rejects invalid values")
    void rejectsInvalidValues(@ForAll("invalidValues") Tuple.Tuple2<String, Integer> boundary) {
        Allure.parameter(boundary.get1(), boundary.get2());
        int value = boundary.get2();
        assertThrows(IllegalArgumentException.class, () -> new MemLimitInKB(value));
    }

    @Provide
    Arbitrary<Tuple.Tuple2<String, Integer>> validValues() {
        return Arbitraries.of(
                Tuple.of("MIN", MemLimitInKB.MIN),
                Tuple.of("MAX", MemLimitInKB.MAX)
        );
    }

    @Provide
    Arbitrary<Tuple.Tuple2<String, Integer>> invalidValues() {
        return Arbitraries.of(
                Tuple.of("MIN - 1", MemLimitInKB.MIN - 1),
                Tuple.of("MAX + 1", MemLimitInKB.MAX + 1)
        );
    }
}
