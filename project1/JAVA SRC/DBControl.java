
import java.sql.*;   
import java.util.Scanner;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import java.util.ArrayList;
public class DBControl {

	//private static final String kAddress = "jdbc:mysql://localhost/moviedb";
	private static final String kAddress = "jdbc:mysql://localhost/test";
	
	private Connection DBConnection_;

	//connect to database server
	
	public DBControl () {
	}
	
	public DBControl (String username, String password) throws SQLException {
		DBConnection_ = DriverManager.getConnection(kAddress,username,password);
	}
	
	
	void get_star(){
		Scanner scanner = new Scanner(System.in);
		//boolean inputFlag = true;
		ArrayList<inputrecord> datas = new ArrayList<inputrecord>();
		datas.add(new inputrecord("first_name","","first name"));
		datas.add(new inputrecord("last_name","","last name"));
		System.out.println("Please enter asked data and press [enter] to finish, or just press [ENTER] key for empty data:");
		
		for(int i=0; i<datas.size();i++){
		System.out.println("Please enter "+datas.get(i).name+" of the star:");
		datas.get(i).data = scanner.nextLine();
		}
		
		try{
			Statement statem = DBConnection_.createStatement();
			String query = "SELECT 	movies.id,movies.title,movies.year,movies.director,movies.banner_url,movies.trailer_url FROM movies, stars_in_movies, stars WHERE movies.id = stars_in_movies.movie_id AND star_id = stars.id AND ";
			ResultSet myRs;
			if(datas.get(0).data.trim().length()!=0&&datas.get(1).data.trim().length()!=0){
				//case of both firstname and lastname are entered.
				query += "first_name = \"" + datas.get(0).data + "\" AND last_name = \"" + datas.get(1).data+" \" ORDER BY stars.first_name ASC;";
				myRs = statem.executeQuery(query);
				System.out.println("Query Access successed.\n"+"-------------------------------------------");
				while(myRs.next()){
					  
			  		  System.out.println(String.format("%-10s %s","ID:",myRs.getString("id")));
					  System.out.println(String.format("%-10s %s","title:",myRs.getString("title")));
					  System.out.println(String.format("%-10s %s","year:",myRs.getString("year")));
					  System.out.println(String.format("%-10s %s","director:",myRs.getString("director")));
				//	  System.out.println(String.format("%-10s %s","banner_url:",myRs.getString("banner_url")));
				//	  System.out.println(String.format("%-10s %s","director:",myRs.getString("trailer_url")));
					  System.out.println("-------------------------------------------");
				}
			}
			else if((datas.get(0).data.trim().length()>0&&datas.get(1).data.trim().length()==0)||(datas.get(0).data.trim().length()==0&&datas.get(1).data.trim().length()>0)){
				if (datas.get(0).data.trim().length()>0&&datas.get(1).data.trim().length()==0)
					query += "first_name = \"" + datas.get(0).data.trim() +"\" ORDER BY stars.first_name ASC;";
				else
					query += "last_name = \"" + datas.get(0).data.trim() +"\" ORDER BY stars.first_name ASC;";	
				myRs = statem.executeQuery(query);
				System.out.println("Query Access successed.\n"+"-------------------------------------------");
				while(myRs.next()){
					  System.out.println(String.format("%-10s %s","ID:",myRs.getString("id")));
					  System.out.println(String.format("%-10s %s","title:",myRs.getString("title")));
					  System.out.println(String.format("%-10s %s","year:",myRs.getString("year")));
					  System.out.println(String.format("%-10s %s","director:",myRs.getString("director")));
				//	  System.out.println(String.format("%-10s %s","banner_url:",myRs.getString("banner_url")));
				//	  System.out.println(String.format("%-10s %s","director:",myRs.getString("trailer_url")));
					  System.out.println("-------------------------------------------");
				}
				}
			else{
				
				System.out.println("Error: the star has to have at least one last/first name or both.");
			}
		}catch (Exception exc){
			System.out.println(exc.getMessage());
		};
		scanner.close();
	};

	void insert_star(){
			Scanner scanner = new Scanner(System.in);
			ArrayList<inputrecord> datas = new ArrayList<inputrecord>();
			datas.add(new inputrecord("first_name","","firstname"));
			datas.add(new inputrecord("last_name","","lastname"));
			datas.add(new inputrecord("dob","","data ot both in(YYYY/MM/DD)"));
			datas.add(new inputrecord("photo_url","","photo URL"));
			System.out.println("Please enter asked information and press [enter] key to enter, or just press [enter] key for empty date:");
			for(int i=0; i<datas.size();i++){
				System.out.println("Please enter "+datas.get(i).name+" of the star:");
				datas.get(i).data = "'"+scanner.nextLine()+"'";
				}
			
		try{
			Statement statem = DBConnection_.createStatement();
			String sql = "INSERT INTO stars";
			String types = "(";
			String values = "VALUES(";
			if(datas.get(0).data.trim().length()!=2&&datas.get(1).data.trim().length()!=2){
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
			else if((datas.get(0).data.trim().length()>2&&datas.get(1).data.trim().length()==2)||(datas.get(0).data.trim().length()==2&&datas.get(1).data.trim().length()>2)){
				String name;
				if(datas.get(0).data.trim().length()>2&&datas.get(1).data.trim().length()==2)
					name = datas.get(0).data.trim();
				else 
					name = datas.get(1).data.trim();
				types +="first_name,last_name,";
				values +="'',"+name+",";
				for(int i = 2;i<4;i++){
					if(datas.get(i).data!=""){
					types += datas.get(i).type +=",";
					values += datas.get(i).data += ",";
					}
				}
				sql += (types.substring(0, types.length()-1) +")"+values.substring(0, values.length()-1)+");");
				statem.executeUpdate(sql);
				System.out.println("data insertion succeed.");
			
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
		Scanner scanner = new Scanner(System.in);
		ArrayList<inputrecord> datas = new ArrayList<inputrecord>();
		datas.add(new inputrecord("first_name","","first name"));
		datas.add(new inputrecord("last_name","","last name"));
		datas.add(new inputrecord("cc_id","","credit card number"));
		datas.add(new inputrecord("address","","address"));
		datas.add(new inputrecord("email","","E-mail"));
		datas.add(new inputrecord("password","","password"));
		System.out.println("Please enter asked data and press [enter] to finish, Please do not enter empty data:");
		for(int i=0; i<datas.size();i++){
			System.out.println("Please enter "+datas.get(i).name+" of the customer:");
			datas.get(i).data = "'"+scanner.nextLine()+"'";
			}
		scanner.close();
		try{
			Statement statem = DBConnection_.createStatement();
			String sql = "INSERT INTO customers";
			String cc_sql = "SELECT COUNT(*) AS valid FROM creditcards WHERE creditcards.id = ";
			String types = "(";
			String values = "VALUES(";
			int cc_check = 0;
				for(int i = 0;i<=5;i++){
					if(datas.get(i).data!=""){
						types += datas.get(i).type +=",";
						values += datas.get(i).data += ",";
					}
					else{
						System.out.println("Insertion failed: empty data entered");	
						throw new Exception("Error: empty data entered");
					}
				}
				cc_sql += datas.get(2).data.substring(1, datas.get(2).data.length()-2)+";";
				ResultSet myRs = statem.executeQuery(cc_sql);
				if(myRs.next())
					cc_check = myRs.getInt("valid");
				if(cc_check>=1){
				sql += (types.substring(0, types.length()-1) +")"+values.substring(0, values.length()-1)+");");
				//JenniferSystem.out.println(sql);
				statem.executeUpdate(sql);
				System.out.println("data insertion succeed.");
				}else{
					
				System.out.println("Insertion failed: invalid credit card");
				throw new Exception("Error: empty data entered");
				}
		}catch (Exception exc){
			System.out.println(exc.getMessage());
		};
		
	};
	
	void delete_customer(){
		//get input from user
		String customerID;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter costumer ID which you want to delete and press [enter] to finish.");
		customerID = scanner.nextLine();
		scanner.close();
		try{
   			Statement statem = DBConnection_.createStatement();
   			String sql = "delete from customers where customers.id = " + customerID +";";
   			int result = statem.executeUpdate(sql); //if the database has been changed, result will equal number of changes
   			if (result>=1) 
   				System.out.println("The customer " +customerID+ " has been successful deleted.");
   			else
   				System.out.println("The customer " +customerID+ " does not exist in database.");
		}catch(Exception exc){
			System.out.println(exc.getMessage());
		};
	}
	//print all meta data
	void get_metadata(){
		try{
		Statement statem = DBConnection_.createStatement();
		DatabaseMetaData meta = DBConnection_.getMetaData();
		ResultSetMetaData rsmd;
		String[] types = {"TABLE"};
		int columnCount;
		//String[] columnName;
		ResultSet rs = meta.getTables(null, null, "%", types);
		ResultSet col;
		String sql;
		
		while (rs.next()) {
			System.out.println("----------------------------");
            System.out.println("Table : "+rs.getString("TABLE_NAME")+"\n");
            sql = "SELECT * FROM "+ rs.getString("TABLE_NAME").trim() + ";";
            col = statem.executeQuery(sql);
            rsmd = col.getMetaData();
            columnCount = rsmd.getColumnCount();
           // columnName = new String[columnCount];
            for(int i=0;i<columnCount;i++){
           
			System.out.println(String.format("%-15s %s",rsmd.getColumnName(i+1),rsmd.getColumnTypeName(i+1)));
            }
        }
  
		}catch(Exception exc){
			System.out.println(exc.getMessage());
		};
	};
	
	void sql_command_update(){
		String sql;
		String updateType;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter completed sql quary and press [enter] to finish.");
		sql = scanner.nextLine();
		scanner.close();
		try{
			updateType = sql.substring(0,6).toUpperCase();
   			Statement statem = DBConnection_.createStatement();
   			//if the update is select, display all data retrieved from result set
   			if(updateType.equals("SELECT")){
   				ResultSet rs = statem.executeQuery(sql);
   				ResultSetMetaData rsmd = rs.getMetaData();
   				int columnCount = rsmd.getColumnCount();
   				String[] columnName = new String[columnCount];
   				for(int i=0;i<columnCount;i++){
   					columnName[i] = rsmd.getColumnName(i+1);
   				}
   				System.out.println("Query Access successed");
   				int count = 1;
   				while(rs.next()){
   					System.out.println("--------------------"+Integer.toString(count)+"----------------------");
   					for(int i=0;i<columnCount;i++)
   						System.out.println(String.format("%-15s %s",columnName[i],rs.getString(columnName[i])));
   					count++;
   				}
   				System.out.println("--------------------END----------------------");
   				}
   			//execute update and get number of changes
   			else{
   				int changed = statem.executeUpdate(sql);
   				System.out.println(changed +" records have been successfully updated.");
   			}
		}catch(Exception exc){
			System.out.println("Error: Update is failed");
			System.out.println(exc.getMessage());
		};
	}
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
	public String type,data,name;
	public inputrecord(String type,String data,String name){
		this.type = type;
		this.data = data;
		this.name = name;
	}
}
