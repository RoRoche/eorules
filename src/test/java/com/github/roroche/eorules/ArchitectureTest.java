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

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test class for the architecture of the library.
 *
 * @since 0.0.1
 * @todo #1:25m/DEV Add rule for classes to be abstract of final
 * @todo #1:25m/DEV Add rule for classes to not have setter or getter
 * @todo #1:25m/DEV Add rule for classes to not have static methods
 * @todo #1:25m/DEV Add rule for classes to not have private methods
 * @todo #1:25m/DEV Add rule for fields to be final
 * @todo #1:25m/DEV Add rule for classes to have public methods declared in an interface
 */
@SuppressWarnings({
    "allpublic",
    "JTCOP.RuleEveryTestHasProductionClass",
    "JTCOP.RuleAssertionMessage",
    "JTCOP.RulePresentTense"
})
final class ArchitectureTest {
    @Test
    void fake() {
        MatcherAssert.assertThat(
            "Fake test",
            1 + 2,
            new IsEqual<>(3)
        );
    }
}
