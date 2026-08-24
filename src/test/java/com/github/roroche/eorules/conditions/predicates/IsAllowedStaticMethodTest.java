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

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Mutation test for {@link IsAllowedStaticMethod}, verifying boolean
 * branches that are difficult to distinguish through package-wide
 * architecture tests.
 * @since 0.0.3
 */
@SuppressWarnings({"allpublic", "JTCOP.RuleAssertionMessage", "allfinal"})
final class IsAllowedStaticMethodTest {

    @Test
    void rejectsOrdinaryMethodAsAllowedStaticMethod() {
        MatcherAssert.assertThat(
            "An ordinary static method must not be treated as compiler generated",
            new IsAllowedStaticMethod(
                PredicateTestSupport.method(
                    PredicateTestSupport.classes(StaticMethods.class),
                    StaticMethods.class,
                    "ordinary"
                )
            ).value(),
            new IsEqual<>(false)
        );
    }

    @Test
    void acceptsDollarPrefixedStaticMethod() {
        MatcherAssert.assertThat(
            "A dollar-prefixed method must be treated as compiler generated",
            new IsAllowedStaticMethod(
                PredicateTestSupport.method(
                    PredicateTestSupport.classes(StaticMethods.class),
                    StaticMethods.class,
                    "$generated"
                )
            ).value(),
            new IsEqual<>(true)
        );
    }

    @Test
    void acceptsSyntheticMethod() {
        MatcherAssert.assertThat(
            "A synthetic static method must be treated as compiler generated",
            new IsAllowedStaticMethod(
                PredicateTestSupport.synthetic(
                    PredicateTestSupport.method(
                        PredicateTestSupport.classes(
                            StaticMethods.class
                        ),
                        StaticMethods.class,
                        "ordinary"
                    ),
                    StaticMethods.class,
                    "lambda$action$0"
                )
            ).value(),
            new IsEqual<>(true)
        );
    }
}
