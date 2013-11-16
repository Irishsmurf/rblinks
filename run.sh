#!/bin/bash
javac -cp "./pircbot.jar:mongo-java-driver-2.11.2.jar:." RbLinks.java
java -cp "./pircbot.jar:mongo-java-driver-2.11.2.jar:." RbLinks
