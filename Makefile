#!make

.PHONY: install test verify compile fmt clean updateVersion

install: fmt
	@ mvn install
	@ docker build . -t gateway

test: fmt
	@ mvn test

verify: fmt
	@ mvn verify

compile: fmt
	@ mvn compile

fmt:
	@ mvn spotless:apply

clean:
	@ mvn clean

updateVersion:
	@ mvn versions:set -DnewVersion=1.0.0-SNAPSHOT
	@ mvn versions:commit
