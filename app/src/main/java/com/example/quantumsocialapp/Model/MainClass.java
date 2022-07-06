package com.example.quantumsocialapp.Model;

import java.util.ArrayList;

public class MainClass {

    private String status;
    private String totalData;
    private ArrayList<ModelClass> articles;

    public MainClass(String status, String totalData, ArrayList<ModelClass> articles) {
        this.status = status;
        this.totalData = totalData;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalData() {
        return totalData;
    }

    public void setTotalData(String totalData) {
        this.totalData = totalData;
    }

    public ArrayList<ModelClass> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<ModelClass> articles) {
        this.articles = articles;
    }
}
