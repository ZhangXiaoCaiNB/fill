package com.zhangxiaocai.fill;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Fill {
    int intVal() default 0;
    byte byteVal() default 0;
    char charVal() default '\u0000';
    short shortVal() default 0;
    long longVal() default 0L;
    double doubleVal() default 0.0d;
    float floatVal() default 0.0f;
    boolean booleanVal() default false;
    String stringVal() default "";
    Class<?> objectVal() default Object.class;
}
