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
   Ensure you have Java 17+ and Maven installed.

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
git checkout -b feature/your-feature-name
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

## Commit Guidelines

We follow Gitmoji for commit messages. Example:

- ✨ `:sparkles:` for new features
- 🐛 `:bug:` for bug fixes
- 📝 `:memo:` for documentation
- 🔧 `:wrench:` for configuration changes

**Format:**

```bash
<emoji> Short description (ref #ISSUE_NUMBER)
```

Example:

```bash
✨ Add support for custom serializers (ref #42)
```

## Code Quality

- Keep methods and classes small and readable.
- Follow Java naming conventions.
- Ensure all tests pass before committing.
- Lint YAML and other configuration files.
- Avoid breaking existing functionality.

## Reporting Issues

When reporting a bug or suggesting a feature, please:

- Use our GitHub issue template.
- Provide a clear title and description.
- Include steps to reproduce (if applicable).
- Reference related Pull Requests or commits if needed.

## Puzzle-Driven Development (PDD)

We follow the Puzzle-Driven Development (PDD) approach as described by Yegor Bugayenko and the CQFN community.

- 📖 [Concept article](https://www.yegor256.com/2010/03/04/pdd.html)
- 📐 [Formatting rules](https://github.com/cqfn/pdd#how-to-format)

### What is PDD?

Puzzle-Driven Development means that:

- All non-trivial work must start with a GitHub issue.
- Each issue represents a puzzle to solve.
- The Pull Request must reference one issue.
- Closing the PR automatically closes the issue.

## References

- [GitHub Flow](https://docs.github.com/en/get-started/using-github/github-flow)
- [Semantic Commit Messages](https://www.conventionalcommits.org/)
- [Yegor256 GitHub Guidelines](https://www.yegor256.com/2014/04/15/github-guidelines.html)
