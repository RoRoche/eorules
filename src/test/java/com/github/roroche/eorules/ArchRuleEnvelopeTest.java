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

import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test on {@link ArchRuleEnvelope} delegation methods.
 * @since 0.0.3
 */
@SuppressWarnings({"allpublic", "JTCOP.RuleAssertionMessage"})
final class ArchRuleEnvelopeTest {

    @Test
    void delegatesBecause() {
        MatcherAssert.assertThat(
            "The decorated rule returned by because must not be null",
            new FieldsShouldBeFinalRule().because("fields must stay immutable"),
            Matchers.notNullValue()
        );
    }

    @Test
    void delegatesAllowEmptyShould() {
        MatcherAssert.assertThat(
            "The decorated rule returned by allowEmptyShould must not be null",
            new FieldsShouldBeFinalRule().allowEmptyShould(true),
            Matchers.notNullValue()
        );
    }

    @Test
    void delegatesAs() {
        MatcherAssert.assertThat(
            "The decorated rule returned by as must not be null",
            new FieldsShouldBeFinalRule().as("all fields remain final"),
            Matchers.notNullValue()
        );
    }

    @Test
    void exposesDescription() {
        MatcherAssert.assertThat(
            "The wrapped rule description must not be empty",
            new FieldsShouldBeFinalRule().getDescription(),
            Matchers.not(Matchers.emptyString())
        );
    }

    @Test
    void acceptsEmptyClassesWhenAllowed() {
        MatcherAssert.assertThat(
            "An empty class set must produce no violation when explicitly allowed",
            new FieldsShouldBeFinalRule().allowEmptyShould(true).evaluate(
                new ClassFileImporter().importClasses()
            ).hasViolation(),
            new IsEqual<>(false)
        );
    }
}
