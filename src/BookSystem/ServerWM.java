package BookSystem;

import java.io.*;
import java.net.*;
import java.sql.*;

//Hotel Windsor in Melbourne;

public class ServerWM {
	public static void main(String[] args){
		ServerSocket server = null;
		System.out.println("Windsor in Melbourne is running");
		try{
			server = new ServerSocket(Constant.PORT2);
			while(true){
					Socket sSocket = server.accept();
					new ServerHandler(sSocket, "windsor_m").start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}



