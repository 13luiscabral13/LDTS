package com.aor.beastie;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    public void getType() {
        Product p1 = new Product("Tool","Pickaxe", 25);
        String tipo = p1.getType();
        Assert.assertEquals("Tool", tipo);
        int price = p1.getPrice();
        Assert.assertEquals(25, price);
        Product p2 = new Product("Meat", "Beef", 15);
        String tipo2 = p2.getType();
        Assert.assertEquals("Meat", tipo2);
        int price2 = p2.getPrice();
        Assert.assertEquals(15, price2);
    }
    @Test
    public void setType() {
        Product p1 = new Product("Tool", "Pickaxe", 25);
        p1.setType("Meat");
        String tipo = p1.getType();
        Assert.assertEquals("Meat", tipo);
    }
    @Test
    public void getQuantity() {
        Product p1 = new Product("Tool", "Pickaxe", 25);
        int q = p1.getQuantity();
        Assert.assertEquals(0,q);
    }
    @Test
    public void setQuantity() {
        Product p1 = new Product("Tool", "Pickaxe", 25);
        p1.setQuantity(21);
        int q = p1.getQuantity();
        Assert.assertEquals(21,q);
    }
}