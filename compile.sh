#!/bin/bash
find src -name "*.java" > sources.txt
javac @sources.txt -d out
java -cp out main.java.ru.clevertec.check.Main 3-1 2-5 3-2 discountCard=1451 balanceDebitCard=112 pathToFile=./propro.csv saveToFile=./ree.csv 
