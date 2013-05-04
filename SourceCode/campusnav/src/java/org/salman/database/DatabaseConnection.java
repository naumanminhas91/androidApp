
	package org.salman.database;

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;

	public class DatabaseConnection {

		private String USER,
		PASSWORD,
		HOST,
		driverName;
		Statement statement;
		ResultSet result;
		private Connection connection;
		
		public DatabaseConnection( String _HOST, String _user, String _password, String _driverName ){
			
			HOST = _HOST;
			USER = _user;
			PASSWORD = _password;
			statement = null;
			result = null;
			connection = null;
			driverName = _driverName;
			
		}
		
		
		public static Connection connectToDB(String userName, String passWord, String _HOST, String _driverName ){
			
			Connection connection;
			
			try {
				
				//Class.forName(driverName);
				Class.forName(_driverName);
				
				connection = DriverManager.getConnection(_HOST, userName, passWord);
			} catch ( ClassNotFoundException e) {
				
				e.printStackTrace();
				return null;
			}catch( SQLException e ){
				e.printStackTrace();
				return null;
			}
			
			System.out.println("connection succesful");
			return connection;
			
		}
		
		
		
		public Connection connect( ){

			try {
				
				//Class.forName(driverName);
				Class.forName(driverName);
				
				connection = DriverManager.getConnection(HOST, USER, PASSWORD);
			} catch ( ClassNotFoundException e) {
				
				e.printStackTrace();
				return null;
			}catch( SQLException e ){
				e.printStackTrace();
				return null;
			}
			
			System.out.println("connection succesful");
			return connection;
			
		}
		
		public ResultSet executeTheQuery( String queryString){
			
			try {
				statement = connection.createStatement();
				result = statement.executeQuery( queryString);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return result;
		}
		

		public void close(){
			
			
			try {
				if(result != null){
					result.close();
				}
				if(statement != null){
					statement.close();
				}
				if( connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
		}
	}

