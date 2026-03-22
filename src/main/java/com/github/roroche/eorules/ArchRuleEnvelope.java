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

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.EvaluationResult;

/**
 * Wrapper for {@link ArchRule}.
 *
 * @since 0.0.1
 */
public abstract class ArchRuleEnvelope implements ArchRule {

    /**
     * The wrapped {@link ArchRule} to be decorated.
     */
    private final ArchRule delegate;

    protected ArchRuleEnvelope(final ArchRule delegate) {
        this.delegate = delegate;
    }

    @Override
    public final void check(final JavaClasses classes) {
        this.delegate.check(classes);
    }

    @Override
    public final ArchRule because(final String reason) {
        return this.delegate.because(reason);
    }

    @Override
    public final ArchRule allowEmptyShould(final boolean allow) {
        return this.delegate.allowEmptyShould(allow);
    }

    @SuppressWarnings("PMD.ShortMethodName")
    @Override
    public final ArchRule as(final String description) {
        return this.delegate.as(description);
    }

    @Override
    public final EvaluationResult evaluate(final JavaClasses classes) {
        return this.delegate.evaluate(classes);
    }

    @Override
    public final String getDescription() {
        return this.delegate.getDescription();
    }
}
