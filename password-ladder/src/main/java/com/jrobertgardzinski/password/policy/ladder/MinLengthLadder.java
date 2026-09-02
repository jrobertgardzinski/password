package com.jrobertgardzinski.password.policy.ladder;

import com.jrobertgardzinski.config.ladder.ConfigLadder;
import com.jrobertgardzinski.config.source.live.LiveConfigPort;
import com.jrobertgardzinski.config.source.live.LiveConfigSource;
import com.jrobertgardzinski.config.source.restart.RestartConfigPort;
import com.jrobertgardzinski.config.source.restart.RestartConfigSource;
import com.jrobertgardzinski.password.config.MinLength;

public final class MinLengthLadder {

    public static final String KEY = "security.password.policy.min.length";

    private MinLengthLadder() {}

    public static ConfigLadder<Integer> over(LiveConfigPort<Integer> live, RestartConfigPort<Integer> restart) {
        return ConfigLadder.live(
                KEY, MinLength.DEFAULT.value(),
                length -> new MinLength(length),
                new LiveConfigSource<>(live), new RestartConfigSource<>(restart));
    }
}
