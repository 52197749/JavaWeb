package com.yjl.entity;

import java.io.Serializable;


public class PetOwner implements Serializable{


    private static final long serialVersionUID = -9075071328879492144L;
    private Integer id;
    private String name;
    private String password;
    private double money;

    public PetOwner() {
    }

    public PetOwner(Integer id, String name, String password, double money) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.money = money;
    }

    @Override
    public String toString() {
        return "PetOwner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", money=" + money +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        //System.out.println(111111111);
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
