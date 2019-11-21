package com.hjz.rmi.core;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface RMIInterface1 {
	Class<?>[] rmiInterfacelist();
//  ��ע�͵�nameΪ �ӿڵ�klass��� ����
//	��ע�������� �̳��˽ӿڵ��ࣨ��Ϊ����ʵ������صķ����� 
}
