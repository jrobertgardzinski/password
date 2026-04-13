package com.jrobertgardzinski.password.policy;

import com.jrobertgardzinski.password.domain.PlaintextPassword;
import com.jrobertgardzinski.password.security.config.*;
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
                new MinLength(12),
                SpecialChars.DEFAULT,
                RequiresUppercase.DEFAULT,
                RequiresLowercase.DEFAULT,
                RequiresDigit.DEFAULT
        );
    }

    public PasswordPolicy(MinLength minLength, SpecialChars specialChars) {
        this(minLength, specialChars, RequiresUppercase.DEFAULT, RequiresLowercase.DEFAULT, RequiresDigit.DEFAULT);
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
