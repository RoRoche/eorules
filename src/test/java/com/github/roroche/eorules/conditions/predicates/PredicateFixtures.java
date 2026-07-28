/*
 * MIT License
 *
 * Copyright (c) 2026 Romain Rochegude
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.roroche.eorules.conditions.predicates;

/**
 * Fixture types shared by several predicate mutation tests.
 * @since 0.0.3
 */
@SuppressWarnings({
    "allpublic",
    "allfinal",
    "staticfree",
    "PMD.MissingStaticMethodInNonInstantiatableClass"
})
final class PredicateFixtures {

    /**
     * Only nested fixture types, no instances.
     */
    private PredicateFixtures() {
    }

    /**
     * Reference contract exposing a single-argument {@code run} method.
     * @since 0.0.3
     */
    @FunctionalInterface
    interface Contract {

        void run(Number value);
    }

    /**
     * Implements {@link Contract} with the exact same parameter type.
     * @since 0.0.3
     */
    static final class ExactType {

        /*
         * @checkstyle NonStaticMethodCheck (5 lines)
         */
        @SuppressWarnings("PMD.PublicMemberInNonPublicType")
        public void run(final Number value) {
            // Intentionally empty.
        }
    }

    /**
     * Implements {@link Contract} with a narrower parameter type.
     * @since 0.0.3
     */
    static final class NarrowType {

        /*
         * @checkstyle NonStaticMethodCheck (5 lines)
         */
        @SuppressWarnings("PMD.PublicMemberInNonPublicType")
        public void run(final Integer value) {
            // Intentionally empty.
        }
    }

    /**
     * Declares a same-named method with an unrelated parameter type.
     * @since 0.0.3
     */
    @SuppressWarnings("PMD.PublicMemberInNonPublicType")
    static final class WrongType {

        /*
         * @checkstyle NonStaticMethodCheck (4 lines)
         */
        public void run(final String value) {
            // Intentionally empty.
        }
    }

    /**
     * Declares a same-named method with a different arity.
     * @since 0.0.3
     */
    @SuppressWarnings("PMD.PublicMemberInNonPublicType")
    static final class WrongArity {

        /*
         * @checkstyle NonStaticMethodCheck (4 lines)
         */
        public void run(final Number first, final Number second) {
            // Intentionally empty.
        }
    }

    /**
     * Marker interface used to test subtype detection.
     * @since 0.0.3
     */
    interface Marker {
    }

    /**
     * Implements {@link Marker}.
     * @since 0.0.3
     */
    static final class Marked implements Marker {
    }

    /**
     * Static methods, some of which are compiler-generated-looking.
     * @since 0.0.3
     */
    @SuppressWarnings({"PMD.PublicMemberInNonPublicType", "PMD.ProhibitPublicStaticMethods"})
    static final class StaticMethods {

        private StaticMethods() {
            // Intentionally empty.
        }

        public static void ordinary() {
            // Intentionally empty.
        }

        @SuppressWarnings({"PMD.MethodNamingConventions", "PMD.AvoidDollarSigns"})
        // @checkstyle MethodNameCheck (4 lines)
        public static void $generated() {
            // Intentionally empty.
        }

        public static Runnable action() {
            return () -> { };
        }
    }

    /**
     * Generic getter contract used by regular getter tests.
     * @param <T> Value type
     * @since 0.0.3
     */
    @FunctionalInterface
    interface GenericGetter<T> {

        T getValue();
    }

    /**
     * String getter implementation.
     * @since 0.0.3
     */
    static final class StringGetter implements GenericGetter<String> {

        @Override
        public String getValue() {
            return "value";
        }
    }

    /**
     * Candidate {@code main} methods with varying signatures and modifiers.
     * @since 0.0.3
     */
    @SuppressWarnings({"PMD.PublicMemberInNonPublicType", "PMD.ProhibitPublicStaticMethods"})
    static final class MainMethods {

        public static void main(final String[] args) {
            // Intentionally empty.
        }

        public static void wrongMain(final String args) {
            // Intentionally empty.
        }

        /*
         * @checkstyle NonStaticMethodCheck (5 lines)
         */
        @SuppressWarnings("PMD.UseVarargs")
        public void instanceMain(final String[] args) {
            // Intentionally empty.
        }
    }

    /**
     * Accessor-like methods, some of which are not real getters or setters.
     * @since 0.0.3
     */
    @SuppressWarnings("PMD.PublicMemberInNonPublicType")
    static final class Accessors {

        /*
         * @checkstyle NonStaticMethodCheck (4 lines)
         */
        public boolean isReady() {
            return true;
        }

        /*
         * @checkstyle NonStaticMethodCheck (4 lines)
         */
        public String isName() {
            return "name";
        }

        /*
         * @checkstyle NonStaticMethodCheck (4 lines)
         */
        public Boolean isEnabled() {
            return Boolean.TRUE;
        }

        /*
         * @checkstyle NonStaticMethodCheck (4 lines)
         */
        public void setName(final String name) {
            // Intentionally empty.
        }

        public Accessors setFluently(final String name) {
            return this;
        }
    }
}
