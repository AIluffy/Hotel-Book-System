package BookSystem;

import java.io.*;
import java.net.*;
import java.sql.*;

public class Broker {
	public static void main(String[] args){
		ServerSocket s = null;
		System.out.println("The broker is running");
		try{
			//build a client connection;
			s= new ServerSocket(Constant.PORT);
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}

		while(true) {
			Socket incoming = null;
			try{
				incoming = s.accept();
			}catch(IOException e){
				System.out.println(e);
				continue;
			}	
			new SocektHandler(incoming).start();
		}
	}
}

//multiple thread;
class SocektHandler extends Thread {
	Socket incoming;
	
	BufferedReader reader;
	PrintStream writer;
	
	SocektHandler(Socket incoming){
		this.incoming = incoming;
		
	}
	//construct function;
	
	public void run(){
		try{
			writer = new PrintStream(incoming.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
			//read from and write to client;
			
			while(true){
				String line = reader.readLine();
				
				if(line == null){
					break;
				}
				
				System.out.println("Message from client hopp--------->"+line);
				
				if(line.startsWith(Constant.CITY)){
					queryCityRequest();
				}else if(line.startsWith(Constant.HOTEL)){
					queryHotelRequest(losePrefix(line, Constant.HOTEL));
				}else if(line.startsWith(Constant.ROOMRATE)){
					queryRoomrateRequest(losePrefix(line, Constant.ROOMRATE));
				}else if(line.startsWith(Constant.VACANCY)){
					queryVacancy(losePrefix(line, Constant.VACANCY));
				}else if(line.startsWith(Constant.BOOK)){
					bookroom(losePrefix(line, Constant.BOOK));
				}else if(line.startsWith(Constant.EXIT)){
					break;	
				}else {
					writer.print(Constant.ERROR + Constant.CR_LF);
				}	
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	 //cut the command prefix;
	public String losePrefix(String str, String prefix) {
	    int index = prefix.length();
	    String ret = str.substring(index).trim();
	    return ret;  
	}	
	
	//send query city command;
	public void queryCityRequest() {
		String str = "SELECT cityid, cityname FROM city";
		DataManage dm = new DataManage(str, writer);
		dm.queryCity();
	}
	
	//send query hotel command;
	public void queryHotelRequest(String req) {
		String str = "SELECT idhotel, hotelname FROM hotel WHERE cityname = '" + req + "';";
		DataManage dm = new DataManage(str, writer);
		dm.queryHotel();
	}
	
	//send roomrate query;
	public void queryRoomrateRequest(String req) {
		String[] strs = req.split(":");
		String str = "SELECT hotelname, roomid, roomtype, roomrate FROM roomrate WHERE cityid = '" + strs[0] + "'and hotelid = '" + strs[1] + "';";
		DataManage dm = new DataManage(str, writer);
		dm.queryRoomrate();
	}
	
	//sned query vacancy query;
	public void queryVacancy(String req){
		DataManage dm = null;
		String[] strs = req.split(":");
		if(strs[0].equalsIgnoreCase("A") && strs[1].equalsIgnoreCase("A")){
			String str = "SELECT checkin, checkout FROM hilton_m WHERE roomtype = 'single';";
			dm = new DataManage(str, writer);
			dm.queryVacancy();
		}else if(strs[0].equalsIgnoreCase("A") && strs[1].equalsIgnoreCase("B")){
			String str = "SELECT checkin, checkout FROM hilton_m WHERE roomtype = 'double';";
			dm = new DataManage(str, writer);
			dm.queryVacancy();
		}else if(strs[0].equalsIgnoreCase("A") && strs[1].equalsIgnoreCase("C")){
			String str = "SELECT checkin, checkout FROM hilton_m WHERE roomtype = 'luxury';";
			dm = new DataManage(str, writer);
			dm.queryVacancy();
		}else if(strs[0].equalsIgnoreCase("B") && strs[1].equalsIgnoreCase("A")){
			String str = "SELECT checkin, checkout FROM windsor_m WHERE roomtype = 'single';";
			dm = new DataManage(str, writer);
			dm.queryVacancy();
		}else if(strs[0].equalsIgnoreCase("B") && strs[1].equalsIgnoreCase("B")){
			String str = "SELECT checkin, checkout FROM windsor_m WHERE roomtype = 'double';";
			dm = new DataManage(str, writer);
			dm.queryVacancy();
		}else if(strs[0].equalsIgnoreCase("B") && strs[1].equalsIgnoreCase("C")){
			String str = "SELECT checkin, checkout FROM windsor_m WHERE roomtype = 'luxury';";
			dm = new DataManage(str, writer);
			dm.queryVacancy();
		}else if(strs[0].equalsIgnoreCase("C") && strs[1].equalsIgnoreCase("A")){
			String str = "SELECT checkin, checkout FROM hilton_s WHERE roomtype = 'single';";
			dm = new DataManage(str, writer);
			dm.queryVacancy();
		}else if(strs[0].equalsIgnoreCase("C") && strs[1].equalsIgnoreCase("B")){
			String str = "SELECT checkin, checkout FROM hilton_s WHERE roomtype = 'double';";
			dm = new DataManage(str, writer);
			dm.queryVacancy();
		}else if(strs[0].equalsIgnoreCase("C") && strs[1].equalsIgnoreCase("C")){
			String str = "SELECT checkin, checkout FROM hilton_s WHERE roomtype = 'luxury';";
			dm = new DataManage(str, writer);
			dm.queryVacancy();
		}
	}
	
	//send query booking request;
	public void bookroom(String req) {
		String request = req;
		String[] strs=request.split(":");
		
		//decide to build a connection between broker and 3 hotel;
		if(strs[0].equalsIgnoreCase("A") && strs[1].equalsIgnoreCase("A")){
			BookClient bc = new BookClient(Constant.PORT1, request);
			String result = bc.startClient();
			writer.print(result + Constant.CR_LF);
			System.out.println("Hilton in Melbourne is running");
			bc.closeClient();
		}else if(strs[0].equalsIgnoreCase("A") && strs[1].equalsIgnoreCase("B")){
			BookClient bc1 = new BookClient(Constant.PORT2, request);
			String result = bc1.startClient();
			writer.print(result + Constant.CR_LF);
			System.out.println("Windsor in Melbourne is running");
			bc1.closeClient();
		}else if(strs[0].equalsIgnoreCase("B") && strs[1].equalsIgnoreCase("A")){
			BookClient bc2 = new BookClient(Constant.PORT3, request);
			String result = bc2.startClient();
			writer.print(result + Constant.CR_LF);
			System.out.println("Hilton in Sydney is running");
			bc2.closeClient();
		}else{
			writer.print(Constant.ERROR + Constant.CR_LF);
		}
	}		
}
	
	
	


