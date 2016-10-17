rblinks
=======

This is a simple IRC bot that stores links from an IRC Channel on a network.
All the data is publicly available for members to use via a read only MongoDB.

All your links are belong to us.

Command Line Build
------------------
To compile:

    bazel build //src:rblinks

To run:

    ./bazel-bin/src/rblinks


Build Health
---
[![Build Status](https://travis-ci.org/DevChat/rblinks.svg?branch=bazel)](https://travis-ci.org/DevChat/rblinks)
