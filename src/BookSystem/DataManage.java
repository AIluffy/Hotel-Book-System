package BookSystem;

import java.sql.*;
import java.io.*;

//A class to deal with the different request from broker;
public class DataManage {
	ServerDatabase sd = new ServerDatabase();
	StringBuilder sb = new StringBuilder();
	String sqlString, str;
	ResultSet rs;
	PrintStream writer;

	
	DataManage(String str, PrintStream w){
		this.sqlString = str;
		this.writer = w;
	}
	
	DataManage(String str){
		this.sqlString =str;
	}
	
	public void queryCity(){
		try{
			rs = sd.stmt.executeQuery(sqlString);
			while(rs.next()){
				str = rs.getString(1) + "\t\t" + rs.getString(2);
				sb.append(str).append(":");
			}
			writer.print(sb.toString()+Constant.CR_LF);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				sd.closeConn();
				if(rs != null) {
					rs.close();
					rs = null;
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void queryHotel(){
		try{
			rs = sd.stmt.executeQuery(sqlString);	
			while(rs.next()){
				str = rs.getInt(1) + "\t\t" + rs.getString(2);
				sb.append(str).append(":");
			}
			writer.print(sb.toString()+Constant.CR_LF);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				sd.closeConn();
				if(rs != null) {
					rs.close();
					rs = null;
				}
			}catch(SQLException e){
				e.printStackTrace();
			}

		}
	}
	
	public void queryRoomrate(){
		try{
			rs = sd.stmt.executeQuery(sqlString);
			while(rs.next()){
				str = rs.getString(1) + "\t\t" + rs.getInt(2) + "\t\t" + rs.getString(3) + "\t\t"+ rs.getInt(4);
				sb.append(str).append(":");			
			}
			writer.print(sb.toString()+Constant.CR_LF);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				sd.closeConn();
				if(rs != null) {
					rs.close();
					rs = null;
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public void queryVacancy(){
		try{
			rs = sd.stmt.executeQuery(sqlString);
			if(rs.next()){
				str = rs.getString(1) + "&" + rs.getString(2);
				sb.append(str).append(":");		
				while(rs.next()){
					str = rs.getString(1) + "&" + rs.getString(2);
					sb.append(str).append(":");	
					break;
				}
				writer.print(sb.toString()+Constant.CR_LF);
			}else{
				writer.print(Constant.ERROR + Constant.CR_LF);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				sd.closeConn();
				if(rs != null) {
					rs.close();
					rs = null;
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	
	public int bookroom(){
		int row = 0;
			try{
				row= sd.stmt.executeUpdate(sqlString);
			}catch(SQLException e){
				e.printStackTrace();
			}
		return row;
	}
	
}
