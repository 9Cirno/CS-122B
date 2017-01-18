// JDBC Example - printing a database's metadata
	
	
	import java.sql.*;                              // Enable SQL processing
	//import DBControl;
	public class JDBC
	{	       
		 public static void main(String[] args) {

			   DBControl con1 = null;
	    	   //String url = "jdbc:mysql://localhost/test";
	    	   String username = "root"; 
	    	   String password = "roottest";
	    	   try{
	    		   con1 = new DBControl(username,password);
	    	   }catch (Exception exc){   
	    		   
	    	   }
	    	   //all below function calls for test single function, you can delete all 
	    	   //con1.initialization();
	    	   //con1.get_star();;
	    	   //con1.insert_star();
	    	   //con1.insert_customer();
	    	   //con1.delete_customer();
	    	   //con1.sql_command_update();
	    	   //con1.get_metadata();
	    	   System.out.println("end");
	    	   
	 
	   }
}