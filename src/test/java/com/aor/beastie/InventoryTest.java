package com.aor.beastie;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    public void productTester() {
        Product p1 = new Product("Tool", "Sword", 20, 20);
        Assertions.assertEquals(20, p1.getPrice());
    }

    @Test
    public void damageTester() {
        Product p1 = new Product("Tool", "Sword", 20, 20);
        Assertions.assertEquals(20, p1.getValue());
    }

    @Test
    public void productAdderAndRemover() {
        Inventory i1 = new Inventory();
        i1.addProduct("Pickaxe");
        i1.removeProduct("Pickaxe", 1);
        Assertions.assertEquals(0, i1.getProductQuantity("Pickaxe"));
    }

    @Test
    public void toolGetter() {
        Inventory i1 = new Inventory();
        Product p1 = new Product("Tools", "Pickaxe", 15);
        Product p2 = new Product("Tools", "Sword", 20);
        i1.addProduct(p1);
        i1.addProduct(p2);
        Assertions.assertEquals(true, p1.equals(i1.getTools().get(0)));
        Assertions.assertEquals(true, p2.equals(i1.getTools().get(1)));
    }
    @Test
    public void getProductsByType() {
        Inventory i1 = new Inventory();
        Product p1 = new Product("Tools", "Pickaxe", 15);
        i1.addProduct(p1);i1.addProduct(p1);i1.addProduct(p1);
        List<Product>l1 = List.of(p1,p1,p1);
        Assert.assertEquals(l1.get(0),i1.getProductsByType("Tools").get(0));
    }
}