package com.jrobertgardzinski.password.policy.ladder;

import com.jrobertgardzinski.config.ladder.ConfigLadder;
import com.jrobertgardzinski.password.policy.PasswordPolicy;
import com.jrobertgardzinski.password.config.MinLength;
import com.jrobertgardzinski.password.config.RequiresDigit;
import com.jrobertgardzinski.password.config.RequiresLowercase;
import com.jrobertgardzinski.password.config.RequiresUppercase;
import com.jrobertgardzinski.password.config.SpecialChars;

/**
 * The password policy in force, assembled per attempt from the configuration ladder. All FIVE rules
 * are spelled out here on purpose: the policy is not "minimum length plus special characters", and
 * a convenience constructor filling the rest in silently is how that misreading was born.
 *
 * <p>Today exactly one rule moves while the system runs, and the table says which:
 * <ul>
 *   <li><b>minimum length</b> — the full ladder ({@link MinLengthLadder}): a settings row an ADMIN
 *       wrote (live) over the {@value MinLengthLadder#KEY} property (restart) over
 *       {@link MinLength#DEFAULT} (rebuild);</li>
 *   <li><b>special characters</b> — rebuild rung only: {@link SpecialChars#DEFAULT};</li>
 *   <li><b>uppercase required</b> — rebuild rung only: {@link RequiresUppercase#DEFAULT} (true);</li>
 *   <li><b>lowercase required</b> — rebuild rung only: {@link RequiresLowercase#DEFAULT} (true);</li>
 *   <li><b>digit required</b> — rebuild rung only: {@link RequiresDigit#DEFAULT} (true).</li>
 * </ul>
 * A rule that grows a ladder of its own gets an argument in this constructor and a line in this
 * table; nothing else has to change.
 */
public final class LadderedPasswordPolicy implements PasswordPolicyInForce {

    private final ConfigLadder<Integer> minLength;

    public LadderedPasswordPolicy(ConfigLadder<Integer> minLength) {
        this.minLength = minLength;
    }

    @Override
    public PasswordPolicy current() {
        return new PasswordPolicy(
                new MinLength(minLength.resolve()),
                SpecialChars.DEFAULT,
                RequiresUppercase.DEFAULT,
                RequiresLowercase.DEFAULT,
                RequiresDigit.DEFAULT);
    }
}
