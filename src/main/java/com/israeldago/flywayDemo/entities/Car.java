package com.israeldago.flywayDemo.entities;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String brand;
    private String color;
    @Transient
    private String customData;

    public Car() {}

    public Car(Integer id, String brand, String color) {
        this.id = id;
        this.brand = brand;
        this.color = color;
    }

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
                ", customData='" + customData + '\'' +
                '}';
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }

    public String getCustomData() {
        return customData;
    }
}
