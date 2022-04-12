package com.visioncare.service;
import com.visioncare.model.User;

public interface LoginServiceInterface {
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(User user);

}
