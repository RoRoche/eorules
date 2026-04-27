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
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher for {@link EvaluationResult} with a violation containing a specific string.
 * @since 0.0.1
 */
public final class HasViolationContaining extends TypeSafeMatcher<EvaluationResult> {

    /**
     * The expected string that should be contained in the violation report.
     */
    private final String expected;

    /**
     * Constructs a new matcher with the expected string.
     * @param expected The string that should be contained in the violation report
     */
    public HasViolationContaining(final String expected) {
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(final EvaluationResult result) {
        return result.getFailureReport().toString().contains(this.expected);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("a violation containing ")
            .appendValue(this.expected);
    }

    @Override
    public void describeMismatchSafely(
        final EvaluationResult result,
        final Description description
    ) {
        description.appendText("was ")
            .appendValue(result.getFailureReport().toString());
    }
}
