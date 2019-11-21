package com.hjz.rmi.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.Socket;

import com.mec.util.ArgumentMaker;

public class RMIActioner1 implements Runnable {
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
//	建立服务器的通信信道
//处理 客户端发来的 方法名称 以及方法的参数
	
	public RMIActioner1(Socket socket) {
		try {
			dis=new DataInputStream(socket.getInputStream());
			dos=new DataOutputStream(socket.getOutputStream());
			new  Thread(this).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public void run() {
		try {
			String methodname=dis.readUTF();
			String arguments=dis.readUTF();
			
			RMIMethodDefination1  methodDefination=RMIMethodFactory1.getMethod(methodname);
			Object object=methodDefination.getObject();
			Method method=methodDefination.getMethod();
			
			Object values[] =getParameterValues(method, arguments);
			try {
				Object result=method.invoke(object, values);
				dos.writeUTF(ArgumentMaker.gson.toJson(result));
				//将执行结果传过去
				
				close();
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private Object[] getParameterValues(Method method ,String argsString) {
		ArgumentMaker argumentMaker = new ArgumentMaker(argsString);
		Parameter[] parameters = method.getParameters();
		int ParameterCount=method.getParameterCount();
		if(ParameterCount<=0){
			return new Object[] {};
		}
		Object[] res=new Object[ParameterCount];
		for(int i=0;i<ParameterCount;i++) {
			String argname="arg"+i;
			Object argvalue=argumentMaker.getValue(argname,
					parameters[i].getParameterizedType());
			res[i]=argvalue;
		}
		//得到了  参数的一个数组并且里面存着参数的值
		return res;
	}

	private void close() {
		try {
			if (dis != null) {
				dis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			dis = null;
		}
		try {
			if (dos != null) {
				dos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			dos = null;
		}
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			socket = null;
		}
	}
}
