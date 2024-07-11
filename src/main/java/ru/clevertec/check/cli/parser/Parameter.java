package main.java.ru.clevertec.check.cli.parser;

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
     *
     * @return the name of the parameter
     */
    String name() default "";

    /**
     * The view pattern of the parameter.
     * This is a regular expression pattern that the argument value should match.
     *
     * @return the view pattern of the parameter
     */
    String view() default "";

    /**
     * The description of the parameter.
     * This is a description of the parameter that can be used for documentation or help messages.
     *
     * @return the description of the parameter
     */
    String description() default "";

    /**
     * Specifies if the parameter is required.
     * If true, the parameter must be provided in the command line arguments.
     *
     * @return true if the parameter is required, false otherwise
     */
    boolean required() default true;

}

