package com.hjz.rmi.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
// ����������
public class RMIServer1 implements Runnable{
	private int port;
	private ServerSocket server;
	private volatile  boolean   goon;
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public RMIServer1(int port) {
		this.port=port;
		
	}
	void Startup() {
		if(goon==true) {
			return;
		}
		try {
			server=new ServerSocket(port);
			goon=true;
			new Thread(this,"serverThread").start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	void shutdown() {
		if(goon==false) {
			return ;
		}
		goon=false;
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
//		���� �������˵� ͨ���ߵ��Ľ���
//		���� �ͻ��˴�����������ִ��
		Socket socket;
		try {
			socket = server.accept();
			new RMIActioner1(socket) ;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
