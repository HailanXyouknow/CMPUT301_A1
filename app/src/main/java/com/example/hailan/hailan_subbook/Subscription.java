package com.example.hailan.hailan_subbook;

/**
 * Created by Hailan on 2018-02-02.
 */

public class Subscription {
    private String name;
    private String date;
    private Double monthlyCharge;
    private String comment;

    public Subscription(String name, String date, Double monthlyCharge, String comment) {
        this.name = name;
        this.date = date;
        this.monthlyCharge = monthlyCharge;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getMonthlyCharge() {
        return monthlyCharge;
    }

    public void setMonthlyCharge(Double monthlyCharge) {
        this.monthlyCharge = monthlyCharge;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
