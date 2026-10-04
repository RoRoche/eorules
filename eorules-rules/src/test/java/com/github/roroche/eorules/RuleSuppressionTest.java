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
package com.github.roroche.eorules;

import com.github.roroche.eorules.matchers.HasViolationCount;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Tests fine-grained rule suppression.
 *
 * @since 0.0.2
 */
@SuppressWarnings({
    "allpublic",
    "staticfree",
    "allfinal",
    "PMD.JUnitTestClassShouldBeFinal",
    "PMD.ProhibitPublicStaticMethods",
    "PMD.PublicMemberInNonPublicType",
    "PMD.UncommentedEmptyMethodBody",
    "PMD.UnusedPrivateField",
    "PMD.UnusedPrivateMethod",
    "PMD.UseUtilityClass",
    "UnusedMethod",
    "UnusedVariable",
    "JTCOP.RuleEveryTestHasProductionClass"
})
final class RuleSuppressionTest {

    @Test
    void reportsClassThatIsNotAbstractNorFinalWithoutSuppression() {
        MatcherAssert.assertThat(
            "Class finality rule should report a class without suppression",
            new ClassesAreAbstractOrFinalRule().evaluate(
                new ClassFileImporter().importClasses(MutableType.class)
            ),
            new HasViolationCount(1)
        );
    }

    @Test
    void suppressesClassThatIsNotAbstractNorFinalWithOwnKey() {
        MatcherAssert.assertThat(
            "Class finality rule should ignore a class carrying its key",
            new ClassesAreAbstractOrFinalRule().evaluate(
                new ClassFileImporter().importClasses(SuppressedMutableType.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void reportsClassThatIsNotAbstractNorFinalWithWrongKey() {
        MatcherAssert.assertThat(
            "Class finality rule should report a class carrying another key",
            new ClassesAreAbstractOrFinalRule().evaluate(
                new ClassFileImporter().importClasses(WronglySuppressedMutableType.class)
            ),
            new HasViolationCount(1)
        );
    }

    @Test
    void suppressesClassThatIsNotAbstractNorFinalWithMultipleKeys() {
        MatcherAssert.assertThat(
            "Class finality rule should ignore a class carrying multiple keys including its key",
            new ClassesAreAbstractOrFinalRule().evaluate(
                new ClassFileImporter().importClasses(MultiplySuppressedMutableType.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void reportsStaticMethodWithoutSuppression() {
        MatcherAssert.assertThat(
            "Static-method rule should report a static method without suppression",
            new ClassesShouldHaveNoStaticMethodsRule().evaluate(
                new ClassFileImporter().importClasses(StaticMethod.class)
            ),
            new HasViolationCount(1)
        );
    }

    @Test
    void suppressesStaticMethodAtClassLevel() {
        MatcherAssert.assertThat(
            "Static-method rule should ignore a class carrying its key",
            new ClassesShouldHaveNoStaticMethodsRule().evaluate(
                new ClassFileImporter().importClasses(ClassSuppressedStaticMethod.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void suppressesStaticMethodAtMemberLevel() {
        MatcherAssert.assertThat(
            "Static-method rule should ignore a static method carrying its key",
            new ClassesShouldHaveNoStaticMethodsRule().evaluate(
                new ClassFileImporter().importClasses(MemberSuppressedStaticMethod.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void reportsStaticMethodWithWrongKey() {
        MatcherAssert.assertThat(
            "Static-method rule should report a static method carrying another key",
            new ClassesShouldHaveNoStaticMethodsRule().evaluate(
                new ClassFileImporter().importClasses(WronglySuppressedStaticMethod.class)
            ),
            new HasViolationCount(1)
        );
    }

    @Test
    void suppressesStaticMethodWithMultipleKeys() {
        MatcherAssert.assertThat(
            "Static-method rule should ignore a static method carrying multiple keys including its key",
            new ClassesShouldHaveNoStaticMethodsRule().evaluate(
                new ClassFileImporter().importClasses(MultiplySuppressedStaticMethod.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void reportsGetterWithoutSuppression() {
        MatcherAssert.assertThat(
            "Accessor rule should report a getter without suppression",
            new ClassesShouldNotHaveGettersOrSettersRule().evaluate(
                new ClassFileImporter().importClasses(GetterMethod.class)
            ),
            new HasViolationCount(1)
        );
    }

    @Test
    void suppressesGetterAtClassLevel() {
        MatcherAssert.assertThat(
            "Accessor rule should ignore a class carrying its key",
            new ClassesShouldNotHaveGettersOrSettersRule().evaluate(
                new ClassFileImporter().importClasses(ClassSuppressedGetter.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void suppressesGetterAtMemberLevel() {
        MatcherAssert.assertThat(
            "Accessor rule should ignore a getter carrying its key",
            new ClassesShouldNotHaveGettersOrSettersRule().evaluate(
                new ClassFileImporter().importClasses(MemberSuppressedGetter.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void reportsGetterWithWrongKey() {
        MatcherAssert.assertThat(
            "Accessor rule should report a getter carrying another key",
            new ClassesShouldNotHaveGettersOrSettersRule().evaluate(
                new ClassFileImporter().importClasses(WronglySuppressedGetter.class)
            ),
            new HasViolationCount(1)
        );
    }

    @Test
    void suppressesGetterWithMultipleKeys() {
        MatcherAssert.assertThat(
            "Accessor rule should ignore a getter carrying multiple keys including its key",
            new ClassesShouldNotHaveGettersOrSettersRule().evaluate(
                new ClassFileImporter().importClasses(MultiplySuppressedGetter.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void reportsPrivateMethodWithoutSuppression() {
        MatcherAssert.assertThat(
            "Private-method rule should report a private method without suppression",
            new ClassesShouldNotHavePrivateMethodsRule().evaluate(
                new ClassFileImporter().importClasses(PrivateMethod.class)
            ),
            new HasViolationCount(1)
        );
    }

    @Test
    void suppressesPrivateMethodAtClassLevel() {
        MatcherAssert.assertThat(
            "Private-method rule should ignore a class carrying its key",
            new ClassesShouldNotHavePrivateMethodsRule().evaluate(
                new ClassFileImporter().importClasses(ClassSuppressedPrivateMethod.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void suppressesPrivateMethodAtMemberLevel() {
        MatcherAssert.assertThat(
            "Private-method rule should ignore a private method carrying its key",
            new ClassesShouldNotHavePrivateMethodsRule().evaluate(
                new ClassFileImporter().importClasses(MemberSuppressedPrivateMethod.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void reportsPrivateMethodWithWrongKey() {
        MatcherAssert.assertThat(
            "Private-method rule should report a private method carrying another key",
            new ClassesShouldNotHavePrivateMethodsRule().evaluate(
                new ClassFileImporter().importClasses(WronglySuppressedPrivateMethod.class)
            ),
            new HasViolationCount(1)
        );
    }

    @Test
    void suppressesPrivateMethodWithMultipleKeys() {
        MatcherAssert.assertThat(
            "Private-method rule should ignore a private method carrying multiple keys including its key",
            new ClassesShouldNotHavePrivateMethodsRule().evaluate(
                new ClassFileImporter().importClasses(MultiplySuppressedPrivateMethod.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void reportsNonFinalFieldWithoutSuppression() {
        MatcherAssert.assertThat(
            "Field-finality rule should report a field without suppression",
            new FieldsShouldBeFinalRule().evaluate(
                new ClassFileImporter().importClasses(NonFinalField.class)
            ),
            new HasViolationCount(1)
        );
    }

    @Test
    void suppressesNonFinalFieldAtClassLevel() {
        MatcherAssert.assertThat(
            "Field-finality rule should ignore a class carrying its key",
            new FieldsShouldBeFinalRule().evaluate(
                new ClassFileImporter().importClasses(ClassSuppressedNonFinalField.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void suppressesNonFinalFieldAtMemberLevel() {
        MatcherAssert.assertThat(
            "Field-finality rule should ignore a field carrying its key",
            new FieldsShouldBeFinalRule().evaluate(
                new ClassFileImporter().importClasses(MemberSuppressedNonFinalField.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void reportsNonFinalFieldWithWrongKey() {
        MatcherAssert.assertThat(
            "Field-finality rule should report a field carrying another key",
            new FieldsShouldBeFinalRule().evaluate(
                new ClassFileImporter().importClasses(WronglySuppressedNonFinalField.class)
            ),
            new HasViolationCount(1)
        );
    }

    @Test
    void suppressesNonFinalFieldWithMultipleKeys() {
        MatcherAssert.assertThat(
            "Field-finality rule should ignore a field carrying multiple keys including its key",
            new FieldsShouldBeFinalRule().evaluate(
                new ClassFileImporter().importClasses(MultiplySuppressedNonFinalField.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void reportsPublicMethodWithoutSuppression() {
        MatcherAssert.assertThat(
            "Interface-contract rule should report a public method without suppression",
            new PublicMethodsDeclaredInInterfacesRule().evaluate(
                new ClassFileImporter().importClasses(PublicMethod.class)
            ),
            new HasViolationCount(1)
        );
    }

    @Test
    void suppressesPublicMethodAtClassLevel() {
        MatcherAssert.assertThat(
            "Interface-contract rule should ignore a class carrying its key",
            new PublicMethodsDeclaredInInterfacesRule().evaluate(
                new ClassFileImporter().importClasses(ClassSuppressedPublicMethod.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void suppressesPublicMethodAtMemberLevel() {
        MatcherAssert.assertThat(
            "Interface-contract rule should ignore a public method carrying its key",
            new PublicMethodsDeclaredInInterfacesRule().evaluate(
                new ClassFileImporter().importClasses(MemberSuppressedPublicMethod.class)
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void reportsPublicMethodWithWrongKey() {
        MatcherAssert.assertThat(
            "Interface-contract rule should report a public method carrying another key",
            new PublicMethodsDeclaredInInterfacesRule().evaluate(
                new ClassFileImporter().importClasses(WronglySuppressedPublicMethod.class)
            ),
            new HasViolationCount(1)
        );
    }

    @Test
    void suppressesPublicMethodWithMultipleKeys() {
        MatcherAssert.assertThat(
            "Interface-contract rule should ignore a public method carrying multiple keys including its key",
            new PublicMethodsDeclaredInInterfacesRule().evaluate(
                new ClassFileImporter().importClasses(MultiplySuppressedPublicMethod.class)
            ),
            new HasViolationCount(0)
        );
    }

    static class MutableType {
    }

    @SuppressEoRule(SuppressEoRule.CLASS_FINALITY)
    static class SuppressedMutableType {
    }

    @SuppressEoRule(SuppressEoRule.NO_STATIC_METHODS)
    static class WronglySuppressedMutableType {
    }

    @SuppressEoRule({
        SuppressEoRule.NO_STATIC_METHODS,
        SuppressEoRule.CLASS_FINALITY
    })
    static class MultiplySuppressedMutableType {
    }

    static final class StaticMethod {

        public static void action() {
        }
    }

    @SuppressEoRule(SuppressEoRule.NO_STATIC_METHODS)
    static final class ClassSuppressedStaticMethod {

        public static void action() {
        }
    }

    static final class MemberSuppressedStaticMethod {

        @SuppressEoRule(SuppressEoRule.NO_STATIC_METHODS)
        public static void action() {
        }
    }

    static final class WronglySuppressedStaticMethod {

        @SuppressEoRule(SuppressEoRule.NO_ACCESSORS)
        public static void action() {
        }
    }

    static final class MultiplySuppressedStaticMethod {

        @SuppressEoRule({
            SuppressEoRule.NO_ACCESSORS,
            SuppressEoRule.NO_STATIC_METHODS
        })
        public static void action() {
        }
    }

    static final class GetterMethod {

        public String getName() {
            return "name";
        }
    }

    @SuppressEoRule(SuppressEoRule.NO_ACCESSORS)
    static final class ClassSuppressedGetter {

        public String getName() {
            return "name";
        }
    }

    static final class MemberSuppressedGetter {

        @SuppressEoRule(SuppressEoRule.NO_ACCESSORS)
        public String getName() {
            return "name";
        }
    }

    static final class WronglySuppressedGetter {

        @SuppressEoRule(SuppressEoRule.NO_PRIVATES)
        public String getName() {
            return "name";
        }
    }

    static final class MultiplySuppressedGetter {

        @SuppressEoRule({
            SuppressEoRule.NO_PRIVATES,
            SuppressEoRule.NO_ACCESSORS
        })
        public String getName() {
            return "name";
        }
    }

    static final class PrivateMethod {

        private void action() {
        }
    }

    @SuppressEoRule(SuppressEoRule.NO_PRIVATES)
    static final class ClassSuppressedPrivateMethod {

        private void action() {
        }
    }

    static final class MemberSuppressedPrivateMethod {

        @SuppressEoRule(SuppressEoRule.NO_PRIVATES)
        private void action() {
        }
    }

    static final class WronglySuppressedPrivateMethod {

        @SuppressEoRule(SuppressEoRule.FIELDS_FINAL)
        private void action() {
        }
    }

    static final class MultiplySuppressedPrivateMethod {

        @SuppressEoRule({
            SuppressEoRule.FIELDS_FINAL,
            SuppressEoRule.NO_PRIVATES
        })
        private void action() {
        }
    }

    static final class NonFinalField {

        /**
         * Non-final field.
         *
         * @since 0.0.1
         */
        private String name;
    }

    @SuppressEoRule(SuppressEoRule.FIELDS_FINAL)
    static final class ClassSuppressedNonFinalField {

        /**
         * Non-final field.
         *
         * @since 0.0.1
         */
        private String name;
    }

    static final class MemberSuppressedNonFinalField {

        /**
         * Non-final field.
         *
         * @since 0.0.1
         */
        @SuppressEoRule(SuppressEoRule.FIELDS_FINAL)
        private String name;
    }

    static final class WronglySuppressedNonFinalField {

        /**
         * Non-final field.
         *
         * @since 0.0.1
         */
        @SuppressEoRule(SuppressEoRule.PUBLIC_CONTRACTS)
        private String name;
    }

    static final class MultiplySuppressedNonFinalField {

        /**
         * Non-final field.
         *
         * @since 0.0.1
         */
        @SuppressEoRule({
            SuppressEoRule.PUBLIC_CONTRACTS,
            SuppressEoRule.FIELDS_FINAL
        })
        private String name;
    }

    static final class PublicMethod {

        public void action() {
        }
    }

    @SuppressEoRule(SuppressEoRule.PUBLIC_CONTRACTS)
    static final class ClassSuppressedPublicMethod {

        public void action() {
        }
    }

    static final class MemberSuppressedPublicMethod {

        @SuppressEoRule(SuppressEoRule.PUBLIC_CONTRACTS)
        public void action() {
        }
    }

    static final class WronglySuppressedPublicMethod {

        @SuppressEoRule(SuppressEoRule.CLASS_FINALITY)
        public void action() {
        }
    }

    static final class MultiplySuppressedPublicMethod {

        @SuppressEoRule({
            SuppressEoRule.CLASS_FINALITY,
            SuppressEoRule.PUBLIC_CONTRACTS
        })
        public void action() {
        }
    }
}
