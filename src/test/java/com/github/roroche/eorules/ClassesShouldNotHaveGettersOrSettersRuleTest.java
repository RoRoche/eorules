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

import com.github.roroche.eorules.matchers.HasViolationContaining;
import com.github.roroche.eorules.matchers.HasViolations;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;

/**
 * Test on {@link ClassesShouldNotHaveGettersOrSettersRule}.
 *
 * @since 0.0.1
 */
@SuppressWarnings("allpublic")
final class ClassesShouldNotHaveGettersOrSettersRuleTest {
    @Test
    void isOk() {
        MatcherAssert.assertThat(
            "Valid classes does not violate the rule",
            new ClassesShouldNotHaveGettersOrSettersRule().evaluate(
                new ClassFileImporter()
                    .importPackages("com.github.roroche.eorules.examples.valid")
            ),
            new IsNot<>(new HasViolations())
        );
    }

    @Test
    void isNotOk() {
        MatcherAssert.assertThat(
            "Classes that have getters or setters should violate the rule with message",
            new ClassesShouldNotHaveGettersOrSettersRule().evaluate(
                new ClassFileImporter()
                    .importPackages("com.github.roroche.eorules.examples.invalid")
            ),
            new AllOf<>(
                new HasViolations(),
                new HasViolationContaining("getDescription"),
                new HasViolationContaining("isInvalid"),
                new HasViolationContaining("setName")
            )
        );
    }
}
