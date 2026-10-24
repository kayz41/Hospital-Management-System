
package com.mycompany.userauthentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection
{ 
    private static final String URL = 
            System.getenv("HMS_DB_URL");
    
    private static final String USER =
            System.getenv("HMS_DB_USER");
    
    private static final String PASSWORD =
            System.getenv("HMS_DB_PASSWORD");
    
    
    
    public static Connection getConnection(){
        try {
            if (URL == null || URL.isBlank()){
                throw new SQLException(
                "HMS_DB_URL environment variable is not configured.");
            }
            
            if(USER == null || USER.isBlank()){
                throw new SQLException(
                "HMS_DB_USER environment variable is not configured.");
            }
            
            if(PASSWORD == null || PASSWORD.isBlank()){
                
                throw new SQLException(
                "HMS_DB_PASSWORD environment variable is not configured.");
            }
            
            
            return DriverManager.getConnection(
            URL,
            USER,
            PASSWORD);
        }
        
        catch(SQLException e){
           throw new RuntimeException(
           "Database connection failed:\n"
           + e.getMessage(),
           e);            
           
            
              
        }  
        }
    }
