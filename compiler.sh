#!/bin/bash
find ./ | grep java 2> poubelle 1> fichiers && javac @fichiers