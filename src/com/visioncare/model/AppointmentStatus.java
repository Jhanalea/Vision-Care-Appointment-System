package com.visioncare.model;

public enum AppointmentStatus {
    Upcoming, Completed, Missed;

    private AppointmentStatus(){}

    public String value(){
        return name();
    }

    public static AppointmentStatus fromvalue(String v){
        return valueOf(v);
    }
}
