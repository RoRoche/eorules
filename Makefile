# Default target
default: help

fast: ## Run unit tests for fast feedback
	@mvn clean test

check: ## Run the complete Maven verification lifecycle
	@mvn clean verify

mutation: ## Run unit tests and mutation testing
	@mvn clean test org.pitest:pitest-maven:mutationCoverage

watch: ## Run unit tests whenever Java sources change
	@watchexec --restart \
		--watch src/main \
		--watch src/test \
		--exts java \
		-- make fast

watch-check: ## Run Maven verification whenever sources or pom.xml change
	@watchexec --restart \
		--watch src/main \
		--watch src/test \
		--watch pom.xml \
		--exts java,xml \
		-- make check

watch-mutation: ## Run mutation testing whenever Java sources change
	@watchexec --restart \
		--watch src/main \
		--watch src/test \
		--exts java \
		-- make mutation

lint: ## Check code formatting
	@npx validate-branch-name
	@mvn sortpom:verify
	@mvn license:check
	@mvn qulice:check
	@mvn youshallnotpass:youshallnotpass
	@mvn jtcop:check
	@uv run yamllint .
	@uv run mbake format --check Makefile
	@uv run mbake validate Makefile
	@npx markdownlint "**/*.md"
	@npx textlint "**/*.md"

lint-fix: ## Fix formatting automatically
	@mvn sortpom:sort
	@mvn license:format

help: ## Show this help message
	@echo ""
	@echo "Available targets:"
	@echo ""
	@grep -E '^[a-zA-Z0-9_-]+:[^#]*##' Makefile \
		| awk 'BEGIN {FS = "##"}; {printf "  \033[1;32m%-15s\033[0m %s\n", $$1, $$2}'
	@echo ""

.PHONY: check default fast help lint lint-fix mutation watch watch-check watch-mutation
