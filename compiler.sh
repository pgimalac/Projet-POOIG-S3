#!/bin/bash
find ./ | grep java 1> fichiers 2> poubelle
javac @fichiers