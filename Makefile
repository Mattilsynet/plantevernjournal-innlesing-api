# This file was added through the upgradeProject task in snackpack-gradle-plugin
.PHONY: help
help:
	@echo 'Make targets:'
	@echo '  build              Builds the project with tests and linting to create a new docker image'
	@echo '  build-non-strict   Builds the project with no tests or linting to create a new docker image'
	@echo '  clean              Deletes the built docker image and the gradle build output'
	@echo '  help               Displays this help page'
	@echo '  lint               Runs linting (static code analysis)'
	@echo '  test               Runs tests'

ifeq (${CPU_ARCH},arm64)
  fromImage='-Djib.from.image=gcr.io/distroless/java17-debian11:debug-arm64'
else
  fromImage=
endif

.PHONY: build
build:
	sh gradlew clean build

.PHONY: build-non-strict
build-non-strict:
	sh gradlew clean build -x detekt -x test

.PHONY: lint
lint:
	sh gradlew detekt

.PHONY: test
test:
	sh gradlew test
