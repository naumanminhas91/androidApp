package org.salman.map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import android.text.format.Time;
import org.salman.database.*;

public class WiFiMapper {

	final static int THREASHHOLD = 10;
	private DatabaseConnection dbConnection;
	private Connection connection;
	private ResultSet result;
	//should pass the last detected coordinates, its time current coordinate
	public String getLocation(WiFiCoordinate wifiCoordinates[], String/*Time*/ lastReadingTime){
		
		
		return null;
		
	}
	
	public ArrayList<String> getColumsIDsForRouters( WiFiCoordinate wifiCoordinate){
		
		int iterator = 0;
		ArrayList<String> columnIDs;
		String sqlQuery = "select column_name from router_info where ";
		for( String MACAddress: wifiCoordinate.getKeySet() ){
			
			if( wifiCoordinate.getKeySet().size() == 1 ||
					wifiCoordinate.getKeySet().size()-1 == iterator ){
			sqlQuery += "mac_adress=?";
			}else{
				sqlQuery += "mac_adress=? and ";
			}
			++iterator;
		}
		
		iterator=1;
		connection = dbConnection.connect();
		
		PreparedStatement statement;
		try {
			
			statement = connection.prepareStatement(sqlQuery);
			for( String MACAddress: wifiCoordinate.getKeySet() ){
				statement.setString(iterator, MACAddress);
				++iterator;
			}
			
			result = statement.executeQuery();
			columnIDs = new ArrayList<String>();
			
			while(result.next()){
				columnIDs.add( result.getString(1) );
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
		
		return columnIDs;
	}
}

