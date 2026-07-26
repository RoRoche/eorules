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
package com.github.roroche.eorules.matchers;

import com.tngtech.archunit.lang.EvaluationResult;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher to count the number of violations.
 * @since 0.0.2
 */
public final class HasViolationCount extends TypeSafeDiagnosingMatcher<EvaluationResult> {

    /**
     * Expected number of violations.
     */
    private final int expected;

    /**
     * Primary ctor.
     * @param expected Expected number of violations
     */
    public HasViolationCount(final int expected) {
        this.expected = expected;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("an evaluation result with ")
            .appendValue(this.expected)
            .appendText(" violation(s)");
    }

    @Override
    public boolean matchesSafely(
        final EvaluationResult result,
        final Description mismatch
    ) {
        final int actual = result.getFailureReport().getDetails().size();
        mismatch.appendText("had ").appendValue(actual).appendText(" violation(s)");
        return actual == this.expected;
    }
}
