#!/bin/sh

mvn clean       # Clean target directory
mvn package     # Create JAR without dependencies
mvn source:jar  # Create JAR with source code
mvn javadoc:jar # Create JAR with JAVADOC

# Create JAR with dependencies
mvn assembly:single -DdescriptorId=jar-with-dependencies

# Copy dependencies to a directory (dependency)
mvn dependency:copy-dependencies

# Pack dependencies into a ZIP and into a TAR.GZ files
cd target
tar -cavf dependency.zip dependency/
tar -cavf dependency.tar.gz dependency/
cd .. 

# Copy files for release into a directory called "release"
mkdir release
mv target/*.jar release/
mv target/*.zip release/
mv target/*.tar.gz release/