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
package com.github.roroche.eorules.conditions.collections;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaType;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.cactoos.set.SetEnvelope;

/**
 * Iterate to retrieve all the interfaces.
 * @since 1.0.O
 */
public final class RecursiveInterfaces extends SetEnvelope<JavaClass> {

    /**
     * Ctor.
     * @param clazz The class to retrieve the interfaces from
     */
    public RecursiveInterfaces(final JavaClass clazz) {
        super(
            Stream.concat(
                clazz.getInterfaces().stream().map(JavaType::toErasure).flatMap(
                    (final JavaClass iface) -> Stream.concat(
                        Stream.of(iface),
                        new RecursiveInterfaces(iface).stream()
                    )
                ),
                clazz.getSuperclass().map(JavaType::toErasure).map(
                    (final JavaClass superclass) ->
                        new RecursiveInterfaces(superclass).stream()
                ).orElseGet(Stream::empty)
            ).collect(Collectors.toSet())
        );
    }
}
