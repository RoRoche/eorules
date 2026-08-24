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
 * Mutation test for {@link IsSetter}, verifying boolean branches that are
 * difficult to distinguish through package-wide architecture tests.
 * @since 0.0.3
 */
@SuppressWarnings({"allpublic", "JTCOP.RuleAssertionMessage", "allfinal"})
final class IsSetterTest {

    @Test
    void distinguishesSetterFromFluentMethodIsOk() {
        MatcherAssert.assertThat(
            "A one-argument void set-method is a setter",
            new IsSetter(
                PredicateTestSupport.method(
                    PredicateTestSupport.classes(Accessors.class),
                    Accessors.class,
                    "setName"
                )
            ).value(),
            new IsEqual<>(true)
        );
    }

    @Test
    void distinguishesSetterFromFluentMethodIsNotOk() {
        MatcherAssert.assertThat(
            "A fluent set-method is not a setter",
            new IsSetter(
                PredicateTestSupport.method(
                    PredicateTestSupport.classes(Accessors.class),
                    Accessors.class,
                    "setFluently"
                )
            ).value(),
            new IsEqual<>(false)
        );
    }

    @Test
    void rejectsSetterWithoutParameter() {
        MatcherAssert.assertThat(
            "A set-method without a parameter must not be treated as a setter",
            new IsSetter(
                PredicateTestSupport.method(
                    PredicateTestSupport.classes(Accessors.class),
                    Accessors.class,
                    "setNothing"
                )
            ).value(),
            new IsEqual<>(false)
        );
    }

    @Test
    void rejectsSyntheticSetter() {
        MatcherAssert.assertThat(
            "A synthetic mutator must not be treated as a setter",
            new IsSetter(
                PredicateTestSupport.synthetic(
                    PredicateTestSupport.method(
                        PredicateTestSupport.classes(
                            Accessors.class
                        ),
                        Accessors.class,
                        "setName"
                    ),
                    StringSetter.class,
                    "setValue"
                )
            ).value(),
            new IsEqual<>(false)
        );
    }
}
