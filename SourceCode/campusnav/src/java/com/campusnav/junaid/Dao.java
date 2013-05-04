package com.campusnav.junaid;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Asadullah Naweed
 */
public class Dao {

    protected Connection con;
    protected boolean isOpen;
    protected String currentTable;
    
    private static final boolean usingMySQL = true;
       
    
        
    //private static final String username = "spades"; //"root";//
    ///private static final String password = "spades@lums_123";  //"admin";//
    
    private static final String username = "root";
    private static final String password = "admin";
    
    private static final String mysqlDriver = "com.mysql.jdbc.Driver";
    private static final String mysqlURL = "jdbc:mysql://localhost:3306/test";  
    
    
    public Dao() {
        isOpen = false;
    }

    protected Connection openConnection() {
        try {

            Class.forName(mysqlDriver);
            con = DriverManager.getConnection(mysqlURL,username,password);

            isOpen = true;
            
            return  con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void closeConnection() {
        if (!isOpen)
            return;
        try {
            con.close();
            isOpen = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
