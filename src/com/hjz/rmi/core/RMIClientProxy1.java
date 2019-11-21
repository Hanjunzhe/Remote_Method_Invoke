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
//		在代理里面 实现了调用了 clinet 类里面 invokeMethod方法
//		（并且 前提要连上服务器）
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
