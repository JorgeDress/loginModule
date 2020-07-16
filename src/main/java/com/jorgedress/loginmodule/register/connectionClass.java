/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jorgedress.loginmodule.register;

import java.awt.Color;
import java.sql.*;

/**
 *
 * @author jorge
 */
public class connectionClass extends com.jorgedress.loginmodule.register.mainFrame {
    
    String returned;
    static String ID;
    
    public static String Connection(String name, String pw) {
        
        //FIX CONNECTION HERE
        
        try {  
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            
            Connection con=DriverManager.getConnection(
                    //timeZoneFix : ?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC [root on Default schema]
                    "jdbc:mysql://localhost:3306/login?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root",
                    ""
            );  
                 
            PreparedStatement prepStat;
            prepStat = con.prepareStatement("INSERT INTO login VALUES (?, AES_ENCRYPT(?, 'decryptKey'), ?)");

           
            prepStat.setString(1, name);
            prepStat.setString(2, pw);
            prepStat.setString(3, "0");
            
            prepStat.executeUpdate();
            
            //now select the column to know the ID
            
            prepStat = con.prepareStatement("SELECT ID FROM login WHERE Name = ?;");
                    
            prepStat.setString(1, name);
            
            //write to resultSet
            
            ResultSet resset = prepStat.executeQuery();
            //writeResultSet(resset);
            String returned;
            String ID;
            if (resset.next()) {
                //System.out.println(resset.getString(1));
                returned = resset.getString(1);
                Result.setText("Sucessly registered. ID: " + returned);
                Result.setForeground(Color.green);
            } else {
                //System.out.println("Error");
                returned = "Error";
                Result.setText("Error: can't get ID");
                Result.setForeground(Color.green);
            }
            ID = returned;
            //end
            con.close();
            
        } catch (Exception e) { 
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
        return ID;
    }   
    
    public static void main(String[] args) {
        System.out.println(txtName.toString());
        System.out.println(txtPassword.toString());
        /*try {  
            Class.forName("com.mysql.jdbc.Driver"); 
            
            Connection con=DriverManager.getConnection(
                    //fix : ?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC [root on Default schema]
                    "jdbc:mysql://localhost:3306/login",
                    "root",
                    ""
            );  
                
            Statement statement=con.createStatement();  
            ResultSet rs=statement.executeQuery("INSERT INTO login VALUES ('"
                    + txtPassword + "', '" + "" +  "')"); 
        } catch (Exception e) { 
            System.out.println(e);
        }   */
    }
}
