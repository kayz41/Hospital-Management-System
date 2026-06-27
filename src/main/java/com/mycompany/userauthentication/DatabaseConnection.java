
package com.mycompany.userauthentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection
{
    private static final String URL = "jdbc:sqlserver://KAYZ141\\SQLEXPRESS;databaseName=HospitalManagementDB;encrypt=true;trustServerCertificate=true";
    
    private static final String USER = "sa";
    
    private static final String PASSWORD = "2004";
    
    public static Connection getConnection()
    {
        try 
        {
            return DriverManager.getConnection(URL,USER,PASSWORD);
        }
        catch (SQLException e)
        {
            System.out.println("Connection Error: " + e.getMessage());
            return null;
        }
    }
}


