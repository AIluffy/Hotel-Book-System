package BookSystem;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientUI {
	
	protected BufferedReader console;
	protected ClientHOPP clientHOPP;
	StringBuilder sb ;
	
	//instruction interface;
	public static void main(String[] args){
		ClientUI ui = new ClientUI();
		
		System.out.println(
                "---------------------------------------------"+"\n"
               + "|"+"city: show the cities                      "+"|"+"\n"	
               +"---------------------------------------------"+"\n"
		        +"|"+"hotel : show hotels                        "+"|"+"\n"
		        +"---------------------------------------------"+"\n"
               + "|"+"roomrate : show the roomrate               "+"|"+"\n"
               +"---------------------------------------------"+"\n"
               + "|"+"book: book a room                          "+"|"+"\n"
               +"---------------------------------------------"+"\n"
               + "|"+"query: query a room vacancy                "+"|"+"\n"
               +"---------------------------------------------"+"\n"
		        +"|"+"exit: exit                                 "+"|"+"\n"     
   	        +"---------------------------------------------"+"\n");
		ui.loop();
	}
	
	//Construct function
	public ClientUI(){
		clientHOPP = null;
		
		try {
			clientHOPP = new ClientHOPP();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		console = new BufferedReader(new InputStreamReader(System.in));
	
	}

	//Loop function
	public void loop() {
		while (true) {	
			String line =null;
			
			try{
				System.out.println("Enter one of the following request :\n" + Constant.CITY + " - " + Constant.HOTEL + " - " + Constant.ROOMRATE + " - " + Constant.VACANCY + " - " + Constant.BOOK + " - " + Constant.EXIT + ".");
				// formulate the request;
				line = console.readLine();
			}catch(IOException e){
				clientHOPP.exit();
				e.printStackTrace();
				System.exit(1);
			}
			
			if(line.equalsIgnoreCase(Constant.CITY)){
				queryCity();
				//query city list;
			}else if(line.toUpperCase().startsWith(Constant.HOTEL)){
				queryHotel();
				//query hotel list;
			}else if(line.toUpperCase().startsWith(Constant.ROOMRATE)){
				queryRoomrate();
				//query room rate list;
			}else if(line.toUpperCase().startsWith(Constant.VACANCY)){
				queryVacancy();
				//query whether a room is available;
			}else if(line.toUpperCase().startsWith(Constant.BOOK)){
				bookRoom();
				// start booking;
			}else if(line.equalsIgnoreCase(Constant.EXIT)){
				clientHOPP.exit();
				System.exit(0);
				//exit 
			}else{
				System.out.println("Unrecognised command");
			}
		}
	}
	
	//query a city list;
	public void queryCity(){
		String[] cityList = clientHOPP.queryCity();
		
		if(cityList.length == 0){
			System.out.println("Querying city failure");
		}else{
			System.out.println("city list:");
			for(int i = 0; i < cityList.length; i++){
				System.out.println(cityList[i]);

			}
		}
	}
	
	//query the hotel;
	public void queryHotel() {
		String city = null;
		
		try{
			System.out.println("Please selcet a city name (Melbourne or Sydney):");
			city = console.readLine();	
		}catch(IOException e){
			e.printStackTrace();
		}
		String[] hotelList = clientHOPP.queryHotel(city);
		if(hotelList.length == 0){
			System.out.println("Querying hotel failure");
		}else{
			System.out.println("hotel in " + city + ":");
			for(int i = 0; i < hotelList.length; i++){
				System.out.println(hotelList[i]);	
			}
		}
	}
	
	//query the roomrate;
	public void queryRoomrate() {
		sb = new StringBuilder();
		String str = null;
		try{
			System.out.println("Please selcet a city id (Melbourne = 1 , Sydney = 2):");
			String city = console.readLine();
			sb.append(city).append(":");
			System.out.println("Please select a hotel id (Hilton = 1 , Windsor = 2):");
			String hotel = console.readLine();
			sb.append(hotel).append(":");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		str = sb.toString();
		
		String[] roomrateList = clientHOPP.queryRoomrate(str);
		if(roomrateList.length == 0){
			System.out.println("Querying roomrate failure");
		}else{
			System.out.println("roomrate " + ":\n hotel\t\troomid\t\troomtype\trate");
			for(int i = 0; i < roomrateList.length; i ++){
				System.out.println(roomrateList[i]);
				// show the room rate list;
			}
		}
	}
	
	//query a room availability;
	public void queryVacancy(){
		sb = new StringBuilder();
		String checkin = null;
		String checkout = null;
		int i = 0;
		try{
			System.out.println("Please select a hotel you wanna in:(A.Hilton in Melbourne \t\t B.Windsor in Melbourne \t\t C.HIlton in Sydney)");
			String hotel = console.readLine();
			sb.append(hotel).append(":");
			System.out.println("Please select a roomtype (A.single \t\t B.double \t\t C.luxury):");
			String roomtype = console.readLine();
			sb.append(roomtype).append(":");
			System.out.println("type a day in 2015 March to check in:");
			checkin = console.readLine();
			System.out.println("type a day in 2015 March to check out:");
			checkout = console.readLine();
		}catch(IOException e){
			e.printStackTrace();
		}	
		String str = sb.toString();	
		String[] day = clientHOPP.queryVacancy(str);
		
		if(day == null){
			System.out.println("this room is available");
		}else{
			int in = 0, in1 = 0, out = 0, out1 = 0;
			//compare check_in day and check_out day;
			for(int j = 0; j < day.length; j++){
				String[] duration = day[j].split("&");
				try{
					in = Integer.parseInt(duration[0]);
					in1 = Integer.parseInt(checkin);
					out = Integer.parseInt(duration[1]);
					out1 = Integer.parseInt(checkout);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				if(in >in1 && in > out1 ){
					i = i + 0;
				}else if (in1 > out){
					i = i + 0;
				}else{
					i = i + 1;
				}
			}
			if(i == 0){
				System.out.println("you can book this room");
			}else{
				System.out.println("this room has been booked");
			}
		}	
	}
	
	//Start booking room;
	public void bookRoom(){
		sb = new StringBuilder();
		boolean flag = true;
		
		try{
			System.out.println("Please choose a city (A.Melbourne B.Sydney):");
			String city = console.readLine();
			sb.append(city).append(":");
			
			
			System.out.println("Please choose a hotel (A.Hilton B.Windsor):");
			String hotel = console.readLine();
			sb.append(hotel).append(":");
			
			
			System.out.println("Please select a room type (single; double; luxury):");
			String type = console.readLine();
			sb.append(type).append(":");
			
			
			System.out.println("Enter your name:");
			String name = console.readLine();
			sb.append(name).append(":");
			
				
			System.out.println("Enter your phone:");
			String phone = null;
			while(true){
				phone = console.readLine();
				String regExp = "^[1][0-9]{10}$";
				Pattern p = Pattern.compile(regExp);
				Matcher m = p.matcher(phone);
				flag = m.find();
				if(flag == true){
					break;
				}else{
					System.out.println("Please type a correct phone number(11 bit) again:");
				}
			}
			sb.append(phone).append(":");
			
		
			System.out.println("Enter your credit card number:");
			String credit = null;
			while(true){
				credit = console.readLine();
				String regExp = "^[0-9]{18}$";
				Pattern p = Pattern.compile(regExp);
				Matcher m = p.matcher(credit);
				flag = m.find();
				if(flag == true){
					break;
				}else{
					System.out.println("Please type a correct creidt number(18 bit) again:");
				}
			}
			sb.append(credit).append(":");
			
		
			System.out.println("Enter one day in March 2015 to check in (1-31):");
			String checkin = console.readLine();
			sb.append(checkin).append(":");
	
	
			System.out.println("Enter one day in March 2015 to check out (1-31):");
			String checkout = console.readLine();
			sb.append(checkout);
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		String str = sb.toString();	
		
		String res= clientHOPP.bookRoom(str);
		if(res.equals(Constant.SUCCESS)){
			System.out.println("you have book the room successfully. ");
		}else{
			System.out.println("Fail! TRY AGAIN!");
		}
	}	
	
}
