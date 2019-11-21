package com.hjz.rmi.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RMIClientProxy1 {
	private RMIClient1 client;
	
	public RMIClientProxy1() {
		
	}
	

	public void setClient(RMIClient1 client) {
		this.client = client;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<?>  klass) {
//		�ڴ������� ʵ���˵����� clinet ������ invokeMethod����
//		������ ǰ��Ҫ���Ϸ�������
//		loader, interfaces, h
		return (T) Proxy.newProxyInstance(klass.getClassLoader(), 
				klass.getInterfaces(), 
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						client.ConnectToServer();
						return client.invokeMethod(method, args);
					}
		}
				);

		
	}
	
	
}
