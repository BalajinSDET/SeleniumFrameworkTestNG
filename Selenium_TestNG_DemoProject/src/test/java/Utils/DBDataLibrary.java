package Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class DBDataLibrary {
	private static String url = "jdbc:mysql://127.0.0.1:3306/dofy";    
	private static String driverName = "com.mysql.cj.jdbc.Driver";   
	private static String username = "root";   
	private static String password = "Invent@123";
	private static Connection con;
	private static String[][] inputArr;

	public static String[][] readDB(String deviceType) 
	{
		try {
			Class.forName(driverName);
			try {
				
				//Create a connection to DB by passing Url,Username,Password as parameters
				con = DriverManager.getConnection(url, username, password);
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);

				//Executing the Queries
				ResultSet rs = stmt.executeQuery(getQuery(deviceType));						
				rs.last();
				int rows = rs.getRow();
				ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
				int cols = rsmd.getColumnCount();
				System.out.println(rows +"--" + cols);
				inputArr= new String[rows][cols];

				int i =0;
				rs.beforeFirst();
				//Iterating the data in the Table
				while (rs.next())
				{
					for(int j=0;j<cols;j++)
					{
						inputArr[i][j]=rs.getString(j+1);
					}
					System.out.println();
					i++;
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println( ex +"Failed to create the database connection."); 
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			System.out.println(ex +" Driver not found."); 
		}
		return inputArr;
	}

	public static String getQuery(String deviceType)
	{
		switch (deviceType.toLowerCase()) 
		{
		case "phone":
			return "Select MNumber, Otp, DeviceType, PinCode, brandName, Model, Variant, TouchScnWorking, ScreenCondition,TouchGlassCondition,BodyCondition, PhysicalCondition, FunctionalProblems, Accessories, IsUnderWarranty,Warranty"
			+ " from createorder where DeviceType='"+deviceType+"'";
		case "tablet":
			return "Select MNumber, Otp, DeviceType, PinCode, brandName, Model, Variant, TouchScnWorking, ScreenCondition,TouchGlassCondition, BodyCondition, FunctionalProblems, Accessories, IsUnderWarranty,Warranty, ReferralCode"
			+ " from createorder where DeviceType='"+deviceType+"'";

		case "laptop" : case "desktop":
			return "Select MNumber, Otp, DeviceType, PinCode, brandName, Model, TouchScnWorking, ScreenCondition, ConfigDevice, ConfigDeviceGeneration, ConfigRam, ConfigHDD, BodyCondition,FunctionalProblems, Accessories, IsUnderWarranty,Warranty, ReferralCode"
			+ " from createorder where DeviceType='"+deviceType+"'";
		case "tv":
			return "Select MNumber, Otp, DeviceType, PinCode, brandName, Model, TouchScnWorking, ConfigDevice, ConfigDeviceGeneration, BodyCondition, Accessories, IsUnderWarranty,Warranty, ReferralCode"
			+ " from createorder where DeviceType='"+deviceType+"'";
			
		case "gamingconsole":
			return "Select MNumber, Otp, DeviceType, PinCode, brandName, Model, BodyCondition, ConfigRam, FunctionalProblems, Accessories, IsUnderWarranty,Warranty, ReferralCode"
			+ " from createorder where DeviceType='"+deviceType+"'";
			
		case "smartwatch":
			return "Select MNumber, Otp, DeviceType, PinCode, brandName, Model, TouchScnWorking, ScreenCondition, BodyCondition, FunctionalProblems, Accessories, IsUnderWarranty,Warranty, ReferralCode"
			+ " from createorder where DeviceType='"+deviceType+"'";
			
		case "earbud":
			return "Select MNumber, Otp, DeviceType, PinCode, brandName, Model, TouchScnWorking, BodyCondition, PhysicalCondition, Accessories, IsUnderWarranty, Warranty, ReferralCode"
			+ " from createorder where DeviceType='"+deviceType+"'";			
		}
		return null;
	}
	
	public static void updateQuery() {
		try {
			Class.forName(driverName);
			try {
				//Create a connection to DB by passing Url,Username,Password as parameters
				con=DriverManager.getConnection(url, username, password);
				System.out.println("Database connection established");
				
				Statement stmt1 = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				String Qry ="Select DeviceType from createorder where DeviceType='phone'";
				
				//Executing the Queries
				ResultSet rs1 = stmt1.executeQuery(Qry);						
				rs1.last();
				int rows = rs1.getRow();
				ResultSetMetaData rsmd = (ResultSetMetaData) rs1.getMetaData();
				int cols = rsmd.getColumnCount();
				System.out.println(rows +"--" + cols);				
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println( e +" Failed to create the database connection."); 
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e + " Driver not found.");
		}	
	}
	
}

