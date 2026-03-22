# eorules

Set of custom ArchUnit rules to ensure [Elegant Objects](https://www.elegantobjects.org/) principles respect.

`eorules` helps you design and craft code that respect strong object-oriented programming paradigm.

[![Build Status](https://github.com/RoRoche/eorules/actions/workflows/build-java.yml/badge.svg)](https://github.com/RoRoche/eorules/actions)
[![YAML Lint](https://github.com/RoRoche/eorules/actions/workflows/yamllint.yml/badge.svg)](https://github.com/RoRoche/eorules/actions/workflows/yamllint.yml)
![Nodes.js CI](https://github.com/RoRoche/eorules/actions/workflows/build-npm.yml/badge.svg)

![EO principles respected here](https://www.elegantobjects.org/badge.svg)
[![DevOps By Rultor.com](https://www.rultor.com/b/RoRoche/eorules)](https://www.rultor.com/p/RoRoche/eorules)
![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)

[![PDD status](https://www.0pdd.com/svg?name=RoRoche/eorules)](https://www.0pdd.com/p?name=RoRoche/eorules)

[![codecov](https://codecov.io/gh/RoRoche/eorules/branch/main/graph/badge.svg)](https://codecov.io/gh/RoRoche/eorules)

[![Hits-of-Code](https://hitsofcode.com/github/RoRoche/eorules?branch=main)](https://hitsofcode.com/github/RoRoche/eorules/view?branch=main)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_eorules&metric=bugs)](https://sonarcloud.io/summary/new_code?id=RoRoche_eorules)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_eorules&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=RoRoche_eorules)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_eorules&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=RoRoche_eorules)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_eorules&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=RoRoche_eorules)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_eorules&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=RoRoche_eorules)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_eorules&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=RoRoche_eorules)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_eorules&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=RoRoche_eorules)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_eorules&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=RoRoche_eorules)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_eorules&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=RoRoche_eorules)

![nullfree status](https://youshallnotpass.dev/nullfree/RoRoche/eorules)
![staticfree status](https://youshallnotpass.dev/staticfree/RoRoche/eorules)
![allfinal status](https://youshallnotpass.dev/allfinal/RoRoche/eorules)
![allpublic status](https://youshallnotpass.dev/allpublic/RoRoche/eorules)
![setterfree status](https://youshallnotpass.dev/setterfree/RoRoche/eorules)
![nomultiplereturn status](https://youshallnotpass.dev/nomultiplereturn/RoRoche/eorules)

[![Maven Central](https://img.shields.io/maven-central/v/com.github.roroche/eorules.svg?label=Maven%20Central)](https://search.maven.org/artifact/com.github.roroche/eorules)
[![Javadoc](https://javadoc.io/badge2/com.github.roroche/eorules/javadoc.svg)](https://javadoc.io/doc/com.github.roroche/eorules)

## ✨ Features

- Check that [classes are abstract or final](https://www.yegor256.com/2014/11/20/seven-virtues-of-good-object.html#7-his-class-is-either-final-or-abstract)
- Check that [classes have no static methods](https://www.yegor256.com/2017/02/07/private-method-is-new-class.html)
- Check that [classes have no getters and no setters](https://www.yegor256.com/2014/09/16/getters-and-setters-are-evil.html)
- Check that [classes have no private methods](https://www.yegor256.com/2017/02/07/private-method-is-new-class.html)
- Check that [fields are final](https://www.yegor256.com/2014/11/20/seven-virtues-of-good-object.html#4-he-is-immutable)
- Check that [public methods are defined in interface](https://www.yegor256.com/2014/11/20/seven-virtues-of-good-object.html#2-he-works-by-contracts)

## 📥 Installation

Add the dependency to your project:

```xml
<dependency>
    <groupId>com.github.roroche</groupId>
    <artifactId>eorules</artifactId>
    <version>${latest.version}</version>
</dependency>
```

## 🚀 Usage

```java
class ArchitectureTest {

    private final JavaClasses classes = new ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages("com.example");

    @Test
    void checksClassesAreAbstractOrFinal() {
        new ClassesAreAbstractOrFinalRule().check(this.classes);
    }

    @Test
    void checksThereAreNoStaticMethods() {
        new ClassesShouldHaveNoStaticMethodsRule().check(this.classes);
    }

    @Test
    void checksClassesDoNotHaveGettersOrSetters() {
        new ClassesShouldNotHaveGettersOrSettersRule().check(this.classes);
    }

    @Test
    void checksClassesDoNotHavePrivateMethods() {
        new ClassesShouldNotHavePrivateMethodsRule().check(this.classes);
    }

    @Test
    void checksFieldsAreFinal() {
        new FieldsShouldBeFinalRule().check(this.classes);
    }

    @Test
    void checksPublicMethodsAreDeclaredInInterfaces() {
        new PublicMethodsDeclaredInInterfacesRule().check(this.classes);
    }
}
```

To bypass the checks, you can annotate your class with `@ExcludeFromArchUnit`.

## 🤝 Contributing

Contributions are welcome!

If you'd like to report a bug, suggest a feature, or submit a pull request, please read our
👉 **[Contributing Guide](CONTRIBUTING.md)**

It contains everything you need to know about:

- Development setup
- Coding standards
- Commit conventions
- Pull request process
- Quality requirements

Thank you for helping improve `eorules` 🚀

## 📄 License

Distributed under the MIT License. See [LICENSE](LICENSE) for more information.
