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

import com.tngtech.archunit.core.domain.JavaClass;
import org.cactoos.Scalar;

/**
 * Simulate isAssignableFrom for ArchUnit 1.4.x:
 * returns true if implClass == ifaceClass or implClass inherits/implements ifaceClass.
 * @since 0.0.1
 */
public final class IsSameOrSubtype implements Scalar<Boolean> {

    /**
     * The implemented class.
     */
    private final JavaClass clazz;

    /**
     * The interface.
     */
    private final JavaClass iface;

    /**
     * Ctor.
     * @param clazz The implemented class
     * @param iface The interface
     */
    public IsSameOrSubtype(final JavaClass clazz, final JavaClass iface) {
        this.clazz = clazz;
        this.iface = iface;
    }

    @SuppressWarnings("allfinal")
    @Override
    public Boolean value() {
        boolean result = false;
        if (this.clazz.equals(this.iface)) {
            result = true;
        } else if (this.clazz.getAllRawSuperclasses().contains(this.iface)) {
            result = true;
        } else {
            for (final JavaClass implemented : this.clazz.getAllRawInterfaces()) {
                if (implemented.equals(this.iface)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
