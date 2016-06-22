package BookSystem;

import java.io.*;
import java.net.*;
import java.sql.*;

//Hotel Hilton in Melbourne; 
public class ServerHM {
	public static void main(String[] args){
		ServerSocket server = null;
		System.out.println("Hilton in Melbourne is running");
		try{
			server = new ServerSocket(Constant.PORT1);
			while(true){
					Socket sSocket = server.accept();
					new ServerHandler(sSocket, "hilton_m").start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}



