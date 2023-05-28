package com.aor.beastie;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BuilderTest {
    Player p1 = new Player(0,0);
    Builder b1 = new Builder(0,0,p1);
    @Test
    public void buyTest() {
        p1.getInventory().addProduct("Wood");
        p1.getInventory().addProduct("Wood");
        p1.getInventory().addProduct("Wood");
        p1.getInventory().addProduct("Iron");
        p1.getInventory().addProduct("Iron");
        b1.buy("Axe", 1);
        Assertions.assertEquals(1,b1.getInventory().getProductQuantity("Axe"));
    }

    @Test
    public void buyTesterTwo() {
        p1.getInventory().addProduct("Potato");
        p1.getInventory().addProduct("Potato");
        p1.getInventory().addProduct("Potato");
        p1.getInventory().addProduct("Carrot");
        p1.getInventory().addProduct("Carrot");
        p1.getInventory().addProduct("Carrot");
        b1.buy("Soup", 1);
        Assertions.assertEquals(1,b1.getInventory().getProductQuantity("Soup"));
    }
    @Test
    public void buyTesterThree() {
        p1.getInventory().addProduct("Wood");
        p1.getInventory().addProduct("Wood");
        p1.getInventory().addProduct("Wood");
        p1.getInventory().addProduct("Diamond");
        p1.getInventory().addProduct("Diamond");
        p1.getInventory().addProduct("Diamond");
        b1.buy("Sword", 1);
        Assertions.assertEquals(3,b1.getInventory().getProductQuantity("Sword"));
    }
    @Test
    public void buyTesterFour() {
        p1.getInventory().addProduct("Wood");
        p1.getInventory().addProduct("Wood");
        p1.getInventory().addProduct("Wood");
        p1.getInventory().addProduct("Iron");
        b1.buy("Pickaxe", 1);
        Assertions.assertEquals(1,b1.getInventory().getProductQuantity("Pickaxe"));
    }

}