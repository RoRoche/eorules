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
import java.util.Set;
import org.cactoos.Scalar;

/**
 * Check if a method is declared in an interface.
 *
 * @since 0.0.1
 */
public final class IsDeclaredInInterfaces implements Scalar<Boolean> {

    /**
     * The implemented method.
     */
    private final JavaMethod method;

    /**
     * The methods declared in interfaces.
     */
    private final Set<JavaMethod> methods;

    public IsDeclaredInInterfaces(
        final JavaMethod method,
        final Set<JavaMethod> methods
    ) {
        this.method = method;
        this.methods = methods;
    }

    @Override
    public Boolean value() {
        return this.methods.stream().anyMatch(
            (final JavaMethod ifaceMethod) ->
                ifaceMethod.getName().equals(this.method.getName())
                    &&
                    new HaveSameParameterCount(ifaceMethod, this.method).value()
                    &&
                    new ParametersAssignableIgnoringGenerics(ifaceMethod, this.method).value()
        );
    }
}
