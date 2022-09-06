package com.aniket.ayush;

import java.io.Serializable;

public class Hospital implements Serializable {

    private String name, id, address, documentId, o_time, c_time;
    private String reg_number, url, specialities, email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getO_time() {
        return o_time;
    }

    public void setO_time(String o_time) {
        this.o_time = o_time;
    }

    public String getC_time() {
        return c_time;
    }

    public void setC_time(String c_time) {
        this.c_time = c_time;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    private String zip_code;
    String ph_numbers;

    Hospital(){}

    public String getAddress() {
        return address;
    }

    public void setPh_numbers(String ph_numbers) {
        this.ph_numbers = ph_numbers;
    }

    public void setAddress(String address) {
        this.address = address;
    }



//    public void setCity(String city) {
//        this.city = city;
//    }

    public String getReg_number() {
        return reg_number;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
    }

    public String getPh_numbers() {
        return ph_numbers;
    }


    Hospital(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
