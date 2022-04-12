package com.visioncare.DAO;
import com.visioncare.model.Patient;
import com.visioncare.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginData implements LoginDAOInterface {
    DBConnection dbConnection;

    @Override
    public void createUser(User user) {
        try {
            dbConnection = new DBConnection();
            int id;
            dbConnection.update("INSERT INTO user_data(user_fullname, user_email, login_id, password, secret_question,secret_answer) VALUES ('" + user.getUserFullName() + "','" + user.getUserEmailAddress() + "','" + user.getLoginID() + "','" + user.getPassword() + "','" + user.getSecretQuestion() + "','" + user.getSecretAnswer() + "')");
            dbConnection.closeConnection();
        }
        catch (SQLException ex) {
            Logger.getLogger(LoginData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void updateUser(User user) {
        try {
            dbConnection = new DBConnection();
            dbConnection.update("UPDATE user_data SET user_fullname = '" + user.getUserFullName() + "',user_email = '" + user.getUserEmailAddress() + "',login_id = '" + user.getLoginID() + "',password = '" + user.getPassword() + "',secret_question = '" + user.getSecretQuestion() + "',secret_answer = '" + user.getPassword() + "' WHERE id = '" + user.getUserID() + "'");
            dbConnection.closeConnection();
        }
        catch (SQLException ex) {
            Logger.getLogger(LoginData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            dbConnection = new DBConnection();
            dbConnection.update("Delete FROM user_data WHERE id = '" + user.getUserID() + "'");
            dbConnection.closeConnection();
        } catch(SQLException ex) {
            Logger.getLogger(LoginData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean duplicateUser(User user){
        try {
            dbConnection = new DBConnection();
            ResultSet result = dbConnection.retrieve("SELECT COUNT(*) from user_data WHERE login_id='" + user.getLoginID() + "' OR user_email='" + user.getUserEmailAddress() + "' ");
            int count = result.getInt(1);
            dbConnection.closeConnection();
            if (count >= 1){
                return false;
            }

        } catch(SQLException ex) {
            Logger.getLogger(LoginData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean validateLogin(User user){
        try {
            dbConnection = new DBConnection();
            ResultSet result = dbConnection.retrieve("SELECT COUNT(*) from user_data WHERE login_id='" + user.getLoginID() + "' AND password='" + user.getPassword() + "' ");
            int count = result.getInt(1);
            dbConnection.closeConnection();
            if (count == 0){
                return false;
            }

        } catch(SQLException ex) {
            Logger.getLogger(LoginData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }


}
