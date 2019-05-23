package com.example.tarea_3_luis_esquivel.Models;

import java.io.Serializable;

public class CurrencyItem implements Serializable {

    //Attributtes
    private String key;
    private Double value;
    private Double basicValue;

    public CurrencyItem(String key, Double value, Double basicValue) {
        this.key = key;
        this.value = value;
        this.basicValue = basicValue;
    }

    public String getKey() {
        return key;
    }

    public Double getValue() {
        return value;
    }

    public Double getBasicValue() {
        return basicValue;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setBasicValue(Double basicValue) { this.basicValue = basicValue; }

    public void setValue(Double value) {
        this.value = value;
    }
}
