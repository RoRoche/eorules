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

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for predicates whose boolean branches are difficult
 * to distinguish through package-wide architecture tests.
 * @since 0.0.1
 */
@SuppressWarnings({
    "allpublic",
    "JTCOP.RuleEveryTestHasProductionClass",
    "JTCOP.RuleAssertionMessage",
    "JTCOP.RuleProhibitStaticFields",
    "PMD.TooManyMethods",
    "staticfree",
    "allfinal"
})
final class PredicatesMutationTest {

    /**
     * Constant for run method name.
     * @since 0.0.2
     */
    private static final String RUN = "run";

    @Test
    void rejectsMethodsWithDifferentParameterCounts() {
        final JavaClasses classes = PredicatesMutationTest.classes(
            Contract.class,
            WrongArity.class
        );
        Assertions.assertFalse(
            new HaveSameParameterCount(
                PredicatesMutationTest.method(classes, Contract.class, PredicatesMutationTest.RUN),
                PredicatesMutationTest.method(classes, WrongArity.class, PredicatesMutationTest.RUN)
            ).value(),
            "Methods with different arities must not match"
        );
    }

    @Test
    void rejectsMethodsWithUnrelatedParameterTypes() {
        final JavaClasses classes = PredicatesMutationTest.classes(
            Contract.class,
            WrongType.class
        );
        Assertions.assertFalse(
            new ParametersAssignableIgnoringGenerics(
                PredicatesMutationTest.method(classes, Contract.class, PredicatesMutationTest.RUN),
                PredicatesMutationTest.method(classes, WrongType.class, PredicatesMutationTest.RUN)
            ).value(),
            "Unrelated parameter types must not be assignable"
        );
    }

    @Test
    void acceptsSubtypeParameter() {
        final JavaClasses classes = PredicatesMutationTest.classes(
            Contract.class,
            NarrowType.class
        );
        Assertions.assertTrue(
            new ParametersAssignableIgnoringGenerics(
                PredicatesMutationTest.method(classes, Contract.class, PredicatesMutationTest.RUN),
                PredicatesMutationTest.method(classes, NarrowType.class, PredicatesMutationTest.RUN)
            ).value(),
            "A subtype must be accepted by the current rule semantics"
        );
    }

    @Test
    void rejectsUnrelatedClasses() {
        final JavaClasses classes = PredicatesMutationTest.classes(
            Number.class,
            String.class
        );
        Assertions.assertFalse(
            new IsSameOrSubtype(
                classes.get(String.class),
                classes.get(Number.class)
            ).value(),
            "String is neither Number nor one of its subtypes"
        );
    }

    @Test
    void acceptsImplementedInterface() {
        final JavaClasses classes = PredicatesMutationTest.classes(
            Marker.class,
            Marked.class
        );
        Assertions.assertTrue(
            new IsSameOrSubtype(
                classes.get(Marked.class),
                classes.get(Marker.class)
            ).value(),
            "Implemented interfaces must be detected"
        );
    }

    @Test
    void rejectsMethodWithSameNameButDifferentArity() {
        final JavaClasses classes = PredicatesMutationTest.classes(
            Contract.class,
            WrongArity.class
        );
        Assertions.assertFalse(
            new IsDeclaredInInterfaces(
                PredicatesMutationTest.method(
                    classes,
                    WrongArity.class,
                    PredicatesMutationTest.RUN
                ),
                Set.of(
                    PredicatesMutationTest.method(
                        classes,
                        Contract.class,
                        PredicatesMutationTest.RUN
                    )
                )
            ).value(),
            "A same-named method with a different arity is not declared by the interface"
        );
    }

    @Test
    void rejectsMethodWithSameNameAndUnrelatedType() {
        final JavaClasses classes = PredicatesMutationTest.classes(
            Contract.class,
            WrongType.class
        );
        Assertions.assertFalse(
            new IsDeclaredInInterfaces(
                PredicatesMutationTest.method(classes, WrongType.class, PredicatesMutationTest.RUN),
                Set.of(
                    PredicatesMutationTest.method(
                        classes,
                        Contract.class,
                        PredicatesMutationTest.RUN
                    )
                )
            ).value(),
            "A same-named method with an unrelated parameter type is not declared"
        );
    }

    @Test
    void acceptsExactInterfaceMethod() {
        final JavaClasses classes = PredicatesMutationTest.classes(
            Contract.class,
            ExactType.class
        );
        Assertions.assertTrue(
            new IsDeclaredInInterfaces(
                PredicatesMutationTest.method(classes, ExactType.class, PredicatesMutationTest.RUN),
                Set.of(
                    PredicatesMutationTest.method(
                        classes,
                        Contract.class,
                        PredicatesMutationTest.RUN
                    )
                )
            ).value(),
            "An exact implementation must be declared by its interface"
        );
    }

    @Test
    void rejectsOrdinaryMethodAsAllowedStaticMethod() {
        Assertions.assertFalse(
            new IsAllowedStaticMethod(
                PredicatesMutationTest.method(
                    PredicatesMutationTest.classes(StaticMethods.class),
                    StaticMethods.class,
                    "ordinary"
                )
            ).value(),
            "An ordinary static method must not be treated as compiler generated"
        );
    }

    @Test
    void acceptsDollarPrefixedStaticMethod() {
        Assertions.assertTrue(
            new IsAllowedStaticMethod(
                PredicatesMutationTest.method(
                    PredicatesMutationTest.classes(
                        StaticMethods.class
                    ),
                    StaticMethods.class,
                    "$generated"
                )
            ).value(),
            "A dollar-prefixed method must be treated as compiler generated"
        );
    }

    @Test
    void distinguishesValidMainFromWrongSignatureIsOk() {
        Assertions.assertTrue(
            new HasCorrectMainSignature(
                PredicatesMutationTest.method(
                    PredicatesMutationTest.classes(
                        MainMethods.class
                    ),
                    MainMethods.class,
                    "main"
                )
            ).value(),
            "String array is the expected main parameter"
        );
    }

    @Test
    void distinguishesValidMainFromWrongSignatureIsNotOk() {
        Assertions.assertFalse(
            new HasCorrectMainSignature(
                PredicatesMutationTest.method(
                    PredicatesMutationTest.classes(
                        MainMethods.class
                    ),
                    MainMethods.class,
                    "wrongMain"
                )
            ).value(),
            "A String parameter must not be accepted as a main signature"
        );
    }

    @Test
    void distinguishesPublicStaticMainFromWrongModifiersIsOk() {
        Assertions.assertTrue(
            new HasCorrectMainModifiers(
                PredicatesMutationTest.method(
                    PredicatesMutationTest.classes(
                        MainMethods.class
                    ),
                    MainMethods.class,
                    "main"
                )
            ).value(),
            "A public static main method has the expected modifiers"
        );
    }

    @Test
    void distinguishesPublicStaticMainFromWrongModifiersIsNotOk() {
        Assertions.assertFalse(
            new HasCorrectMainModifiers(
                PredicatesMutationTest.method(
                    PredicatesMutationTest.classes(
                        MainMethods.class
                    ),
                    MainMethods.class,
                    "instanceMain"
                )
            ).value(),
            "An instance method must not have valid main modifiers"
        );
    }

    @Test
    void distinguishesMainNameFromOtherNamesIsOk() {
        Assertions.assertTrue(
            new IsNamedMain(
                PredicatesMutationTest.method(
                    PredicatesMutationTest.classes(
                        MainMethods.class
                    ),
                    MainMethods.class,
                    "main"
                )
            ).value(),
            "The main method must be recognised by name"
        );
    }

    @Test
    void distinguishesMainNameFromOtherNamesIsNotOk() {
        Assertions.assertFalse(
            new IsNamedMain(
                PredicatesMutationTest.method(
                    PredicatesMutationTest.classes(
                        MainMethods.class
                    ),
                    MainMethods.class,
                    "wrongMain"
                )
            ).value(),
            "Another method name must not be recognised as main"
        );
    }

    @Test
    void distinguishesBooleanGetterFromNonBooleanIsMethodIsOk() throws Exception {
        Assertions.assertTrue(
            new IsIs(
                PredicatesMutationTest.method(
                    PredicatesMutationTest.classes(
                        Accessors.class
                    ),
                    Accessors.class,
                    "isReady"
                )
            ).value(),
            "A no-argument boolean is-method is a getter"
        );
    }

    @Test
    void distinguishesBooleanGetterFromNonBooleanIsMethodIsNotOK() throws Exception {
        Assertions.assertFalse(
            new IsIs(
                PredicatesMutationTest.method(
                    PredicatesMutationTest.classes(
                        Accessors.class
                    ),
                    Accessors.class,
                    "isName"
                )
            ).value(),
            "A String-returning is-method is not a getter"
        );
    }

    @Test
    void distinguishesSetterFromFluentMethodIsOk() {
        Assertions.assertTrue(
            new IsSetter(
                PredicatesMutationTest.method(
                    PredicatesMutationTest.classes(
                        Accessors.class
                    ),
                    Accessors.class,
                    "setName"
                )
            ).value(),
            "A one-argument void set-method is a setter"
        );
    }

    @Test
    void distinguishesSetterFromFluentMethodIsNotOk() {
        Assertions.assertFalse(
            new IsSetter(
                PredicatesMutationTest.method(
                    PredicatesMutationTest.classes(
                        Accessors.class
                    ),
                    Accessors.class,
                    "setFluently"
                )
            ).value(),
            "A fluent set-method is not a setter"
        );
    }

    private static JavaClasses classes(final Class<?>... types) {
        return new ClassFileImporter().importClasses(types);
    }

    private static JavaMethod method(
        final JavaClasses classes,
        final Class<?> owner,
        final String name
    ) {
        return classes.get(owner).getMethods().stream()
            .filter(method -> method.getName().equals(name))
            .findFirst()
            .orElseThrow();
    }

    @FunctionalInterface
    private interface Contract {

        void run(Number value);
    }

    private static final class ExactType {

        @SuppressWarnings("PMD.PublicMemberInNonPublicType")
        public void run(final Number value) {
            // Intentionally empty.
        }
    }

    private static final class NarrowType {

        @SuppressWarnings("PMD.PublicMemberInNonPublicType")
        public void run(final Integer value) {
            // Intentionally empty.
        }
    }

    @SuppressWarnings("PMD.PublicMemberInNonPublicType")
    private static final class WrongType {

        public void run(final String value) {
            // Intentionally empty.
        }
    }

    @SuppressWarnings("PMD.PublicMemberInNonPublicType")
    private static final class WrongArity {

        public void run(final Number first, final Number second) {
            // Intentionally empty.
        }
    }

    private interface Marker {
    }

    private static final class Marked implements Marker {
    }

    @SuppressWarnings({"PMD.PublicMemberInNonPublicType", "PMD.ProhibitPublicStaticMethods"})
    private static final class StaticMethods {

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

    @SuppressWarnings({"PMD.PublicMemberInNonPublicType", "PMD.ProhibitPublicStaticMethods"})
    private static final class MainMethods {

        public static void main(final String[] args) {
            // Intentionally empty.
        }

        public static void wrongMain(final String args) {
            // Intentionally empty.
        }

        @SuppressWarnings("PMD.UseVarargs")
        public void instanceMain(final String[] args) {
            // Intentionally empty.
        }
    }

    @SuppressWarnings("PMD.PublicMemberInNonPublicType")
    private static final class Accessors {

        public boolean isReady() {
            return true;
        }

        public String isName() {
            return "name";
        }

        public void setName(final String name) {
            // Intentionally empty.
        }

        public Accessors setFluently(final String name) {
            return this;
        }
    }
}
