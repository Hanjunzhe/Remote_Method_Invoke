package com.hjz.rmi.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;

import com.mec.util.ArgumentMaker;
// 连接服务器
//传送 消息
public class RMIClient1 {
	
	private  int RMIport;
	private  String RMIip;
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	public int getRMIport() {
		return RMIport;
	}


	public void setRMIport(int rMIport) {
		RMIport = rMIport;
	}


	public String getRMIip() {
		return RMIip;
	}


	public void setRMIip(String rMIip) {
		RMIip = rMIip;
	}


	public RMIClient1(int RMIport,String RMIip) {
		this.RMIip=RMIip;
		this.RMIport=RMIport;
	}
	
	
	void ConnectToServer() {
		try {
			this.socket=new Socket(RMIip,RMIport);
			this.dis=new DataInputStream(socket.getInputStream());
			this.dos=new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	public Object invokeMethod( Method method ,Object[] args) {
//		将要实现的方法传过去
		ArgumentMaker argmaker=new ArgumentMaker();
		for(int i=0;i<args.length;i++) {
			argmaker.addArg("arg"+i, args[i]);
		}
		String argStr=argmaker.toString();
		
		Object res=null;
		try {
			dos.writeUTF(String.valueOf(method.toString().hashCode()));
			dos.writeUTF(argStr);
			
			String result =dis.readUTF();
//			将由服务端返回的JSON字符串 结果传递回来给客户端
			res=ArgumentMaker.gson.fromJson(result, method.getReturnType());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
		return res;
	}
	
	
	
	
}
