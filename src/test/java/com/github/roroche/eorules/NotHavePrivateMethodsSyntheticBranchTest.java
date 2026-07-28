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

import com.github.roroche.eorules.examples.valid.SyntheticPrivateMethodExample;
import com.github.roroche.eorules.matchers.HasViolationCount;
import com.github.roroche.eorules.matchers.HasViolations;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;

/**
 * Tests the synthetic branch of the private-method condition.
 * @since 0.0.1
 */
@SuppressWarnings({"allpublic", "JTCOP.RuleEveryTestHasProductionClass"})
final class NotHavePrivateMethodsSyntheticBranchTest {

    @Test
    void ignoresSyntheticPrivateMethodFromFixture() {
        MatcherAssert.assertThat(
            "The fixture must contain a private synthetic method",
            Arrays.stream(
                SyntheticPrivateMethodExample.class.getDeclaredMethods()
            ).anyMatch(
                method -> Modifier.isPrivate(method.getModifiers())
                    && method.isSynthetic()
            ),
            new IsEqual<>(true)
        );
    }

    @Test
    void ignoresSyntheticPrivateMethod() {
        MatcherAssert.assertThat(
            "A private synthetic method must not violate the rule",
            new ClassesShouldNotHavePrivateMethodsRule().evaluate(
                new ClassFileImporter().importClasses(
                    SyntheticPrivateMethodExample.class
                )
            ),
            new AllOf<>(
                new IsNot<>(new HasViolations()),
                new HasViolationCount(0)
            )
        );
    }
}
