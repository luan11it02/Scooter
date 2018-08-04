package com.app.lfc.scooter.Model;

public class YeuCau {
    private int id;
    private String name;
    private String emailsdt;
    private String content;
    private boolean duyet;
    private String date;
    public YeuCau(){

    }

    public YeuCau(int id, String name, String emailsdt, String content, boolean duyet, String date) {
        this.id = id;
        this.name = name;
        this.emailsdt = emailsdt;
        this.content = content;
        this.duyet = duyet;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDuyet() {
        return duyet;
    }

    public void setDuyet(boolean duyet) {
        this.duyet = duyet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailsdt() {
        return emailsdt;
    }

    public void setEmailsdt(String emailsdt) {
        this.emailsdt = emailsdt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
