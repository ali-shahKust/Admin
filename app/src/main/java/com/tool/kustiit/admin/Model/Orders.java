package com.tool.kustiit.admin.Model;

public class Orders {

    private String user_name , phone , city , country , adress , time, date , total_ammount , phone_number;

    public Orders(String user_name, String phone, String city, String country, String adress, String time, String date, String total_ammount, String phone_number) {
        this.user_name = user_name;
        this.phone = phone;
        this.city = city;
        this.country = country;
        this.adress = adress;
        this.time = time;
        this.date = date;
        this.total_ammount = total_ammount;
        this.phone_number = phone_number;
    }

    public Orders() {

    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal_ammount() {
        return total_ammount;
    }

    public void setTotal_ammount(String total_ammount) {
        this.total_ammount = total_ammount;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
