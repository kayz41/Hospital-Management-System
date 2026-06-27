/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.userauthentication;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Joel Kayz
 */
public class AuditLogger {
    
    public static void log(String action)
    {
            try
            {
                Connection con =
                        DatabaseConnection.getConnection();
                
                String sql =
                        "INSERT INTO AuditTrail "
                        + "(Username, Role, ActionPerformed) "
                        + "VALUES (?, ?, ?)";
                
                PreparedStatement pst =
                        con.prepareStatement(sql);
                
                pst.setString(1,
                        UserSession.username);
                
                pst.setString(2,
                        UserSession.role);
                
                pst.setString(3,
                      action);
                
                pst.executeUpdate();
            }
            catch(Exception e)
            {
               e.printStackTrace();
                
            }
            }
    
    
    
    
}
