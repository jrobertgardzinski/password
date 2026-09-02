package com.jrobertgardzinski.password.policy.ladder;

import com.jrobertgardzinski.password.config.MinLength;

/**
 * An ADMIN sets the minimum password length while the system runs. The value object is the gate:
 * a length below its floor is refused here, at the door, and nothing is written.
 */
public class SetMinPasswordLength {

    /** The settings key this use case writes — the same key the ladder reads. */
    public static final String KEY = MinLengthLadder.KEY;

    public enum Status { ACCEPTED, REFUSED }

    public record Result(Status status, MinLength minLength, String reason) {
        static Result accepted(MinLength minLength) {
            return new Result(Status.ACCEPTED, minLength, "");
        }
        static Result refused(String reason) {
            return new Result(Status.REFUSED, null, reason);
        }
    }

    private final MinLengthRepository store;

    public SetMinPasswordLength(MinLengthRepository store) {
        this.store = store;
    }

    public Result execute(int requested) {
        MinLength minLength;
        try {
            minLength = new MinLength(requested);
        } catch (IllegalArgumentException refused) {
            return Result.refused(refused.getMessage());
        }
        store.save(minLength);
        return Result.accepted(minLength);
    }
}
