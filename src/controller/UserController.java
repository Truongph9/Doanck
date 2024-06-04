/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.JDBCConnection;

public class UserController {
    private JDBCConnection dbConnection;

    public UserController() {
        dbConnection = new JDBCConnection();
    }

    public boolean validateUser(String username, String password) {
        return dbConnection.validateUser(username, password);
    }
     public boolean registerUser(String username, String password) {
        return dbConnection.registerUser(username, password);
    }
}

