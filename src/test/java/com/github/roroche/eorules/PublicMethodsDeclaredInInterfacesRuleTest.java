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

import com.github.roroche.eorules.examples.invalid.ClassWithStaticMethod;
import com.github.roroche.eorules.examples.invalid.HasGetters;
import com.github.roroche.eorules.examples.invalid.HasSetter;
import com.github.roroche.eorules.examples.valid.BaseOperation;
import com.github.roroche.eorules.examples.valid.ChildOperation;
import com.github.roroche.eorules.examples.valid.GenericValue;
import com.github.roroche.eorules.examples.valid.IntegerOperation;
import com.github.roroche.eorules.examples.valid.ParentOperation;
import com.github.roroche.eorules.examples.valid.StringValue;
import com.github.roroche.eorules.matchers.HasViolationContaining;
import com.github.roroche.eorules.matchers.HasViolationCount;
import com.github.roroche.eorules.matchers.HasViolations;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import java.util.Arrays;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link PublicMethodsDeclaredInInterfacesRule}.
 * @since 0.0.1
 */
@SuppressWarnings("allpublic")
final class PublicMethodsDeclaredInInterfacesRuleTest {

    @Test
    void isOk() {
        MatcherAssert.assertThat(
            "Valid classes does not violate the rule",
            new PublicMethodsDeclaredInInterfacesRule().evaluate(
                new ClassFileImporter()
                    .importPackages("com.github.roroche.eorules.examples.valid")
            ),
            new AllOf<>(
                new IsNot<>(new HasViolations()),
                new HasViolationCount(0)
            )
        );
    }

    @Test
    void isNotOk() {
        MatcherAssert.assertThat(
            "Classes with public method not declared in interface violate the rule with message",
            new PublicMethodsDeclaredInInterfacesRule().evaluate(
                new ClassFileImporter().importClasses(
                    ClassWithStaticMethod.class,
                    HasGetters.class,
                    HasSetter.class
                )
            ),
            new AllOf<>(
                new HasViolations(),
                new HasViolationCount(4),
                new HasViolationContaining("getDescription"),
                new HasViolationContaining("getStaticDescription"),
                new HasViolationContaining("isInvalid"),
                new HasViolationContaining("setName")
            )
        );
    }

    @Test
    void exposesSyntheticBridgeMethodInFixture() {
        MatcherAssert.assertThat(
            "The fixture must expose a synthetic bridge method",
            Arrays.stream(StringValue.class.getDeclaredMethods())
                .anyMatch(java.lang.reflect.Method::isSynthetic),
            Matchers.is(true)
        );
    }

    @Test
    void acceptsSyntheticBridgeMethodDeclaredInInterface() {
        MatcherAssert.assertThat(
            "A public synthetic bridge method declared by an interface must not violate the rule",
            new PublicMethodsDeclaredInInterfacesRule().evaluate(
                new ClassFileImporter().importClasses(
                    GenericValue.class,
                    StringValue.class
                )
            ),
            new HasViolationCount(0)
        );
    }

    @Test
    void acceptsMethodDeclaredByInterfaceInheritedThroughSuperclass() {
        MatcherAssert.assertThat(
            "An inherited interface declaration should be recognised",
            new PublicMethodsDeclaredInInterfacesRule().evaluate(
                new ClassFileImporter().importClasses(
                    ParentOperation.class,
                    ChildOperation.class,
                    BaseOperation.class,
                    IntegerOperation.class
                )
            ),
            new HasViolationCount(0)
        );
    }
}
