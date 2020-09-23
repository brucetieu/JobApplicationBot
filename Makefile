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

clean:
	@rm -rf target