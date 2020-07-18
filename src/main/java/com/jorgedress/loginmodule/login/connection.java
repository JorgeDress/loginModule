/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jorgedress.loginmodule.login;

import java.awt.Color;
import java.sql.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.jorgedress.loginmodule.login.mainFrame;

/**
 *
 * @author jorge
 */
public class connection extends com.jorgedress.loginmodule.login.mainFrame {
    
    static String nameInput;
    static char[] pwInput;
    static String decPw;
    static String decServerPw;
    static String serverID;
    private static boolean hasFailed;

    /**
     *
     * @param nameInput
     * @param decPw
     * @param resultLabel
     * @param attemps
     */
    
    public connection() {
        nameInput = txtFieldName.getText();
        pwInput = pwField.getPassword();
        decPw = String.valueOf(pwInput);
    }
    
    public static void Connect(String name, String password, JLabel result, JLabel attemps) {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection con=DriverManager.getConnection(
                    //fix : ?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC [root on Default schema]
                    "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7355802",
                    "sql7355802",
                    "EZWxbHWSal"
            );
            
            CallableStatement csts = con.prepareCall("SELECT Name, AES_DECRYPT(Password, 'decryptKey'), ID FROM login WHERE Name = ?;");
            
            csts.setString(1, name);
            
            ResultSet resset = csts.executeQuery();
            
            //System.out.println(name);
            //System.out.println(password);
            
            while(resset.next()) {
                decServerPw = resset.getString(2);
                serverID = resset.getString(3);
                //System.out.println(resset.getString(1) + " " + resset.getString(2) + " " + resset.getString(3));
                if (decServerPw.equals(password)) {
                    result.setText("User "+ resset.getString(1) + " correct login, ID " + serverID);
                    //resultLabel.setText("Correct login for user Jorge with ID " + resset.getString(3));
                    
                    result.setForeground(Color.GREEN);
                    
                    //HERE TODO AFTER SUCESSFUL LOGIN
                    
                    
                } else {
                    int attempsNum;
                    int minusOne = Integer.parseInt(attemps.getText())-1;
                    attempsNum = Integer.parseInt(attemps.getText());
                    attemps.setText(String.valueOf(minusOne));
                    if (attempsNum > 0 ) {
                        result.setText("Wrong password, try again.");
                        result.setForeground(Color.RED);
                    } else if (attempsNum == 0) {
                        JOptionPane.showMessageDialog(
                                null, 
                                "Too much incorrect attemps, try again later",
                                "Attemps",
                                JOptionPane.ERROR_MESSAGE
                        );
                        System.exit(0);
                    }
                    
                }
                con.close();
                
            }
            
            /*PreparedStatement prepStat;
            
            //'AES_DECRYPT(PASSWORD, 'decryptKey'),
            prepStat = con.prepareStatement("SELECT Name, ID FROM login WHERE Name = ?;");
                    
            prepStat.setString(1, name);
            
            //write to resultSet
            
            ResultSet resuset = prepStat.executeQuery();
            //String serverPw;
            //String serverID;
            
            while(resuset.next()) {
                System.out.println(resuset.getString(1) + " " +resuset.getString(3));
                System.out.println("It worked");
                con.close();
            }*/
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        
    }
}
