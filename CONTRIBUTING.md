# Contributing to This Project

Welcome! 👋
We love contributions from the community. By participating, you agree to follow a set of simple guidelines to keep our project healthy and sustainable.

## Table of Contents

1. [Getting Started](#getting-started)
2. [Development Workflow](#development-workflow)
3. [Branch Naming Convention](#branch-naming-convention)
4. [Commit Guidelines](#commit-guidelines)
5. [Code Quality](#code-quality)
6. [Reporting Issues](#reporting-issues)
7. [Puzzle-Driven Development (PDD)](#puzzle-driven-development-pdd)
8. [References](#references)

## Getting Started

[Mise](https://mise.jdx.dev/) manages the development tools on Linux, macOS and Windows.
Install Git and [Mise](https://mise.jdx.dev/getting-started.html) first.

```bash
# macOS
brew install mise
```

```powershell
# Windows (PowerShell)
winget install jdx.mise
```

On Linux, follow the official [installation instructions](https://mise.jdx.dev/installing-mise.html).
Then clone your fork and run these commands from the repository root:

```bash
mise trust
mise install
mise run setup
mise run versions
```

Review `mise.toml` before trusting it. It defines exact versions of Temurin Java 21, Maven, Node.js and npm.
It also pins Python, UV and `watchexec`. npm has its own version pin.
Mise sets `JAVA_HOME` and selects the tools for each task.
UV uses the configured Python version without downloading another interpreter.
`npm ci` and `uv sync --frozen` install dependencies from the committed lockfiles.
The setup task also installs the Husky Git hooks.

Tasks require `mise install` first. Use `mise run` to list the tasks. These tasks work without shell activation or Make,
including from PowerShell on Windows. Git hooks also require `mise` on `PATH`.
The existing Makefile remains available on Linux, macOS and WSL2.

To run tools directly, prefix commands with `mise exec --`:

```bash
mise exec -- mise run test
mise exec -- uv run --frozen yamllint .
```

For automatic tool selection in your terminal, follow the
[shell activation instructions](https://mise.jdx.dev/getting-started.html#activate-mise).
For example, add `eval "$(mise activate zsh)"` to `~/.zshrc` for Zsh.
In an IDE, select the JDK directory reported by `mise where java` as the project SDK
and the Maven directory from `mise where maven` as Maven home.

### Updating the environment

Keep shared tool versions in `mise.toml`; avoid separate version files or global installs.
Use `mise use --pin <tool>@<version>` to update a tool, then run setup and the checks.
When updating Python, also update `UV_PYTHON` in `mise.toml`.
Update project dependencies with npm or UV and commit their lockfiles.
Personal overrides belong in the ignored `mise.local.toml`.

GitHub Actions uses `jdx/mise-action` and the same configuration.
Each lint or build job installs its required tools.
The environment workflow installs all tools and locked dependencies on Linux, macOS and Windows.
Mise manages tool installations; Maven, npm and UV manage project dependencies.
Git, IDEs and optional Make remain external prerequisites.

## Development Workflow

1. Fork the repository and clone it locally.
2. Create a branch for your contribution:

```bash
git checkout -b feature/issue-number/your-feature-name
```

3. Make your changes. Keep commits small and focused.
4. Lint your files:

```bash
mise run lint
```

This runs Java, Markdown, branch-name, YAML and Makefile checks.
For Java checks, use `mise run lint:java`.
See [Code Quality](#code-quality) for the Java analysis tools and their diagnostics.

5. Run tests:

```bash
mise run test
```

6. Run the complete Maven verification lifecycle before opening a Pull Request:

```bash
mise run check
```

This performs a clean build, runs tests and checks Java quality, including SpotBugs.
The Java CI build runs the same `clean verify` lifecycle.

7. Push your branch to your fork and open a Pull Request referencing the related issue.

### Continuous Feedback During Development

Mise installs [watchexec](https://watchexec.github.io/) for automatic checks after source changes.
Choose the task that matches your work:

```bash
mise run watch
mise run watch-check
mise run watch-mutation
```

`watch` runs unit tests after Java changes. `watch-check` runs Maven verification
and also watches `pom.xml`. `watch-mutation` runs mutation testing.
`watchexec` restarts the command after another relevant change.
These commands also work on Windows without Make or WSL2.

## Branch Naming Convention

To keep the repository clean, readable, and easy to maintain, all branches **must follow a strict naming convention**.

### Required Pattern

Branch names must match the following regular expression:

```bash
^(main)$|^(renovate\/.*)$|^(feature|fix|hotfix|release|doc)\/\d+\/[a-z0-9-]+$
```

### Explanation

This pattern enforces:

* A **single protected branch**:
  * `main` → the primary branch of the repository

* Renovate branches for automated dependency updates

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

* Must contain **only lowercase letters, digits and hyphens**
* Should be concise but descriptive
* No spaces, underscores, or special characters

### Examples

Valid branch names:

```bash
main
feature/42/add-login-page
fix/128/null-pointer-error
hotfix/12/urgent-crash-fix
doc/77/improve-installation-guide
renovate/npm-dependencies
```

### Invalid Examples

```bash
Feature/add-login       # uppercase not allowed
feature/add_login       # underscores not allowed
feature/add-login       # missing issue number
feature/12              # missing description
feature/abc/add-login   # issue number must be numeric
bugfix/123/fix-issue    # invalid type (use "fix")
```

### Best Practices

* Keep descriptions **short, meaningful, and action-oriented**
* Use hyphens to separate words (kebab-case)

### Automated Dependency Updates

[Renovate](https://docs.renovatebot.com/) manages Maven, npm, GitHub Actions, Mise and UV dependency updates.
Its configuration lives in `.github/renovate.json`.
Install or enable the Renovate GitHub App for this repository to activate updates.

Maven, npm, Mise and UV updates run on Sundays from 04:00 until 12:00 in `Europe/Paris` time
with the schedule `"schedule": ["* 4-11 * * 0"]`.
GitHub Actions updates run on Mondays. Updates appear on the next Renovate run within each window.
Renovate groups npm updates into one Pull Request and requests review from `RoRoche`
for Maven, npm, Mise and UV updates. It allows up to five open update Pull Requests across the repository.
These update commits use the `⬆️` Gitmoji with an ecosystem scope.

The `mise` manager updates tools pinned in `mise.toml`, including UV itself.
The `pep621` manager updates Python dependencies in `pyproject.toml` and refreshes `uv.lock`.
Weekly lockfile maintenance also refreshes locked dependencies within existing version constraints.
When reviewing Python tool updates, keep `UV_PYTHON` aligned with the Python version in `mise.toml`.

Automated branches use the `renovate/` prefix and do not require an issue number.
The Java CI workflow skips PIT mutation testing and its report upload for Pull Requests
authored by `renovate[bot]`; the other Java checks still run.

## Commit Guidelines

We follow [Gitmoji](https://gitmoji.dev/) for commit messages. Example:

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

### Java Analysis Tools

Qulice runs the general Java quality checks, NullAway checks null safety during compilation,
and SpotBugs analyzes compiled bytecode.
Their configuration lives in `pom.xml`.

| Command | NullAway | SpotBugs |
| --- | --- | --- |
| `mise run lint:java` or `mise run lint` | Checks production sources during compilation | Checks production classes |
| `mise run test` | Checks sources when Maven compiles them | Does not run |
| `mise run check` | Clean compilation of production and test sources | Checks production classes during `verify` |
| Java CI build (`mvn -B -ntp clean verify`) | Clean compilation of production and test sources | Checks production classes during `verify` |

#### NullAway

[NullAway](https://github.com/uber/NullAway) runs during Maven compilation.
It treats references in `com.github.roroche` and its subpackages as non-null by default.
Null-safety violations in production or test sources fail compilation.
Keep new project packages under this namespace, or update `NullAway:AnnotatedPackages` in `pom.xml` when renaming it.
Prefer non-null APIs and fix violations instead of adding broad suppressions.
NullAway checks dereferences, arguments, return values and field initialization.
This complements the `youshallnotpass` rule against explicit `null` usage.
NullAway stays on the compiler processor path and adds no runtime dependency to the library.

#### SpotBugs

[SpotBugs](https://spotbugs.github.io/) analyzes compiled production classes with `Max` effort and includes low-priority findings.
Findings fail the check. SpotBugs runs during Maven's `verify` phase and as an explicit step in `lint:java`.
Run `mise exec -- mvn -B -ntp compile spotbugs:check` for a focused check.
SpotBugs writes its XML report to `target/spotbugsXml.xml`. CI uploads it as the `spotbugs-report` artifact.

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
