# Default target
default: help

lint: ## Check code formatting
	@mvn sortpom:verify
	@mvn license:check
	@mvn qulice:check
	@mvn youshallnotpass:youshallnotpass
	@mvn jtcop:check
	@mvn exec:exec -Dexec.executable=yamllint -Dexec.args="."
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

.PHONY: lint lint-fix help default
