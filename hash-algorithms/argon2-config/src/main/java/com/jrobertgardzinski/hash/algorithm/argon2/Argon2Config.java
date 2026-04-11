package com.jrobertgardzinski.hash.algorithm.argon2;

public record Argon2Config(Iterations iterations, MemLimitInKB memLimit, Parallelism parallelism) {

    public static Argon2Config withDefaults() {
        return builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        public static final int DEFAULT_ITERATIONS = 3;
        public static final int DEFAULT_MEM_LIMIT = 65536;
        public static final int DEFAULT_PARALLELISM = 1;

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
            return new Argon2Config(new Iterations(iterations), new MemLimitInKB(memLimit), new Parallelism(parallelism));
        }
    }
}
