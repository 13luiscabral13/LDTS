package com.aor.beastie;

public class Product {
    private String type;
    private String name;
    private int quantity;
    private int price;
    private int value;

    public Product(String type, String name, int price){
        this.type=type;
        this.name = name;
        this.quantity = 0;
        this.price = price;
    }

    public Product(String type, String name, int price, int value){
        this.type=type;
        this.name = name;
        this.quantity = 0;
        this.price = price;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }

    public int getValue(){
        return value;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null) return false;

        Product p = (Product) o;
        return type==p.getType() && name==p.getName();
    }
}