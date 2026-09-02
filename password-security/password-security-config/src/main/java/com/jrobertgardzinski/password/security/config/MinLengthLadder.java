package com.jrobertgardzinski.password.security.config;

import com.jrobertgardzinski.config.ladder.ConfigLadder;
import com.jrobertgardzinski.config.source.live.LiveConfigPort;
import com.jrobertgardzinski.config.source.live.LiveConfigSource;
import com.jrobertgardzinski.config.source.restart.RestartConfigPort;
import com.jrobertgardzinski.config.source.restart.RestartConfigSource;

/**
 * How a {@link MinLength} is resolved at runtime: the ladder definition that belongs next to the
 * value it produces. The value stays a value — {@link MinLength} never fetches anything — and the
 * ladder is where "which source answers" is decided: a live row an ADMIN wrote, over the
 * {@value #KEY} property a deployment set, over {@link MinLength#DEFAULT} shipped with the build.
 *
 * <p>The ladder speaks the raw number and applies {@link MinLength}'s constructor as its gate on
 * every rung, so a row that holds 3 is refused, logged and fallen through — and reported as such
 * by {@link ConfigLadder#resolution()} — rather than blowing up where it was read.
 *
 * <p>The ports arrive as they are; whoever assembles the system decides what stands behind them
 * (a database, a cache with a TTL, an in-memory map, the JVM's properties).
 */
public final class MinLengthLadder {

    /** The one key, on every rung: property name, settings-row name, ladder name. */
    public static final String KEY = "security.password.policy.min.length";

    private MinLengthLadder() {}

    public static ConfigLadder<Integer> over(LiveConfigPort<Integer> live, RestartConfigPort<Integer> restart) {
        return ConfigLadder.live(
                KEY, MinLength.DEFAULT.value(),
                length -> new MinLength(length),
                new LiveConfigSource<>(live), new RestartConfigSource<>(restart));
    }
}
