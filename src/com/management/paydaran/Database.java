package com.management.paydaran;


//. Import required packages
import java.sql.*;

public class Database {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:derby://localhost:1527/Vezarat_DB"; //;shutdown=true
//<C://Users/pc10/Documents/NetBeansProjects/Management/dist/>
   //  Database credentials
   static final String USER = "paydaran";
   static final String PASS = "paydaran";
   
   Connection conn = null;
   Statement stmt = null;
   ResultSet rs = null;
   
   public void openDb(){
	   try {
		   // Register JDBC driver
		      Class.forName("java.sql.Driver");
		      
		   // setup the properties 
		     java.util.Properties prop = new java.util.Properties();
                     prop.put("useUnicode", "true");
		     prop.put("charSet", "UTF-8");
                     //prop.put("shutdown", "true");
                     prop.put("derby.language.sequence.preallocator", "1"); //derby.language.sequence.
		     prop.put("user", USER);
		     prop.put("password", PASS);
		      
		      // Open a connection
		      //System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL, prop);
		      //System.out.println("Connected...!");
	} catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
   }
   
   public ResultSet createQuery(String str){	
	   try {
		 //Execute a query
		   stmt = conn.createStatement();
		   rs = stmt.executeQuery(str);
		   //System.out.println("Query created...!");
	} catch (Exception e) {
		// TODO: handle exception
	}   
	return rs;
   }
   
   public boolean insertDb(String str){
	   boolean result = true;
	   try {
		Statement insertstmt = conn.createStatement();
		insertstmt.execute(str);
	} catch (SQLException e) {
		e.printStackTrace();
		result = false;
	}
	   
	   return result;
   }
   
   public boolean removefromDb(String str){
	   boolean result = false;
	   try {
		Statement removestmt = conn.createStatement();
		removestmt.execute(str);
                result = true;
	} catch (SQLException e) {
		e.printStackTrace();
		result = false;
	}   
	   return result;
   }
   
   public boolean updateDb(String str){
	   boolean result = true;
	   try {
		Statement removestmt = conn.createStatement();
		removestmt.execute(str);
	} catch (SQLException e) {
		e.printStackTrace();
		result = false;
	}   
	   return result;
   }
   
   public void closeDb(){
	   //Clean-up environment
	   try {
		   rs.close();
		   stmt.close();
		   conn.close();
	} catch (Exception e) {
		// TODO: handle exception
	}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   //System.out.println("Goodbye!");
   }
}