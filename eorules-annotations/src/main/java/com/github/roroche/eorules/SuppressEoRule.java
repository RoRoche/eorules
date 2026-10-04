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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to suppress specific eorules rules.
 *
 * @since 0.0.2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.CONSTRUCTOR,
    ElementType.FIELD,
    ElementType.METHOD,
    ElementType.TYPE
})
@SuppressWarnings("allfinal")
public @interface SuppressEoRule {

    /**
     * Rule key for classes being abstract or final.
     */
    String CLASS_FINALITY = "classes-abstract-or-final";

    /**
     * Rule key for classes having no static methods.
     */
    String NO_STATIC_METHODS = "no-static-methods";

    /**
     * Rule key for classes having no getters or setters.
     */
    String NO_ACCESSORS = "no-getters-or-setters";

    /**
     * Rule key for classes having no private methods.
     */
    String NO_PRIVATES = "no-private-methods";

    /**
     * Rule key for fields being final.
     */
    String FIELDS_FINAL = "fields-final";

    /**
     * Rule key for public methods being declared in interfaces.
     */
    String PUBLIC_CONTRACTS = "public-methods-in-interfaces";

    /**
     * Suppressed eorules rule keys.
     *
     * @return Rule keys suppressed on the annotated element
     */
    String[] value();
}
