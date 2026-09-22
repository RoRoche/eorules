# Tools
MVN := mvn
PITEST_CMD := org.pitest:pitest-maven:mutationCoverage

# Build artifacts and sentinel files
TARGET_DIR := target
SENTINEL_DIR := $(TARGET_DIR)/.make
SENTINEL_TEST := $(SENTINEL_DIR)/tests-passed
SENTINEL_PIT := $(SENTINEL_DIR)/mutation-coverage

# Sources that invalidate tests and mutation coverage
SOURCES := pom.xml $(shell find src -type f \( -name "*.java" -o -name "*.xml" -o -name "*.properties" \) 2>/dev/null)

# Default target
default: help

test: $(SENTINEL_TEST) ## Run unit tests

$(SENTINEL_TEST): $(SOURCES)
	@mkdir -p $(SENTINEL_DIR)
	@$(MVN) clean test
	@mkdir -p $(SENTINEL_DIR)
	@touch $@

check: ## Run the complete Maven verification lifecycle
	@$(MVN) clean verify

mutation: $(SENTINEL_PIT) ## Run unit tests and mutation testing

$(SENTINEL_PIT): $(SENTINEL_TEST)
	@$(MVN) $(PITEST_CMD)
	@touch $@

watch: ## Run unit tests whenever Java sources change
	@watchexec --restart \
		--watch src/main \
		--watch src/test \
		--exts java \
		-- make test

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
	@$(MVN) sortpom:verify
	@$(MVN) license:check
	@$(MVN) qulice:check
	@$(MVN) youshallnotpass:youshallnotpass
	@$(MVN) jtcop:check
	@uv run yamllint .
	@uv run mbake format --check Makefile
	@uv run mbake validate Makefile
	@npx markdownlint "**/*.md"
	@npx textlint "**/*.md"

lint-fix: ## Fix formatting automatically
	@$(MVN) sortpom:sort
	@$(MVN) license:format

force-test: ## Force unit tests to run even if sources are unchanged
	@rm -f $(SENTINEL_TEST)
	@$(MAKE) test

force-mutation: ## Force mutation testing to run even if sources are unchanged
	@rm -f $(SENTINEL_PIT)
	@$(MAKE) mutation

clean: ## Clean the build and remove sentinel files
	@$(MVN) clean

help: ## Show this help message
	@echo ""
	@echo "Available targets:"
	@echo ""
	@grep -E '^[a-zA-Z0-9_-]+:[^#]*##' Makefile \
		| awk 'BEGIN {FS = "##"}; {printf "  \033[1;32m%-15s\033[0m %s\n", $$1, $$2}'
	@echo ""

.PHONY: check clean default force-mutation force-test help lint lint-fix mutation test watch watch-check watch-mutation
