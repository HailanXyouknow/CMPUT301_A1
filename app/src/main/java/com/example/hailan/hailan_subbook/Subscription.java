package com.example.hailan.hailan_subbook;

/**
 * Created by Hailan on 2018-02-02.
 */

public class Subscription {
    private String name;
    private String date;
    private String monthlyCharge;
    private String comment;

    public Subscription(String name, String date, String monthlyCharge, String comment) {
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

    public String getMonthlyCharge() {
        return monthlyCharge;
    }

    public void setMonthlyCharge(String monthlyCharge) {
        this.monthlyCharge = monthlyCharge;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Subscription: " + name + '\n' +
                "Date: " + date + '\n' +
                "Monthly Charge: " + monthlyCharge + '\n' +
                "Comment: " + comment + '\n';
    }
}
