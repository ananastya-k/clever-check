#!/bin/bash
find src -name "*.java" > sources.txt
javac @sources.txt -d out
java -cp out main.java.ru.clevertec.check.Main 7-1 balanceDebitCard=9 
