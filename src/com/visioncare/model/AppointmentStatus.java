package com.visioncare.model;

public enum AppointmentStatus {
    Upcoming("Upcoming"), Completed("Completed"), Missed("Missed");

    private final String appt_status;

    AppointmentStatus(String appt_status) {
        this.appt_status = appt_status;
    }

    public String AppointmentStatus() { return appt_status; }

    @Override public String toString() { return appt_status; }
}
