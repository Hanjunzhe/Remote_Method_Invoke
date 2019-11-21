package com.hjz.rmi.core;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface RMIInterface1 {
	Class<?>[] rmiInterfacelist();
//  该注释的name为 接口的klass类的 数组
//	该注释用与在 继承了接口的类（因为里面实现了相关的方法） 
}
