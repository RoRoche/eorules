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

import com.tngtech.archunit.core.domain.JavaMethod;
import java.util.stream.IntStream;
import org.cactoos.Scalar;

/**
 * Compare parameters ignoring generics, and check that
 * each implementation type is the same or a subtype of the interface type.
 *
 * @since 0.0.1
 */
public final class ParametersAssignableIgnoringGenerics implements Scalar<Boolean> {

    /**
     * The interface method.
     */
    private final JavaMethod declared;

    /**
     * The implemented method.
     */
    private final JavaMethod implemented;

    public ParametersAssignableIgnoringGenerics(
        final JavaMethod declared,
        final JavaMethod implemented
    ) {
        this.declared = declared;
        this.implemented = implemented;
    }

    @Override
    public Boolean value() {
        return IntStream.range(0, this.declared.getRawParameterTypes().size()).allMatch(
            (final int i) ->
                new IsSameOrSubtype(
                    this.implemented.getRawParameterTypes().get(i).toErasure(),
                    this.declared.getRawParameterTypes().get(i).toErasure()
                ).value()
        );
    }
}
