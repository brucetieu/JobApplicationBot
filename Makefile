SUDO=$(shell which sudo)
OUT=out

default:
	echo "Nothing to do"

install:
	$(SUDO) apt install openjdk-11-jdk-headless maven

test:
	@# Run tests.
	@mvn test