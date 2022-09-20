package com.example.lab6.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Wizard")
public class Wizard {
    @Id
    private String _id;
    private String sex;
    private String name;
    private String school;
    private String house;
    private int money;
    private String position;

    public Wizard() {}
    public Wizard(String _id, String sex, String name, String school, String house, int money, String position) {
        this._id = _id;
        this.sex = sex;
        this.name = name;
        this.school = school;
        this.house = house;
        this.money = money;
        this.position = position;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSex() {
        if (sex.equals("m")){
            return "Male";
        } else if (sex.equals("f")){
            return "Female";
        }
        return null;
    }

    public void setSex(String sex) {
        if (sex.equals("Male")){
            this.sex = "m";
        } else if (sex.equals("Female")) {
            this.sex = "f";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
