.PHONY: init \
init-npm \
clean \
clean-npm \
clean-sls \
clean-test \
test \
coverage \
deploy \

.DEFAULT_GOAL := help

# Deploy configuration
STAGE ?= dev
AWS_PROFILE ?= uvsy-dev

# Migration
COMMAND ?= upgrade
REVISION ?= ""

help:
	@echo "    init"
	@echo "        Initialize development environment."
	@echo "    init-npm"
	@echo "        Initialize npm environment."
	@echo "    clean"
	@echo "        Remove all the development environment files."
	@echo "    clean-npm"
	@echo "        Remove Node modules."
	@echo "    clean-sls"
	@echo "        Remove Serverless artifacts."
	@echo "    clean-test"
	@echo "        Remove Test data."
	@echo "    test"
	@echo "        Run tests."
	@echo "    deploy"
	@echo "        Build and deploy to AWS."

init: clean init-npm init-gradle
	@echo "Project initialized"

init-npm:
	@npm install

init-gradle:
	@./gradlew compileJava

clean: clean-npm clean-sls clean-out clean-build

clean-npm:
	@echo "Removing node modules..."
	@rm -rf node_modules

clean-sls:
	@echo "Removing serverless files..."
	@rm -rf .serverless

clean-build:
	@echo "Removing build artifacts..."
	@./gradlew clean

clean-out:
	@echo "Removing compiled artifacts..."
	@rm -rf out
test:
	@./gradlew test

build: clean-build
	@./gradlew build

deploy: build
	@echo "Deploying to '$(STAGE)' with profile '$(AWS_PROFILE)'..."
	@serverless deploy -v --stage $(STAGE) --profile $(AWS_PROFILE)

run: migrate clean-build build
	@serverless offline start -v --stage local --noAuth


migrate:
	@./gradlew flywayMigrate