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
import com.jorgedress.loginmodule.login.MainFrame;
import com.jorgedress.loginmodule.register.ConnectionClass.*;
import static com.jorgedress.loginmodule.register.ConnectionClass.serverPw;
import static com.jorgedress.loginmodule.register.ConnectionClass.url;
import static com.jorgedress.loginmodule.register.ConnectionClass.userName;

/**
 *
 * @author jorge
 */
public class ConnectionClass extends com.jorgedress.loginmodule.login.MainFrame {
    
    static String nameInput;
    static char[] pwInput;
    static String decPw;
    static String decServerPw;
    static String serverID;
    
    public static boolean sucefulLogin;
    
    
    private static boolean hasFailed;

    /**
     *
     * @param nameInput
     * @param decPw
     * @param resultLabel
     * @param attemps
     */
    
    public ConnectionClass() {
        nameInput = txtFieldName.getText();
        pwInput = pwField.getPassword();
        decPw = String.valueOf(pwInput);
        
             
    }
    
    
    
    public static void Connect(String name, String password, JLabel result, JLabel attemps) {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection con=DriverManager.getConnection(
                    //TimeZoneFix : ?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC [root on Default schema]
                    url,
                    userName,
                    serverPw
            );
            
            CallableStatement csts = con.prepareCall("SELECT Name, AES_DECRYPT(Password, 'decryptKey'), ID FROM login WHERE Name = ?;");
            
            csts.setString(1, name);
            
            ResultSet resset = csts.executeQuery();
            
            //System.out.println(name);
            //System.out.println(password);
            decServerPw = resset.getString(2);
            serverID = resset.getString(3);
                
            while(resset.next()) {
                
            
                if (decServerPw.equals(password)) {
                    result.setText("User "+ resset.getString(1) + " correct login, ID " + serverID);
                    
                    result.setForeground(Color.GREEN);
                    
                    //HERE TODO AFTER SUCESSFUL LOGIN
                    
                    
                    sucefulLogin = true;
                    
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
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        
    }
}
