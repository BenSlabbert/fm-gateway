#!make

.PHONY: install verify compile fmt clean updateVersion

install: fmt
	@ mvn install

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
