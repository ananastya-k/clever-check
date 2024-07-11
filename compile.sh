#!/bin/bash
find src -name "*.java" > sources.txt
javac @sources.txt -d out
java -cp out main.java.ru.clevertec.check.Main 3-1 2-5 5-1 3-5 discountCard=4444 balanceDebitCard=28 pathToFile=src/main/resources/products.csv saveToFile=./rese.csv 
