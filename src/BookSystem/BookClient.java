package BookSystem;

import java.net.*;
import java.io.*;

// a class to build a client in the broker to deal with booking request;

public class BookClient {
	protected Socket bSocket; 
	protected BufferedReader bReader;
	protected PrintStream bWriter;
	protected int port;
	protected String str;
	public String suc;
	
	BookClient(int cPort, String require){
		port = cPort;
		str = require;
	}
	
	public String startClient(){
		try{
			bSocket = new Socket("localhost", port);
			bReader = new BufferedReader(new InputStreamReader(bSocket.getInputStream()));
			bWriter = new PrintStream(bSocket.getOutputStream());	
			bWriter.print( str + Constant.CR_LF);
			suc = bReader.readLine();
		}catch(IOException e){
			e.printStackTrace();
		}	
		return suc;
	}
	
	public void closeClient(){
		try{
			bSocket.close();
			bReader.close();
			bWriter.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
