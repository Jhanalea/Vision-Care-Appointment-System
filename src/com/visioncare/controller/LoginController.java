package com.visioncare.controller;
import com.visioncare.DAO.LoginData;
import com.visioncare.model.Patient;
import com.visioncare.model.SecretQuestions;
import com.visioncare.model.User;
import com.visioncare.service.LoginService;

import com.visioncare.service.PatientService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;


import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;


import javafx.scene.control.*;
import javafx.scene.control.TextInputControl.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import animatefx.animation.*;


public class LoginController implements Initializable {
    LoginService loginService;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btn_create_user;
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_register_new;
    @FXML
    private Button btn_reset_pw;
    @FXML
    private ImageView btn_back;
    @FXML
    private ImageView btn_back_reset;
    @FXML
    private ComboBox<SecretQuestions> cbox_secret_ques;
    @FXML
    private ComboBox<SecretQuestions> cbox_secret_ques_reset;
    @FXML
    private Label label_forgot_pw_login;
    @FXML
    private Pane pane_signin;
    @FXML
    private Pane pane_signup;
    @FXML
    private Pane pane_forgot_pw;
    @FXML
    private PasswordField pf_password_login;
    @FXML
    private PasswordField pf_password_reg;
    @FXML
    private PasswordField pf_new_password_reset;
    @FXML
    private TextField tf_email_reg;
    @FXML
    private TextField tf_fullname_reg;
    @FXML
    private TextField tf_secret_ans;
    @FXML
    private TextField tf_secret_ans_reset;
    @FXML
    private TextField tf_username_login;
    @FXML
    private TextField tf_username_reg;
    @FXML
    private TextField tf_username_reset;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbox_secret_ques.getItems().addAll(SecretQuestions.values());
        cbox_secret_ques_reset.getItems().addAll(SecretQuestions.values());
        // TO DO
    }

    public void createUser() throws SQLException {
        if (validateInputs() && duplicateUser()) {
            User user = new User();
            user.setUserFullName(this.tf_fullname_reg.getText());
            user.setUserEmailAddress(this.tf_email_reg.getText());
            user.setLoginID(this.tf_username_reg.getText());
            user.setPassword(this.pf_password_reg.getText());
            user.setSecretQuestion(this.cbox_secret_ques.getValue().SecretQuestions());
            user.setSecretAnswer(this.tf_secret_ans.getText());
            loginService = new LoginService();
            loginService.createUser(user);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New User Registered");
            alert.setHeaderText("User Account Successfully Created!");
            alert.setContentText("New User: "+ tf_username_reg.getText() + " has been created.");
            alert.showAndWait();
        }
    }

    public boolean validateInputs(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        String emailRegex = "^(.+)@(\\S+)$";
        String nameRegex = "^[a-zA-Z ,.'-]+$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Pattern namePattern = Pattern.compile(nameRegex);

        //Checking Empty Fields
        if (tf_fullname_reg.getText().isEmpty() || tf_email_reg.getText().isEmpty() || pf_password_reg.getText().isEmpty() || tf_secret_ans.getText().isEmpty() ) {
            alert.setTitle("Empty Fields");
            alert.setHeaderText("Fields Cannot Be Empty");
            alert.setContentText("Please ensure all fields are filled out.");
            alert.showAndWait();
            return false;

        } else if(!namePattern.matcher(tf_fullname_reg.getText()).matches()){
            alert.setTitle("Invalid Name");
            alert.setHeaderText("Invalid Characters in User's Name");
            alert.setContentText("Please ensure no invalid characters are in the user's name.");
            alert.showAndWait();
            return false;

        } else if(!emailPattern.matcher(tf_email_reg.getText()).matches()) {
            alert.setTitle("Invalid Email Address");
            alert.setHeaderText("Invalid Characters or Email Format");
            alert.setContentText("Please ensure user's email address is formatted correctly");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public boolean duplicateUser(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        User user = new User();
        user.setUserEmailAddress(this.tf_email_reg.getText());
        user.setLoginID(this.tf_username_reg.getText());
        loginService = new LoginService();
        if (loginService.duplicateUser(user)){
            return true;
        }
        alert.setTitle("User Already Exists");
        alert.setHeaderText("This user has already been created");
        alert.setContentText("Please ensure username and email address are unique");
        alert.showAndWait();
        return false;
    }

    public boolean validateLogin(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        User user = new User();
        user.setLoginID(this.tf_username_login.getText());
        user.setPassword(this.pf_password_login.getText());
        loginService = new LoginService();
        if (loginService.validateLogin(user)){
            return true;
        }
        alert.setTitle("Invalid Credentials");
        alert.setHeaderText("Invalid Login Credentials");
        alert.setContentText("Please ensure username and password are entered correctly");
        alert.showAndWait();
        return false;
    }




    @FXML
    void handleButtonAction(ActionEvent event) throws IOException, SQLException {
        if(event.getSource().equals(btn_register_new)){
            new ZoomIn(pane_signup).play();
            pane_signup.toFront();
        } else if(event.getSource().equals(btn_login)) {
            if(validateLogin()){
                Parent root = FXMLLoader.load(getClass().getResource("../view/AppFrame.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } else if(event.getSource().equals(btn_create_user)){
            createUser();
        }

    }

    @FXML
    void handleMouseEvent(MouseEvent event){
        if (event.getSource().equals(btn_back)){
            new ZoomIn(pane_signin).play();
            pane_signin.toFront();
        } else if (event.getSource().equals(btn_back_reset)){
            new ZoomIn(pane_signin).play();
            pane_signin.toFront();
        } else if (event.getSource().equals(label_forgot_pw_login)) {
            new ZoomIn(pane_forgot_pw).play();
            pane_forgot_pw.toFront();
        }

    }
}
