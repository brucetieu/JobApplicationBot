SUDO=$(shell which sudo)

default:
	echo "Nothing to do"

install:
	$(SUDO) add-apt-repository ppa:openjdk-r/ppa
	$(SUDO) apt update
	$(SUDO) apt-cache search openjdk
	$(SUDO) apt install openjdk-11-jdk-headless maven

test:
	@# Run tests.
	@mvn test

	@make clean > /dev/null

clean:
	@rm -rf target