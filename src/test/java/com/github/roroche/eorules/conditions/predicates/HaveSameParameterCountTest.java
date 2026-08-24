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
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Mutation test for {@link HaveSameParameterCount}, verifying a boolean
 * branch that is difficult to distinguish through package-wide architecture
 * tests.
 * @since 0.0.3
 */
@SuppressWarnings({
    "allpublic",
    "JTCOP.RuleAssertionMessage",
    "JTCOP.RuleProhibitStaticFields",
    "staticfree",
    "allfinal"
})
final class HaveSameParameterCountTest {

    /**
     * Constant for run method name.
     * @since 0.0.3
     */
    private static final String RUN = "run";

    @Test
    void rejectsMethodsWithDifferentParameterCounts() {
        final JavaClasses classes = PredicateTestSupport.classes(
            Contract.class,
            WrongArity.class
        );
        MatcherAssert.assertThat(
            "Methods with different arities must not match",
            new HaveSameParameterCount(
                PredicateTestSupport.method(
                    classes,
                    Contract.class,
                    HaveSameParameterCountTest.RUN
                ),
                PredicateTestSupport.method(
                    classes,
                    WrongArity.class,
                    HaveSameParameterCountTest.RUN
                )
            ).value(),
            new IsEqual<>(false)
        );
    }
}
