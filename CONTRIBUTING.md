# Contributing to This Project

Welcome! 👋
We love contributions from the community. By participating, you agree to follow a set of simple guidelines to keep our project healthy and sustainable.

## Table of Contents

1. Getting Started
2. Development Workflow
3. Commit Guidelines
4. Code Quality
5. Reporting Issues
6. Puzzle-driven development
7. References

## Getting Started

To contribute, you need the following installed locally:

1. Java & Maven

Ensure you have Java 21+ and Maven installed.

```bash
java -version
mvn -version
```

2. Node.js & npm

Required for build tooling:

```bash
npm install
```

3. Python & yamllint

For YAML linting:

```bash
pip install yamllint
```

## Development Workflow

1. Fork the repository and clone it locally.
2. Create a branch for your contribution:

```bash
git checkout -b feature/issue-number/your-feature-name
```

3. Make your changes. Keep commits small and focused.
4. Lint your files:

```bash
yamllint .
mvn clean compile
```

5. Run tests:

```bash
mvn test
```

6. Push your branch to your fork and open a Pull Request referencing the related issue.

## Branch Naming Convention

To keep the repository clean, readable, and easy to maintain, all branches **must follow a strict naming convention**.

### Required Pattern

Branch names must match the following regular expression:

```bash
^(main){1}$|^(feature|fix|hotfix|release|doc)\/\d+\/[a-z-]+$
```

### Explanation

This pattern enforces:

* A **single protected branch**:
  * `main` → the primary branch of the repository

* Or a **typed branch** with the following structure:

```bash
<type>/<issue-number>/<description>
```

#### Allowed Types

* `feature` → new functionality
* `fix` → bug fixes
* `hotfix` → urgent fixes in production
* `release` → release preparation
* `doc` → documentation updates

#### Issue Number

* Each branch refers a GitHub issue, so prefix the description with the issue number:

  ```bash
  feature/123/add-user-authentication
  ```

* The issue number must be numeric (`\d+`).

#### Description

* Must contain **only lowercase letters and hyphens**
* Should be concise but descriptive
* No spaces, underscores, or special characters

### Examples

Valid branch names:

```bash
main
feature/add-login-page
feature/42/add-login-page
fix/128/null-pointer-error
hotfix/urgent-crash-fix
doc/77/improve-installation-guide
```

### Invalid Examples

```bash
Feature/add-login       # uppercase not allowed
feature/add_login       # underscores not allowed
feature//add-login      # missing description
feature/abc/add-login   # issue number must be numeric
bugfix/123/fix-issue    # invalid type (use "fix")
```

### Best Practices

* Keep descriptions **short, meaningful, and action-oriented**
* Use hyphens to separate words (kebab-case)

## Commit Guidelines

We follow Gitmoji for commit messages. Example:

* ✨ `:sparkles:` for new features
* 🐛 `:bug:` for bug fixes
* 📝 `:memo:` for documentation
* 🔧 `:wrench:` for configuration changes

**Format:**

```bash
<emoji> Short description. See #ISSUE_NUMBER
```

Example:

```bash
✨ Add support for custom serializers. See #42
```

## Code Quality

* Keep methods and classes small and readable.
* Follow Java naming conventions.
* Ensure all tests pass before committing.
* Lint YAML and other configuration files.
* Avoid breaking existing functionality.

## Reporting Issues

When reporting a bug or suggesting a feature, please:

* Use our GitHub issue template.
* Provide a clear title and description.
* Include steps to reproduce (if applicable).
* Reference related Pull Requests or commits if needed.

## Puzzle-Driven Development (PDD)

We follow the Puzzle-Driven Development (PDD) approach as described by Yegor Bugayenko.

* 📖 [Concept article](https://www.yegor256.com/2010/03/04/pdd.html)
* 📐 [Formatting rules](https://github.com/cqfn/pdd#how-to-format)

### What is PDD?

Puzzle-Driven Development means that:

* All non-trivial work must start with a GitHub issue.
* Each issue represents a puzzle to solve.
* The Pull Request must reference one issue.
* Closing the PR automatically closes the issue.

## References

* [GitHub Flow](https://docs.github.com/en/get-started/using-github/github-flow)
* [Semantic Commit Messages](https://www.conventionalcommits.org/)
* [Yegor256 GitHub Guidelines](https://www.yegor256.com/2014/04/15/github-guidelines.html)
