package com.example.designpattern.builder;

import org.apache.commons.lang3.StringUtils;

public class ResourcePoolConfig {
    private String name;
    private int maxTotal;
    private int maxIdle;
    private int minIdle;

    private ResourcePoolConfig(Builder builder) {
        this.name = builder.name;
        this.maxTotal = builder.maxTotal;
        this.maxIdle = builder.maxIdle;
        this.minIdle = builder.minIdle;
    }

    public static class Builder {
        private static final int DEFAULT_MAX_TOTAL = 8;
        private static final int DEFAULT_MAX_IDLE = 8;
        private static final int DEFAULT_MIN_IDLE = 0;
        private String name;
        private int maxTotal = DEFAULT_MAX_TOTAL;
        private int maxIdle = DEFAULT_MAX_IDLE;
        private int minIdle = DEFAULT_MIN_IDLE;

        public ResourcePoolConfig build() {
            if (StringUtils.isBlank(name)) {
                throw new IllegalArgumentException("Name could not blank");
            }
            if (maxIdle > maxTotal) {
                throw new IllegalArgumentException("Too Big maxIdle");
            }
            if (minIdle > maxTotal || minIdle > maxIdle) {
                throw new IllegalArgumentException("Too Big minIdle");
            }

            return new ResourcePoolConfig(this);
        }

        public Builder setName(String name) {
            if (StringUtils.isBlank(name)) {
                throw new IllegalArgumentException("Name IllegalArgumentException");
            }

            this.name = name;
            return this;
        }

        public Builder setMaxTotal(int maxTotal) {
            if (maxTotal <= 0) {
                throw new IllegalArgumentException("Illegal argument MaxTotal");
            }
            this.maxTotal = maxTotal;

            return this;
        }

        public Builder setMaxIdle(int maxIdle) {
            if (maxIdle < 0) {
                throw new IllegalArgumentException("Illegal argument maxIdle");
            }
            this.maxIdle = maxIdle;

            return this;
        }


        public Builder setMinIdle(int minIdle) {
            if (minIdle < 0) {
                throw new IllegalArgumentException("Illegal argument maxIdle");
            }
            this.minIdle = minIdle;

            return this;
        }
    }
}
