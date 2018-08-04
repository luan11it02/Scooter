package com.app.lfc.scooter.Model;

public class User {
    private String uid;
    private String hoten;
    private String emailsdt;

    public User(){

    }

    public User(String uid, String hoten, String emailsdt) {
        this.uid = uid;
        this.hoten = hoten;
        this.emailsdt = emailsdt;
    }

    public String getEmailsdt() {
        return emailsdt;
    }

    public void setEmailsdt(String emailsdt) {
        this.emailsdt = emailsdt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
}
