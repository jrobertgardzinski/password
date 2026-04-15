package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.HashAlgorithmPort;
import com.jrobertgardzinski.password.domain.HashedPassword;
import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.security.config.MinLength;
import com.jrobertgardzinski.util.constraint.ErrorConstraint;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.jqwik.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Use case")
@Feature("Create Password Hash")
class CreatePasswordHashRulesTest {

    private static final PlaintextPassword ANY_PASSWORD = PlaintextPassword.of("P@ssw0rd1!");

    private static final List<ErrorConstraint<PlaintextPassword>> CONSTRAINTS = List.of(
            alwaysFailing(new _MinLengthConstraint(new MinLength(12))),
            alwaysFailing(new _ContainsUppercaseConstraint()),
            alwaysFailing(new _ContainsLowercaseConstraint()),
            alwaysFailing(new _ContainsDigitConstraint()),
            alwaysFailing(new _ContainsSpecialCharConstraint())
    );

    @DisplayName("Rejects with ")
    @ParameterizedTest(name = "{1} when \"{0}\" is unsatisfied")
    @MethodSource("constraintCases")
    void unsatisfiedConstraintCausesRejection(String name, String code, ErrorConstraint<PlaintextPassword> constraint) {
        CreatePasswordHash useCase = new CreatePasswordHash(stubAlgorithm(), List.of(constraint));
        CreatePasswordHash.PasswordHashCreation result = useCase.create(ANY_PASSWORD);
        assertThat(result).isInstanceOf(CreatePasswordHash.PasswordHashCreation.Rejected.class);
        assertThat(((CreatePasswordHash.PasswordHashCreation.Rejected) result).errorCodes()).contains(code);
    }

    static Stream<Arguments> constraintCases() {
        return CONSTRAINTS.stream().map(c -> Arguments.of(c.toString(), c.code(), c));
    }

    @Property(tries = 10)
    @Label("if any subset of CONSTRAINTS is unsatisfied → rejected with all their codes")
    void anySubsetOfUnsatisfiedConstraintsCausesRejection(
            @ForAll("constraintCombinations") Set<ErrorConstraint<PlaintextPassword>> brokenConstraints) {
        List<String> expectedCodes = brokenConstraints.stream().map(ErrorConstraint::code).toList();
        Allure.parameter("CONSTRAINTS", CONSTRAINTS);
        Allure.parameter("broken constraints", expectedCodes);

        CreatePasswordHash useCase = new CreatePasswordHash(stubAlgorithm(), new ArrayList<>(brokenConstraints));
        CreatePasswordHash.PasswordHashCreation result = useCase.create(ANY_PASSWORD);
        assertThat(result).isInstanceOf(CreatePasswordHash.PasswordHashCreation.Rejected.class);
        assertThat(((CreatePasswordHash.PasswordHashCreation.Rejected) result).errorCodes()).containsAll(expectedCodes);
    }

    @Example
    @Label("all constraints satisfied → created")
    void allConstraintsSatisfiedCreates() {
        CreatePasswordHash useCase = new CreatePasswordHash(stubAlgorithm(), List.of(passing()));
        assertThat(useCase.create(ANY_PASSWORD)).isInstanceOf(CreatePasswordHash.PasswordHashCreation.Created.class);
    }

    @Provide
    Arbitrary<Set<ErrorConstraint<PlaintextPassword>>> constraintCombinations() {
        return Arbitraries.subsetOf(CONSTRAINTS).ofMinSize(1);
    }

    // --- stubs ---

    private static ErrorConstraint<PlaintextPassword> alwaysFailing(ErrorConstraint<PlaintextPassword> delegate) {
        String name = delegate.getClass().getSimpleName();
        return new ErrorConstraint<>() {
            @Override public boolean isSatisfied(PlaintextPassword p) { return false; }
            @Override public String code() { return delegate.code(); }
            @Override public String toString() { return name; }
        };
    }

    private static ErrorConstraint<PlaintextPassword> passing() {
        return new ErrorConstraint<>() {
            @Override public boolean isSatisfied(PlaintextPassword p) { return true; }
            @Override public String code() { return "UNUSED"; }
        };
    }

    private static HashAlgorithmPort stubAlgorithm() {
        return new HashAlgorithmPort() {
            @Override public HashedPassword hash(PlaintextPassword p) { return new HashedPassword("stub"); }
            @Override public boolean verify(HashedPassword h, PlaintextPassword p) { return false; }
        };
    }
}
