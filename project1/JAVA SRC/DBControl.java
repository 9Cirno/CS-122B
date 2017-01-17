
import java.sql.*;   
import java.util.Scanner;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import java.util.ArrayList;
public class DBControl {
	Connection DBConn = null;//data base connection
	private String url,username,password;
	private Connection DBConnection;
	
	public DBControl (String url,String username,String password){
	    this.url = url;
	    this.username = username;
	    this.password = password;
	}
	public DBControl (){
	    this.url = null;
	    this.username = null;
	    this.password = null;
	}
	//connect to database server
	void initialization (){
		try{
  		 //1
  		   //Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/test","root","roottest");
		   DBConnection = DriverManager.getConnection(url,username,password);
  		   System.out.println("connected");
  		   //Statement myStmt = myConn.createStatement();
  		   //ResultSet myRs = myStmt.executeQuery("select * from employees");
  		   //while (myRs.next()){};
  		  //1 String sql = "INSERT INTO movies" + "(id,title,year,director,banner_url,trailer_url)"+"VALUES(490003, 'Hostage', 2005, 'Florent Emilio Siri', 'http://ia.imdb.com/media/imdb/01/I/13/78/68m.jpg', 'http://images.apple.com/movies/miramax/hostage/hostage-ref.mov');";
  		  //2 myStmt.executeUpdate(sql);
  		  //3 System.out.println("inserted");
  		   //ResultSet myRs = myStmt.executeQuery("select * from movies");
  		  //while(myRs.next())
  		   //System.out.println(myRs.getString("title"));
  	   		}catch (Exception exc){
  	   			//exc.printStackTrace();
  		   		String Error = exc.getMessage();
  	   			if(Error.contains("Access denied for user"))
  	   			System.out.println("Username or/both Password is incorrect");
  	   			else
  	   			System.out.println(Error);
  	   			
  	   		}
	}
	void get_star(){
		Scanner scanner = new Scanner(System.in);
		//boolean inputFlag = true;
		ArrayList<inputrecord> datas = new ArrayList<inputrecord>();
		datas.add(new inputrecord("first_name",""));
		datas.add(new inputrecord("last_name",""));
		System.out.println("Please enter asked data and press [enter] to finish, or just press [ENTER] key for empty data:");
		//get first name;
		System.out.println("Please enter First Name of the star:");
		datas.get(0).data = scanner.nextLine();
		//get last name
		System.out.println("Please enter Last Name of the star:");
		datas.get(1).data = scanner.nextLine();
		
		
		try{
			Statement statem = DBConnection.createStatement();
			String query = "SELECT 	movies.id,movies.title,movies.year,movies.director,movies.banner_url,movies.trailer_url FROM movies, stars_in_movies, stars WHERE movies.id = stars_in_movies.movie_id AND star_id = stars.id AND ";
			ResultSet myRs;
			//ResultSet myRs = statem.executeQuery("select * from stars where ");
			//while(myRs.next())
		  	//	  System.out.println(myRs.getString("title"));
			if(datas.get(0).data.trim().length()!=0&&datas.get(1).data.trim().length()!=0){
				//case of both firstname and lastname are entered.
				query += "first_name = \"" + datas.get(0).data + "\" AND last_name = \"" + datas.get(1).data+" \" ORDER BY stars.first_name ASC;";
				//JenniferSystem.out.println(sql);
				myRs = statem.executeQuery(query);
				System.out.println("Query Access successed.");
			}
			else if((datas.get(0).data.trim().length()>0&&datas.get(1).data.trim().length()==0)||(datas.get(0).data.trim().length()==0&&datas.get(1).data.trim().length()>0)){
				String name = datas.get(0).data+datas.get(1).data;
				query += "last_name = \"" + name +" \" ORDER BY stars.first_name ASC;";
				myRs = statem.executeQuery(query);
				System.out.println("Query Access successed.");
				}
			else{
				InputStream exc = null;
				System.out.println("Error: the star has to have at least one last/first name or both.");
				throw new ApplicationException("Error: the star has to have at least one last/first name or both.", exc);
			}
		}catch (Exception exc){
			
		};
		scanner.close();
	};
	
	void insert_star(){
			Scanner scanner = new Scanner(System.in);
			//boolean inputFlag = true;
			ArrayList<inputrecord> datas = new ArrayList<inputrecord>();
			datas.add(new inputrecord("first_name",""));
			datas.add(new inputrecord("last_name",""));
			datas.add(new inputrecord("dob",""));
			datas.add(new inputrecord("photo_url",""));
			System.out.println("Please enter asked data and press [enter] to finish, or just press [ENTER] key for empty data:");
			//get first name;
			System.out.println("Please enter First Name of the star:");
			datas.get(0).data = "'"+scanner.nextLine()+"'";
			//get last name
			System.out.println("Please enter Last Name of the star:");
			datas.get(1).data = "'"+scanner.nextLine()+"'";
			//get dob
			System.out.println("Please enter Date of Birth of the star,use slash \"/\" to divide (YYYY/MM/DD) :");
			datas.get(2).data = "'"+scanner.nextLine()+"'";
			//get url
			System.out.println("Please enter photo url of the star:");
			datas.get(3).data = "'"+scanner.nextLine()+"'";
		try{
			Statement statem = DBConnection.createStatement();
			String sql = "INSERT INTO stars";
			String types = "(";
			String values = "VALUES(";
			if(datas.get(0).data.trim().length()!=0&&datas.get(1).data.trim().length()!=0){
				//case of both firstname and lastname are entered.
				for(int i = 0;i<=3;i++){
					if(datas.get(i).data!=""){
					types += datas.get(i).type +=",";
					values += datas.get(i).data += ",";
					}
				}
				
				sql += (types.substring(0, types.length()-1) +")"+values.substring(0, values.length()-1)+");");
				//JenniferSystem.out.println(sql);
				statem.executeUpdate(sql);
				System.out.println("data insertion succeed.");
			}
			else if((datas.get(0).data.trim().length()>0&&datas.get(1).data.trim().length()==0)||(datas.get(0).data.trim().length()==0&&datas.get(1).data.trim().length()>0)){
				String name = datas.get(0).data+datas.get(1).data;
				types +="first_name,last_name,";
				values +="'',"+name+",";
				for(int i = 2;i<=3;i++){
					if(datas.get(i).data!=""){
					types += datas.get(i).type +=",";
					values += datas.get(i).data += ",";
					}
				sql += (types.substring(0, types.length()-1) +")"+values.substring(0, values.length()-1)+");");
				statem.executeUpdate(sql);
				System.out.println("data insertion succeed.");
				}
			}
			else{
				InputStream exc = null;
				System.out.println("Error: the star has to have at least one last/first name or both.");
				throw new ApplicationException("Error: the star has to have at least one last/first name or both.", exc);
			}
		
			//throw new ApplicationException("Failed on reading file soandso", e);
		}catch (Exception exc){
			System.out.println(exc.getMessage());
		};
		scanner.close();
	};
	
	void insert_customer(){
		try{
			
		}catch (Exception exc){
			
		};
		
	};
	void delete_customer(){};
	void get_metadata(){};
	
	
	boolean is_valid_date(String date){
		int month = Integer.parseInt(date.substring(0,2));
		int day = Integer.parseInt(date.substring(3,5));
		int year = Integer.parseInt(date.substring(6,10));
		String slash1 = date.substring(2, 3);
		String slash2 = date.substring(5, 6);
		if(date.length()>10)
		return false;
		if(0>month||month>12)
		return false;
		if(0>day||day>31)
		return false;
		if(1700<year||year>2100)
		return false;
		if(slash1!="/"||slash2!="/")
		return false;
		return true;
	};
	
	
};//class close

class inputrecord{
	public String type,data;
	public inputrecord(String type,String data){
		this.type = type;
		this.data = data;

	}
}
