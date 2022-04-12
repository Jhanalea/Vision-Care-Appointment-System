package com.visioncare.DAO;
import com.visioncare.model.User;

public interface LoginDAOInterface {
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(User user);

}
