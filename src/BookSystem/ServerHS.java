package BookSystem;

import java.io.*;
import java.net.*;
import java.sql.*;

//Hotel Hilton in Sydney;

public class ServerHS {
	public static void main(String[] args){
		ServerSocket server = null;
		System.out.println("Hilton in Sydney is running");
		try{
			server = new ServerSocket(Constant.PORT3);
			while(true){
					Socket sSocket = server.accept();
					new ServerHandler(sSocket, "hilton_s").start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}




