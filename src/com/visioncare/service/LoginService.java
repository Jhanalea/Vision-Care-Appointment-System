package com.visioncare.service;

import com.visioncare.DAO.LoginData;
import com.visioncare.DAO.PatientData;

import com.visioncare.model.Patient;
import com.visioncare.model.User;

public class LoginService implements LoginServiceInterface{
    LoginData loginData;

    @Override
    public void createUser(User user) {
        loginData = new LoginData();
        loginData.createUser(user);
    }

    @Override
    public void updateUser(User user) {
        loginData = new LoginData();
        loginData.updateUser(user);
    }

    @Override
    public void deleteUser(User user) {
        loginData = new LoginData();
        loginData.deleteUser(user);
    }

    public boolean duplicateUser(User user){
        loginData = new LoginData();
        if (loginData.duplicateUser(user)){
            return true;
        }
        return false;
    }

    public boolean validateLogin(User user){
        loginData = new LoginData();
        if (loginData.validateLogin(user)){
            return true;
        }
        return false;
    }


}
