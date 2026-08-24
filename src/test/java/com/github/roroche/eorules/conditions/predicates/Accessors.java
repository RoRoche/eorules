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

/**
 * Accessor-like methods, some of which are not real getters or setters.
 * @since 0.0.3
 */
@SuppressWarnings("PMD.PublicMemberInNonPublicType")
final class Accessors {

    /*
     * @checkstyle NonStaticMethodCheck (4 lines)
     */
    public boolean isReady() {
        return true;
    }

    /*
     * @checkstyle NonStaticMethodCheck (4 lines)
     */
    public boolean isReadyWith(final String value) {
        return !value.isEmpty();
    }

    /*
     * @checkstyle NonStaticMethodCheck (4 lines)
     */
    public String getNamed(final String name) {
        return name;
    }

    /*
     * @checkstyle NonStaticMethodCheck (5 lines)
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    public void getNothing() {
        // Intentionally empty.
    }

    /*
     * @checkstyle NonStaticMethodCheck (4 lines)
     */
    public void setNothing() {
        // Intentionally empty.
    }

    /*
     * @checkstyle NonStaticMethodCheck (5 lines)
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    public String isName() {
        return "name";
    }

    /*
     * @checkstyle NonStaticMethodCheck (4 lines)
     */
    public Boolean isEnabled() {
        return Boolean.TRUE;
    }

    /*
     * @checkstyle NonStaticMethodCheck (4 lines)
     */
    public void setName(final String name) {
        // Intentionally empty.
    }

    public Accessors setFluently(final String name) {
        return this;
    }
}
