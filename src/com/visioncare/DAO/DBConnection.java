package com.visioncare.DAO;

import java.sql.*;


public class DBConnection {
    private static final String CONN = "jdbc:sqlite:vcc-database.db";

    Connection connect = null;
    Statement statement = null;
    ResultSet rs = null;

    public DBConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection(CONN);
            statement = connect.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS patients (patient_id integer PRIMARY KEY AUTOINCREMENT, first_name text NOT NULL, last_name text NOT NULL, tel_num text NOT NULL, email_address text NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS appointments(appt_id integer PRIMARY KEY AUTOINCREMENT, appt_date text NOT NULL, appt_time text NOT NULL, appt_status text NOT NULL,patient_id integer, FOREIGN KEY(patient_id) REFERENCES patients(patient_id))");
        }
        catch(ClassNotFoundException CNFE) {
            CNFE.printStackTrace();
        }
    }

    public void update(String query) {
        try {
            statement.executeUpdate(query);
        }
        catch (SQLException SQLE) {
            SQLE.printStackTrace();
        }
    }

    public  ResultSet retrieve(String query) {
        try {
            rs = statement.executeQuery(query);
        } catch (SQLException SQLE) {
            SQLE.printStackTrace();
        }

        return rs;
    }

    public void closeConnection() {
        try {
            statement.close();
            connect.close();
        } catch (SQLException SQLE) {
            SQLE.printStackTrace();
        }
    }


}
