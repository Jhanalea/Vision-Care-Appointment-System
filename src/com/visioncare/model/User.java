package com.visioncare.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private final IntegerProperty userID = new SimpleIntegerProperty();
    private final StringProperty userFullName = new SimpleStringProperty();
    private final StringProperty userEmailAddress = new SimpleStringProperty();
    private final StringProperty loginID = new SimpleStringProperty();
    private final StringProperty secretQuestion= new SimpleStringProperty();
    private final StringProperty secretAnswer= new SimpleStringProperty();

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    private final StringProperty password = new SimpleStringProperty();

    public int getUserID() {
        return userID.get();
    }

    public IntegerProperty userIDProperty() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID.set(userID);
    }

    public String getUserFullName() {
        return userFullName.get();
    }

    public StringProperty userFullNameProperty() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName.set(userFullName);
    }

    public String getUserEmailAddress() {
        return userEmailAddress.get();
    }

    public StringProperty userEmailAddressProperty() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress.set(userEmailAddress);
    }

    public String getLoginID() {
        return loginID.get();
    }

    public StringProperty loginIDProperty() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID.set(loginID);
    }

    public String getSecretQuestion() {
        return secretQuestion.get();
    }

    public StringProperty secretQuestionProperty() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion.set(secretQuestion);
    }

    public String getSecretAnswer() {
        return secretAnswer.get();
    }

    public StringProperty secretAnswerProperty() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer.set(secretAnswer);
    }


}
