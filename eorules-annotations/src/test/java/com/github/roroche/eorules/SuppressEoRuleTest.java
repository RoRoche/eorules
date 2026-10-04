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
import java.util.Arrays;
import java.util.EnumSet;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link SuppressEoRule}.
 *
 * @since 0.0.2
 */
@SuppressWarnings("allpublic")
final class SuppressEoRuleTest {

    @Test
    void isAvailableAtRuntime() {
        MatcherAssert.assertThat(
            "SuppressEoRule must be available to ArchUnit from bytecode",
            SuppressEoRule.class.getAnnotation(Retention.class).value(),
            Matchers.is(RetentionPolicy.RUNTIME)
        );
    }

    @Test
    void targetsClassesAndMembers() {
        MatcherAssert.assertThat(
            "SuppressEoRule must target classes, methods, constructors, and fields",
            EnumSet.copyOf(
                Arrays.asList(SuppressEoRule.class.getAnnotation(Target.class).value())
            ),
            Matchers.containsInAnyOrder(
                ElementType.CONSTRUCTOR,
                ElementType.FIELD,
                ElementType.METHOD,
                ElementType.TYPE
            )
        );
    }

    @Test
    void exposesStableRuleKeys() {
        MatcherAssert.assertThat(
            "SuppressEoRule must expose exactly the six stable rule keys",
            Arrays.asList(
                SuppressEoRule.CLASS_FINALITY,
                SuppressEoRule.NO_STATIC_METHODS,
                SuppressEoRule.NO_ACCESSORS,
                SuppressEoRule.NO_PRIVATES,
                SuppressEoRule.FIELDS_FINAL,
                SuppressEoRule.PUBLIC_CONTRACTS
            ),
            Matchers.contains(
                "classes-abstract-or-final",
                "no-static-methods",
                "no-getters-or-setters",
                "no-private-methods",
                "fields-final",
                "public-methods-in-interfaces"
            )
        );
    }
}
