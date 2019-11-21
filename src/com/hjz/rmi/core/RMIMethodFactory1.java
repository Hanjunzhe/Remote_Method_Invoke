package com.hjz.rmi.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import com.mec.orm.core.PackageScanner;

public class RMIMethodFactory1 {
	
	private static  Map<String ,RMIMethodDefination1 >  methodPool;
//	hashMap �����Ͷ����Լ�newһ��hashmap��д��
	static {
		methodPool=new HashMap<>();
	}
	

	public void ScanPackage(String packagename) {
//		��ɨ�跽����ʵ�� new��һ���ڲ���
//		��Ҫд��һ����������
		new PackageScanner() {
			@Override
			public void dealClass(Class<?> klass) {
				if(klass.isAnnotationPresent(RMIInterface1.class)) {
					return;
				}
				RMIInterface1 rmiinterface=klass.getAnnotation(RMIInterface1.class);
//				�õ� ����Ӧע�����
				Class<?> []  interfacess =	rmiinterface.rmiInterfacelist();
				for(Class<?> interfaces:interfacess) {
					registryClass( interfaces,  klass);
				}
			}
			
		}.packageScanner(packagename);
	}
	
	
	public void registryClass(Class<?> interfaces, Class<?> klass) {
//		ע��
		if (interfaces == null || klass == null 
				|| !interfaces.isInterface() || klass.isInterface()
				|| !interfaces.isAssignableFrom(klass))	{
//			isAssignableFrom �ж��ǲ���ʵ��ʵ�ֽӿڵ���
			return;
		}
		
		try {
			Object object = klass.newInstance();
			
			Method[] methods = interfaces.getDeclaredMethods();
			for (Method method : methods) {
				String methodId = String.valueOf(method.toGenericString().hashCode());
				
				Class<?>[] parameterTypes = method.getParameterTypes();
				Method classMethod = klass.getDeclaredMethod(method.getName(), parameterTypes);
				
				RMIMethodDefination1 definition = new RMIMethodDefination1(object, classMethod);
				methodPool.put(methodId, definition);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	static RMIMethodDefination1 getMethod(String methodId) {
		return methodPool.get(methodId);
	}
	
}
