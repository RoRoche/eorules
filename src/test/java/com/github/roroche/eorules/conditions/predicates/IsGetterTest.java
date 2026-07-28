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
 * Test on {@link IsGetter}.
 * @since 0.0.3
 */
@SuppressWarnings({"allpublic", "JTCOP.RuleAssertionMessage", "allfinal"})
final class IsGetterTest {

    @Test
    void acceptsRegularGetter() {
        MatcherAssert.assertThat(
            "A regular no-argument accessor must be treated as a getter",
            new IsGetter(
                PredicateTestSupport.method(
                    PredicateTestSupport.classes(PredicateFixtures.StringGetter.class),
                    PredicateFixtures.StringGetter.class,
                    "getValue"
                )
            ).value(),
            new IsEqual<>(true)
        );
    }

    @Test
    void rejectsObjectGetClassMethod() {
        MatcherAssert.assertThat(
            "Object.getClass must not be treated as a getter",
            new IsGetter(
                PredicateTestSupport.method(
                    PredicateTestSupport.classes(Object.class),
                    Object.class,
                    "getClass"
                )
            ).value(),
            new IsEqual<>(false)
        );
    }

    @Test
    void rejectsGetterWithParameter() {
        MatcherAssert.assertThat(
            "A get-method with a parameter must not be treated as a getter",
            new IsGetter(
                PredicateTestSupport.method(
                    PredicateTestSupport.classes(PredicateFixtures.Accessors.class),
                    PredicateFixtures.Accessors.class,
                    "getNamed"
                )
            ).value(),
            new IsEqual<>(false)
        );
    }

    @Test
    void rejectsVoidGetter() {
        MatcherAssert.assertThat(
            "A void get-method must not be treated as a getter",
            new IsGetter(
                PredicateTestSupport.method(
                    PredicateTestSupport.classes(PredicateFixtures.Accessors.class),
                    PredicateFixtures.Accessors.class,
                    "getNothing"
                )
            ).value(),
            new IsEqual<>(false)
        );
    }

    @Test
    void rejectsSyntheticGetter() {
        MatcherAssert.assertThat(
            "A synthetic accessor must not be treated as a getter",
            new IsGetter(
                PredicateTestSupport.synthetic(
                    PredicateTestSupport.method(
                        PredicateTestSupport.classes(
                            PredicateFixtures.StringGetter.class
                        ),
                        PredicateFixtures.StringGetter.class,
                        "getValue"
                    ),
                    PredicateFixtures.StringGetter.class,
                    "getValue"
                )
            ).value(),
            new IsEqual<>(false)
        );
    }
}
