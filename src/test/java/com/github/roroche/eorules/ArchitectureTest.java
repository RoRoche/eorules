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

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

/**
 * Test class for the architecture of the library.
 *
 * @since 0.0.1
 * @todo #8:25m/DEV Add rule for classes to not have private methods
 * @todo #8:25m/DEV Add rule for fields to be final
 * @todo #8:25m/DEV Add rule for classes to have public methods declared in an interface
 */
@SuppressWarnings({
    "allpublic",
    "JTCOP.RuleEveryTestHasProductionClass",
    "JTCOP.RuleAssertionMessage"
})
final class ArchitectureTest {

    /**
     * The classes to be checked.
     */
    private final JavaClasses classes = new ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages("com.github.roroche.eorules");

    @Test
    void checksClassesAreAbstractOrFinal() {
        new ClassesAreAbstractOrFinalRule().check(this.classes);
    }

    @Test
    void checksClassesDoNotHaveGettersOrSetters() {
        new ClassesShouldNotHaveGettersOrSettersRule().check(this.classes);
    }

    @Test
    void checksThereAreNoStaticMethods() {
        new ClassesShouldHaveNoStaticMethodsRule().check(this.classes);
    }

    @Test
    void checksClassesDoNotHavePrivateMethods() {
        new ClassesShouldNotHavePrivateMethodsRule().check(this.classes);
    }
}
