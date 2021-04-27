package com.els.myapplication.bean;

import java.util.Date;
import java.util.List;

public class Project {
    private int uid;
    private String name;
    private String management;
    private String employee;
    private String equipment;
    private String startdate;
    private String enddate;
    private String address;
    private String longitude;
    private String latitude;
    private String financial;


    public Project(int uid, String name, String management, String employee, String equipment, String startdate, String enddate,String address ,String longitude,String latitude,String financial) {
        this.uid = uid;
        this.name = name;
        this.management = management;
        this.employee = employee;
        this.equipment = equipment;
        this.startdate = startdate;
        this.enddate = enddate;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.financial = financial;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManagement() {
        return management;
    }

    public void setManagement(String management) {
        this.management = management;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getFinancial() {
        return financial;
    }

    public void setFinancial(String financial) {
        this.financial = financial;
    }
}
