package BookSystem;

import java.io.*;
import java.net.*;
import java.sql.*;

//hotel handle the booking request thread;

public class ServerHandler extends Thread{
	
	Socket socket;
	String tableName;
	String[] strs;

	BufferedReader reader;
	PrintStream writer;
	
	DataManage dm;
	
	public ServerHandler(Socket s, String str){
		socket = s;
		tableName = str;
	}
	
	public void run() {
		
		try{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintStream(socket.getOutputStream());
			
			String line=null;
			
			synchronized(this){
				line = reader.readLine();
				
				System.out.println("Message from broker---------->"+line);
				
				strs=line.split(":");
				
				String sqlString="insert into " + tableName + " (roomtype,name,phone,credit,checkin,checkout) values('"+ strs[2] +"','" + strs[3]+"','"+strs[4]+"','"+strs[5]+"','"+strs[6]+"','"+strs[7]+"')";
				dm = new DataManage(sqlString);
				int i = dm.bookroom();
				if(i != 0){
					writer.print(Constant.SUCCESS + Constant.CR_LF);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

}