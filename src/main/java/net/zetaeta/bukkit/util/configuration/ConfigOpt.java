package net.zetaeta.bukkit.util.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigOpt {
    String value();
    Class<?> elementType() default void.class;
    boolean hasDefault() default false;
    int defInt() default 0;
    long defLong() default 0;
    long defFloat() default 0;
    long defDouble() default 0;
    boolean defBoolean() default false;
    String defString() default "";
}
