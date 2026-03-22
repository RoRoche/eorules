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
package com.github.roroche.eorules.conditions;

import com.github.roroche.eorules.ExcludeFromArchUnit;
import com.github.roroche.eorules.conditions.messages.NotHavePrivateMethodsMessage;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

/**
 * {@link ArchCondition} to assert a {@link JavaClass} has no private methods.
 *
 * @since 0.0.1
 */
@ExcludeFromArchUnit
public final class NotHavePrivateMethods extends ArchCondition<JavaClass> {

    public NotHavePrivateMethods() {
        super("not have private methods");
    }

    @Override
    public void check(final JavaClass clazz, final ConditionEvents events) {
        clazz.getMethods()
            .stream()
            .filter(
                (final JavaMethod method) ->
                    method.getModifiers().contains(JavaModifier.PRIVATE)
                        &&
                        !method.reflect().isSynthetic()
            )
            .forEach(
                (final JavaMethod method) ->
                    events.add(
                        SimpleConditionEvent.violated(
                            method,
                            new NotHavePrivateMethodsMessage(clazz, method).toString()
                        )
                    )
            );
    }
}
