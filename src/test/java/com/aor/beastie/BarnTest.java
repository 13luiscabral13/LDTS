package com.aor.beastie;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class BarnTest {
    @Test
    public void barnConstructor() {
        Barn b1 = new Barn(20, 10, new Player(0,0));
        Assertions.assertEquals(10,b1.getHeight());
        Assertions.assertEquals(20, b1.getWidth());
    }
    @Test
    public void setNameFinder() {
        Barn b1 = new Barn(20,10, new Player(0,0));
        b1.setName("BarnTwo");
        Assertions.assertEquals("BarnTwo", b1.getName());
    }
    @Test
    public void buyTester() {
        Player p1 = new Player(0,0);
        p1.increaseBalance(15);
        Barn b1 = new Barn(20,10, p1);
        int a = p1.getInventory().getProductQuantity("Potato");
        b1.buyProduct("Potato", 1, 2);
        Assertions.assertEquals(13, p1.getBalance());
        Assertions.assertEquals(a+1, p1.getInventory().getProductQuantity("Potato"));
    }
    @Test
    public void getNPCTester() {
        Player p1 = new Player(0,0);
        Barn b1 = new Barn(20,20,p1);
        NPC n1 = new NPC(0,0);
        n1.setTypeNPC("Farmer");
        b1.getNpc().setTypeNPC("Farmer");
        Assertions.assertEquals("Farmer", b1.getNpc().getTypeNPC());
    }
    @Test
    public void getNamesAndSetNames() {
        Player p1 = new Player(0,0);
        Barn b1 = new Barn(20,20,p1);
        b1.setName("BarnTwo");
        Assert.assertEquals("BarnTwo",b1.getName());
    }
}