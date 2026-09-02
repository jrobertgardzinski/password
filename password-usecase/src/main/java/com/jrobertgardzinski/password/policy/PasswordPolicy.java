package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.config.*;
import com.jrobertgardzinski.util.constraint.ErrorConstraint;

import java.util.ArrayList;
import java.util.List;

public record PasswordPolicy(
        MinLength minLength,
        SpecialChars specialChars,
        RequiresUppercase requiresUppercase,
        RequiresLowercase requiresLowercase,
        RequiresDigit requiresDigit
) {

    public static PasswordPolicy withDefaults() {
        return new PasswordPolicy(
                MinLength.DEFAULT,
                SpecialChars.DEFAULT,
                RequiresUppercase.DEFAULT,
                RequiresLowercase.DEFAULT,
                RequiresDigit.DEFAULT
        );
    }

    /**
     * {@link #withDefaults() The default policy} with these two rules replaced. The other three —
     * uppercase, lowercase, digit — keep their {@code DEFAULT}; the name says so, where a
     * two-argument constructor used to hide it.
     */
    public static PasswordPolicy defaultsExcept(MinLength minLength, SpecialChars specialChars) {
        return new PasswordPolicy(minLength, specialChars,
                RequiresUppercase.DEFAULT, RequiresLowercase.DEFAULT, RequiresDigit.DEFAULT);
    }

    List<ErrorConstraint<PlaintextPassword>> constraints() {
        var list = new ArrayList<ErrorConstraint<PlaintextPassword>>();
        list.add(new _MinLengthConstraint(minLength));
        if (requiresLowercase.value()) list.add(new _ContainsLowercaseConstraint());
        if (requiresUppercase.value()) list.add(new _ContainsUppercaseConstraint());
        if (requiresDigit.value())     list.add(new _ContainsDigitConstraint());
        list.add(new _ContainsSpecialCharConstraint(specialChars));
        return List.copyOf(list);
    }
}
