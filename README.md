rblinks
=======

This is a simple IRC bot that stores links from redbricks #lobby 
All the data is publicly available for members to use via a read only Mongo DB.

All your links are belong to us.

Command Line Build
------------------
    javac -d bin -cp "lib\mongo-java-driver-2.11.2.jar;lib\pircbot.jar" src\com\redbrick\devchat\rblinks\RbLinkbot.java src\com\redbrick\devchat\rblinks\RbLinks.java
