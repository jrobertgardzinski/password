package com.jrobertgardzinski.hash.algorithm.argon2;

import com.jrobertgardzinski.config.source.properties.PropertiesConfigPort;

public record Argon2Config(int iterations, int memLimit, int parallelism) {

    public Argon2Config {
        if (iterations < 1) throw new IllegalArgumentException("iterations must be at least 1");
        if (memLimit < 1) throw new IllegalArgumentException("memLimit must be at least 1");
        if (parallelism < 1) throw new IllegalArgumentException("parallelism must be at least 1");
    }

    public static Argon2Config from(PropertiesConfigPort<String> source) {
        return builder()
                .iterations(intOrDefault(source, "argon2.iterations", Builder.DEFAULT_ITERATIONS))
                .memLimit(intOrDefault(source, "argon2.memLimit", Builder.DEFAULT_MEM_LIMIT))
                .parallelism(intOrDefault(source, "argon2.parallelism", Builder.DEFAULT_PARALLELISM))
                .build();
    }

    private static int intOrDefault(PropertiesConfigPort<String> source, String key, int defaultValue) {
        String value = source.get(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        static final int DEFAULT_ITERATIONS = 20;
        static final int DEFAULT_MEM_LIMIT = 66536;
        static final int DEFAULT_PARALLELISM = 1;

        private int iterations = DEFAULT_ITERATIONS;
        private int memLimit = DEFAULT_MEM_LIMIT;
        private int parallelism = DEFAULT_PARALLELISM;

        public Builder iterations(int iterations) {
            this.iterations = iterations;
            return this;
        }

        public Builder memLimit(int memLimit) {
            this.memLimit = memLimit;
            return this;
        }

        public Builder parallelism(int parallelism) {
            this.parallelism = parallelism;
            return this;
        }

        public Argon2Config build() {
            return new Argon2Config(iterations, memLimit, parallelism);
        }
    }
}
