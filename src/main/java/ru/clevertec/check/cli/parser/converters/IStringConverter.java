package main.java.ru.clevertec.check.cli.parser.converters;

public interface IStringConverter <T>{
    T convert(String s);
    boolean check(String s);
}