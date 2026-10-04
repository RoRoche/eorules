# Tools
MVN := mvn
PITEST_CMD := test-compile org.pitest:pitest-maven:mutationCoverage

# Maven modules
ANNOTATIONS_MODULE := eorules-annotations
RULES_MODULE := eorules-rules
MODULES := $(ANNOTATIONS_MODULE) $(RULES_MODULE)

# Build artifacts and sentinel files
TARGET_DIR := target
SENTINEL_DIR := $(TARGET_DIR)/.make
SENTINEL_ANNOTATIONS_TEST := $(SENTINEL_DIR)/$(ANNOTATIONS_MODULE)-tests-passed
SENTINEL_RULES_TEST := $(SENTINEL_DIR)/$(RULES_MODULE)-tests-passed
SENTINEL_TEST := $(SENTINEL_DIR)/reactor-tests-passed
SENTINEL_PIT := $(SENTINEL_DIR)/$(RULES_MODULE)-mutation-coverage

# Sources that invalidate tests and mutation coverage
ROOT_BUILD_FILES := pom.xml Makefile
MODULE_POMS := $(foreach module,$(MODULES),$(module)/pom.xml)
ANNOTATIONS_SOURCES := $(ANNOTATIONS_MODULE)/pom.xml $(shell find $(ANNOTATIONS_MODULE)/src -type f \( -name "*.java" -o -name "*.xml" -o -name "*.properties" \) 2>/dev/null)
RULES_SOURCES := $(RULES_MODULE)/pom.xml $(shell find $(RULES_MODULE)/src -type f \( -name "*.java" -o -name "*.xml" -o -name "*.properties" \) 2>/dev/null)
SOURCES := $(ROOT_BUILD_FILES) $(MODULE_POMS) $(ANNOTATIONS_SOURCES) $(RULES_SOURCES)
PIT_SOURCES := $(ROOT_BUILD_FILES) $(MODULE_POMS) $(ANNOTATIONS_SOURCES) $(RULES_SOURCES)

# Default target
default: help

test: $(SENTINEL_TEST) ## Run unit tests

$(SENTINEL_ANNOTATIONS_TEST): pom.xml $(ANNOTATIONS_SOURCES)
	@mkdir -p $(SENTINEL_DIR)
	@$(MVN) -pl $(ANNOTATIONS_MODULE) test
	@touch $@

$(SENTINEL_RULES_TEST): pom.xml $(ANNOTATIONS_SOURCES) $(RULES_SOURCES)
	@mkdir -p $(SENTINEL_DIR)
	@$(MVN) -pl $(RULES_MODULE) -am test
	@touch $@

$(SENTINEL_TEST): $(SENTINEL_ANNOTATIONS_TEST) $(SENTINEL_RULES_TEST) $(SOURCES)
	@touch $@

check: ## Run the complete Maven verification lifecycle
	@$(MVN) clean verify

mutation: $(SENTINEL_PIT) ## Run unit tests and mutation testing

$(SENTINEL_PIT): $(SENTINEL_TEST) $(PIT_SOURCES)
	@$(MVN) $(PITEST_CMD)
	@mkdir -p $(SENTINEL_DIR)
	@touch $@

watch: ## Run unit tests whenever Java sources change
	@watchexec --restart \
		--watch $(ANNOTATIONS_MODULE)/src/main \
		--watch $(ANNOTATIONS_MODULE)/src/test \
		--watch $(RULES_MODULE)/src/main \
		--watch $(RULES_MODULE)/src/test \
		--exts java \
		-- make test

watch-check: ## Run Maven verification whenever sources or pom.xml change
	@watchexec --restart \
		--watch pom.xml \
		--watch $(ANNOTATIONS_MODULE)/pom.xml \
		--watch $(ANNOTATIONS_MODULE)/src/main \
		--watch $(ANNOTATIONS_MODULE)/src/test \
		--watch $(RULES_MODULE)/pom.xml \
		--watch $(RULES_MODULE)/src/main \
		--watch $(RULES_MODULE)/src/test \
		--exts java,xml \
		-- make check

watch-mutation: ## Run mutation testing whenever Java sources change
	@watchexec --restart \
		--watch $(ANNOTATIONS_MODULE)/src/main \
		--watch $(ANNOTATIONS_MODULE)/src/test \
		--watch $(RULES_MODULE)/src/main \
		--watch $(RULES_MODULE)/src/test \
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
	@rm -f $(SENTINEL_ANNOTATIONS_TEST) $(SENTINEL_RULES_TEST) $(SENTINEL_TEST)
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
