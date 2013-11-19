#!/bin/bash
figlet "RbLinks - wafs"
sleep 2
JAVASRC="../src/com/redbrick/devchat/rblinks/"
JAVACP="../lib/pircbot.jar:../lib/mongo-java-driver-2.11.2.jar:."
COMP="*.java"
COMPILE="javac -d . -cp $JAVACP $JAVASRC$COMP -verbose"
eval $COMPILE
RUN="java -cp $JAVACP com.redbrick.devchat.rblinks.RbLinks"
eval $RUN

