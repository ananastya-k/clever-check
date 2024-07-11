package main.java.ru.clevertec.check.cli.parser.converters;

// Interface for string conversion
public interface IStringConverter<T> {
    /**
     * Converts a string to a specific type.
     *
     * @param s the string to convert
     * @return the converted type
     */
    T convert(String s);

    /**
     * Checks if the string is valid for conversion.
     *
     * @param s the string to check
     * @return true if valid, false otherwise
     */
    boolean check(String s);
}