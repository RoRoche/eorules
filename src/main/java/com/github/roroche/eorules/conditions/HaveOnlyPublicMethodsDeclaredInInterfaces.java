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
import com.github.roroche.eorules.conditions.collections.InterfaceMethods;
import com.github.roroche.eorules.conditions.messages.PublicMethodsDeclaredInInterfacesMessage;
import com.github.roroche.eorules.conditions.predicates.IsDeclaredInInterfaces;
import com.github.roroche.eorules.conditions.predicates.IsMainMethod;
import com.github.roroche.eorules.conditions.predicates.IsObjectMethod;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

/**
 * {@link ArchCondition} to assert a {@link JavaClass}
 * has public methods only declared in interfaces.
 * @since 0.0.1
 */
@ExcludeFromArchUnit
public final class HaveOnlyPublicMethodsDeclaredInInterfaces extends ArchCondition<JavaClass> {

    public HaveOnlyPublicMethodsDeclaredInInterfaces() {
        super("have only public methods declared in implemented interfaces");
    }

    @Override
    public void check(final JavaClass clazz, final ConditionEvents events) {
        if (!clazz.isInterface()) {
            clazz.getMethods().stream().filter(
                (final JavaMethod method) ->
                    method.getModifiers().contains(JavaModifier.PUBLIC)
            ).filter(
                (final JavaMethod method) ->
                    !new IsObjectMethod(method).value() && !new IsMainMethod(method).value()
            ).filter(
                (final JavaMethod method) ->
                    !new IsDeclaredInInterfaces(method, new InterfaceMethods(clazz)).value()
            ).forEach(
                (final JavaMethod method) ->
                    events.add(
                        SimpleConditionEvent.violated(
                            method,
                            new PublicMethodsDeclaredInInterfacesMessage(
                                clazz,
                                method
                            ).toString()
                        )
                    )
            );
        }
    }
}
