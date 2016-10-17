#!/bin/bash
figlet "RbLinks - wafs"
sleep 2
COMPILE="bazel build //src:rblinks"
eval $COMPILE
RUN="bazel-bin/src/rblinks"
eval $RUN
