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
 * Mutation test for {@link IsSameOrSubtype}, verifying boolean branches that
 * are difficult to distinguish through package-wide architecture tests.
 * @since 0.0.3
 */
@SuppressWarnings({"allpublic", "JTCOP.RuleAssertionMessage", "allfinal"})
final class IsSameOrSubtypeTest {

    @Test
    void rejectsUnrelatedClasses() {
        final JavaClasses classes = PredicateTestSupport.classes(
            Number.class,
            String.class
        );
        MatcherAssert.assertThat(
            "String is neither Number nor one of its subtypes",
            new IsSameOrSubtype(
                classes.get(String.class),
                classes.get(Number.class)
            ).value(),
            new IsEqual<>(false)
        );
    }

    @Test
    void acceptsImplementedInterface() {
        final JavaClasses classes = PredicateTestSupport.classes(
            Marker.class,
            Marked.class
        );
        MatcherAssert.assertThat(
            "Implemented interfaces must be detected",
            new IsSameOrSubtype(
                classes.get(Marked.class),
                classes.get(Marker.class)
            ).value(),
            new IsEqual<>(true)
        );
    }
}
