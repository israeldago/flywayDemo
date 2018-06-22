package com.israeldago.flywayDemo.entities;

import com.opencsv.bean.CsvBindByName;

import javax.persistence.*;

@Entity
@Table(name = "bla")
public class CarBIS {
    @Id
    @CsvBindByName
    private Integer id;
    @CsvBindByName
    private String brand;
    @CsvBindByName
    private String color;
    @CsvBindByName @Lob
    private String custom;

    public CarBIS() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", custom='" + custom + '\'' +
                '}';
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getCustom() {
        return custom;
    }
}
