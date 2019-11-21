package com.hjz.rmi.core;

import java.lang.reflect.Method;

public class RMIMethodDefination1 {
	public  Object object;
	public  Method method;
	
	public RMIMethodDefination1(Object object,Method method) {
		this.method=method;
		this.object=object;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	@Override
	public String toString() {
		return method.getName();
		
	}

}
