package com.aniket.ayush;

import java.io.Serializable;

public class Appointment implements Serializable {

    String patient_name, hospitalID, message,patient_number, patient_symptoms  ;

    Appointment(){};

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPatient_number() {
        return patient_number;
    }

    public void setPatient_number(String patient_number) {
        this.patient_number = patient_number;
    }

    public String getPatient_symptoms() {
        return patient_symptoms;
    }

    public void setPatient_symptoms(String patient_symptoms) {
        this.patient_symptoms = patient_symptoms;
    }
}
