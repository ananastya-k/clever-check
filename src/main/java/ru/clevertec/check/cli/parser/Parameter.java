package main.java.ru.clevertec.check.cli.parser;

import main.java.ru.clevertec.check.cli.parser.converters.IStringConverter;
import main.java.ru.clevertec.check.cli.parser.validators.IValueValidator;
import main.java.ru.clevertec.check.cli.parser.validators.NoValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to define parameters for command line arguments.
 * This annotation is used to mark fields in a class that should be populated
 * from command line arguments.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {

    /**
     * The name of the parameter.
     * This is the name that should be used in the command line argument.
     */
    String name() default "";

    /**
     * The validateWith pattern of the parameter.
     * This is a regular expression pattern that the argument should match.
     */
    String validateWith() default "";

    /**
     * The description of the parameter.
     * This is a description of the parameter that can be used for documentation or help messages.
     */
    String description() default "";

    /**
     * Specifies if the parameter is required.
     * If true, the parameter must be provided in the command line arguments.
     */
    boolean required() default true;

    Class<? extends IValueValidator> validateValueWith() default NoValidator.class;
    String regex() default "";
}


