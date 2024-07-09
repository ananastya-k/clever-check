package main.java.ru.clevertec.check.cli.parser.converters;


import main.java.ru.clevertec.check.exceptions.BadRequestException;
import main.java.ru.clevertec.check.exceptions.IntertalServerException;

public class ConvertersFactory {
    public  IStringConverter<?> createConverter(String simpleName) throws IntertalServerException {
        return switch (simpleName){
            case "Integer", "int" -> new IntegerConverter();
            case "Double", "double" -> new DoubleConverter();
            case "String" -> new StringConverter();
            default -> throw new IntertalServerException("Sorry. We can't fill the field with type: " + simpleName);
        };
    }
}
