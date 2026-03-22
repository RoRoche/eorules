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

import com.github.roroche.eorules.conditions.NotHavePrivateMethods;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

/**
 * {@link com.tngtech.archunit.lang.ArchRule} to assert that classes have no private methods.
 *
 * @since 0.0.1
 */
public final class ClassesShouldNotHavePrivateMethodsRule extends ArchRuleEnvelope {

    public ClassesShouldNotHavePrivateMethodsRule() {
        super(
            ArchRuleDefinition.classes()
                .that().areNotAnnotatedWith(ExcludeFromArchUnit.class)
                .should(new NotHavePrivateMethods())
                .because("https://www.yegor256.com/2017/02/07/private-method-is-new-class.html")
        );
    }
}
