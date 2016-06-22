package BookSystem;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

//client HOPP;
public class ClientHOPP {
	protected Socket csocket;
	protected BufferedReader reader;
	protected PrintStream writer;

	public ClientHOPP() throws UnknownHostException, IOException{
		csocket = null;
		InputStream in = null;
		OutputStream out = null;
		
		csocket = new Socket("localhost", Constant.PORT);
		in = csocket.getInputStream();
		out = csocket.getOutputStream();
		//build connect between client and broker;
		
		reader = new BufferedReader(new InputStreamReader(in));
		writer = new PrintStream(out);
		
	}
	
	private String str;
	public ClientHOPP(String str)
	{
		this.str=str;
	}
	
	public void exit(){
		try{
			writer.print(Constant.EXIT + Constant.CR_LF);
			reader.close();
			writer.close();
			csocket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		// print exit command to broker and close the connection;
	}
	
	public String[] queryCity(){
		writer.print(Constant.CITY + Constant.CR_LF);
		ArrayList<String> tmpList = new ArrayList<String>();
		// define the temporary list to store city list; 
		String line = null;
		
		try{
			line = reader.readLine();
			//read the city from broker;
		}catch(IOException e){
			e.printStackTrace();
		}
				
		String[] strs=line.split(":");
		for(String str: strs)
		{
		tmpList.add(str);
		}
			
		String[] cityList = new String[tmpList.size()];
		tmpList.toArray(cityList); 
		// conform the city list in the form of array;
		return cityList;
		//send  city list query command to broker and get the city list from database;
	}
	
	public String[] queryHotel(String city){
		writer.print(Constant.HOTEL + city + Constant.CR_LF);
		// output the command hotel and the city a hotel in;
		ArrayList<String> tmpList = new ArrayList<String>();
		String line = null;
		
		try{
			line = reader.readLine();
		}catch(IOException e){
			e.printStackTrace();
		}

		String[] strs=line.split(":");
		for(String str: strs)
		{
		tmpList.add(str);
		}
			
		String[] hotelList = new String[tmpList.size()];
		tmpList.toArray(hotelList);
		return hotelList;
		//send query hotel list command to broker and get the hotel list from database;
	}
	
	public String[] queryRoomrate(String hotel){
		writer.print(Constant.ROOMRATE  + hotel + Constant.CR_LF);
		// output the room rate command and which hotel to query;
		ArrayList<String> tmpList = new ArrayList<String>();
		String line = null;
		try{
			line = reader.readLine();
		}catch(IOException e){
			e.printStackTrace();
		}
			
		String[] strs=line.split(":");
		for(String str: strs)
		{
		tmpList.add(str);
		}
	
		String[] roomrateList = new String[tmpList.size()];
		tmpList.toArray(roomrateList);
		return roomrateList ;
		//send  room rate query command to broker and get the list from database
	}
	
	public String bookRoom(String str){
		writer.print(Constant.BOOK + str + Constant.CR_LF );
		String result = null;
		try{
			result = reader.readLine();
		}catch(IOException e){
			e.printStackTrace();
		}
		if(result.startsWith(Constant.ERROR)){
			System.out.println("Windsor doesn't exit in Sydney.");
		}
		
		return result;
	}
	
	public String[] queryVacancy(String vaca){
		writer.print(Constant.VACANCY + vaca + Constant.CR_LF);
		ArrayList<String> tmpList = new ArrayList<String>();
		String result = null;
		String[] day = null;
		try{
			result = reader.readLine();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		if(result.startsWith(Constant.ERROR)){
			day = null;
		}else{
			String[] strs = result.split(":");
			
			for(String str: strs)
			{
			tmpList.add(str);
			}
		
			day = new String[tmpList.size()];
			tmpList.toArray(day);
		}
		return day ;	
	}



}
