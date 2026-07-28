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

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ClassFileImporter;

/**
 * Shared helpers used by the predicate mutation tests to import fixture
 * classes with ArchUnit and to resolve a single method by name.
 * @since 0.0.3
 */
@SuppressWarnings({"allpublic", "staticfree", "allfinal"})
final class PredicateTestSupport {

    /**
     * Only utility methods, no instances.
     */
    private PredicateTestSupport() {
    }

    /**
     * Imports the given classes with ArchUnit.
     * @param types Classes to import
     * @return The imported classes
     */
    static JavaClasses classes(final Class<?>... types) {
        return new ClassFileImporter().importClasses(types);
    }

    /**
     * Resolves a single method by name on an imported class.
     * @param classes Imported classes
     * @param owner Class declaring the method
     * @param name Method name
     * @return The matching method
     */
    static JavaMethod method(
        final JavaClasses classes,
        final Class<?> owner,
        final String name
    ) {
        return classes.get(owner).getMethods().stream()
            .filter(method -> method.getName().equals(name))
            .filter(method -> !method.reflect().isSynthetic())
            .findFirst()
            .orElseThrow();
    }
}
