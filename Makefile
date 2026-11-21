# Tools
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
	@mise run test
	@mkdir -p $(SENTINEL_DIR)
	@touch $@

mutation: $(SENTINEL_PIT) ## Run unit tests and mutation testing

$(SENTINEL_PIT): $(SENTINEL_TEST)
	@mise run mutation
	@touch $@

force-test: ## Force unit tests to run even if sources are unchanged
	@rm -f $(SENTINEL_TEST)
	@$(MAKE) test

force-mutation: ## Force mutation testing to run even if sources are unchanged
	@rm -f $(SENTINEL_PIT)
	@$(MAKE) mutation

help: ## Show this help message
	@echo ""
	@echo "Available targets:"
	@echo ""
	@grep -E '^[a-zA-Z0-9_-]+:[^#]*##' Makefile \
		| awk 'BEGIN {FS = "##"}; {printf "  \033[1;32m%-15s\033[0m %s\n", $$1, $$2}'
	@echo ""

.PHONY: default force-mutation force-test help mutation test
