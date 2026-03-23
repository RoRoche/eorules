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
package com.github.roroche.eorules.examples.valid;

import java.util.Objects;

/**
 * Simple java object.
 *
 * @since 0.0.2
 */
public final class Person {
    /**
     * The first name.
     */
    private final String firstname;

    /**
     * The last name.
     */
    private final String lastname;

    /**
     * The age.
     */
    private final int age;

    /**
     * Primary ctor.
     *
     * @param firstname The first name.
     * @param lastname The last name.
     * @param age The age.
     */
    public Person(final String firstname, final String lastname, final int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    @Override
    public boolean equals(final Object other) {
        return this == other
            || other instanceof Person casted
            && this.age == casted.age
            && Objects.equals(this.firstname, casted.firstname)
            && Objects.equals(this.lastname, casted.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.firstname, this.lastname, this.age);
    }

    @Override
    public String toString() {
        return "Person{firstName='%s', lastName='%s', age=%d}".formatted(
            this.firstname,
            this.lastname,
            this.age
        );
    }
}
