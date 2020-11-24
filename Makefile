SUDO=$(shell which sudo)

default:
	echo "Nothing to do"

install:
	$(SUDO) apt update
	$(SUDO) apt install -y openjdk-8-jdk-headless maven

test:
	@# Run tests.
	@mvn test

	@make clean > /dev/null

build:
	@# Build executable jar file.
	@mvn clean compile assembly:single

documentation:
	@# Generate documentation.
	@mvn -Dmaven.javadoc.skip=true verify clean site

# 	@xdg-open target/site/testapidocs/index.html

clean:
	@rm -rf target