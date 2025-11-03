package com.example.demo.model;

public class Subject {
    private String code;
    private String name;
    private int mse;
    private int ese;

    public Subject() {
    }

    public Subject(String code, String name, int mse, int ese) {
        this.code = code;
        this.name = name;
        this.mse = mse;
        this.ese = ese;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMse() {
        return mse;
    }

    public void setMse(int mse) {
        this.mse = mse;
    }

    public int getEse() {
        return ese;
    }

    public void setEse(int ese) {
        this.ese = ese;
    }

    public double getTotal() {
        return mse + ese;
    }

    public String getGrade() {
        double total = getTotal();
        if (total >= 90) return "A+";
        if (total >= 80) return "A";
        if (total >= 70) return "B";
        if (total >= 60) return "C";
        if (total >= 50) return "D";
        return "F";
    }
}