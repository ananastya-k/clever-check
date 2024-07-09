package main.java.ru.clevertec.check.cli.parser.converters;

public class StringConverter implements IStringConverter{

    @Override
    public Object convert(String s) {
        return s;
    }

    @Override
    public boolean check(String s) {
        return true;
    }
}
