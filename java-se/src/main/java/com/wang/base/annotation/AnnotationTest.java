package com.wang.base.annotation;

import java.lang.annotation.*;

/**
 * The constants ANNOTATION_TYPE , CONSTRUCTOR , FIELD , LOCAL_VARIABLE , METHOD , PACKAGE , PARAMETER , TYPE , and TYPE_PARAMETER correspond to the declaration
 */
@Target(ElementType.METHOD)
/**
 * 注解的生命周期
 * SOURCE 只存在源码中
 * CLASS 编译后还存在
 * RUNTIME 运行时可以通过反射获得
 */
@Retention(RetentionPolicy.CLASS)
/**
 * 元注解：有了这个注解，子类就会集成父类的注解
 */
@Inherited
public @interface AnnotationTest {

    //注解元素
    byte b();

    short s();

    int i();

    long l();

    double d();

    float f();

    boolean bool();

    String str() default "";

    Class<?> clazz();

    AnotherAnnotation annotation();
}

@interface AnotherAnnotation {

    //注解元素
    byte[] b();

    short[] s();

    int[] i();

    long[] l();

    double[] d();

    float[] f();

    boolean[] bool();

    String[] str() default {"a", "b"};

    Class<?>[] clazz();
}
