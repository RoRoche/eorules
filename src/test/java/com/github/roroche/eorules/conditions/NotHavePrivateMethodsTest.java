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
package com.github.roroche.eorules.conditions;

import com.github.roroche.eorules.examples.valid.SyntheticPrivateMethodExample;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ConditionEvents;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Tests {@link NotHavePrivateMethods}.
 * @since 0.0.3
 */
@SuppressWarnings({
    "allpublic",
    "allfinal",
    "JTCOP.RuleTestCaseContainsMockery",
    "PMD.UnitTestContainsTooManyAsserts"
})
final class NotHavePrivateMethodsTest {

    @Test
    void ignoresSyntheticPrivateMethodFromFixture() {
        final Method reflected = Arrays.stream(
            SyntheticPrivateMethodExample.class.getDeclaredMethods()
        ).filter(
            Method::isSynthetic
        ).findFirst().orElseThrow();
        final JavaMethod method = Mockito.mock(JavaMethod.class);
        Mockito.doReturn(
            Set.of(JavaModifier.PRIVATE)
        ).when(
            method
        ).getModifiers();
        Mockito.doReturn(
            reflected
        ).when(
            method
        ).reflect();
        final JavaClass clazz = Mockito.mock(JavaClass.class);
        Mockito.doReturn(
            Set.of(method)
        ).when(
            clazz
        ).getMethods();
        new NotHavePrivateMethods().check(clazz, Mockito.mock(ConditionEvents.class));
        MatcherAssert.assertThat(
            "The fixture method must be synthetic",
            reflected.isSynthetic(),
            new IsEqual<>(true)
        );
    }

    @Test
    void ignoresSyntheticPrivateMethod() {
        final JavaMethod method = Mockito.mock(JavaMethod.class);
        Mockito.doReturn(
            Set.of(JavaModifier.PRIVATE)
        ).when(
            method
        ).getModifiers();
        Mockito.doReturn(
            Arrays.stream(
                SyntheticPrivateMethodExample.class.getDeclaredMethods()
            ).filter(
                Method::isSynthetic
            ).findFirst().orElseThrow()
        ).when(
            method
        ).reflect();
        final JavaClass clazz = Mockito.mock(JavaClass.class);
        Mockito.doReturn(
            Set.of(method)
        ).when(
            clazz
        ).getMethods();
        final ConditionEvents events = Mockito.mock(ConditionEvents.class);
        new NotHavePrivateMethods().check(clazz, events);
        MatcherAssert.assertThat(
            "A private synthetic method must not produce a violation",
            Mockito.mockingDetails(events).getInvocations().isEmpty(),
            new IsEqual<>(true)
        );
    }
}
