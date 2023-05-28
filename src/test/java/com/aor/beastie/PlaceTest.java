package com.aor.beastie;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

class PlaceTest {
    @Test
    public void getWidth() {
        Place p1 = new Place(20,20, new Player(0,0));
        int q = p1.getWidth();
        Assert.assertEquals(20,q);
    }
    @Test
    public void getHeight() {
        Place p1 = new Place(20,30, new Player(0,0));
        int q  = p1.getHeight();
        Assert.assertEquals(30,q);
    }
    @Test
    public void setWidth() {
        Place p1 = new Place(20,20, new Player(0,0));
        p1.setWidth(30);
        int q = p1.getWidth();
        Assert.assertEquals(30, q);
    }
    @Test
    public void setHeight() {
        Place p1 = new Place(20,20, new Player(0,0));
        p1.setHeight(40);
        int q = p1.getHeight();
        Assert.assertEquals(40,q);
    }
    @Test
    public void getPlayer() {
        Player p2 = new Player(20,21);
        Place p1 = new Place(20,20, p2);
        Player a = p1.getPlayer();
        Assert.assertEquals(p1.getPlayer(),a);
    }
    @Test
    public void setPlayer() {
        Player p2 = new Player(20,21);
        Player p3 = new Player(13,14);
        Place p1 = new Place(10,20, p2);
        p1.setPlayer(p3);
        Player q = p1.getPlayer();
        Assert.assertEquals(p1.getPlayer(), q);
    }
    @Test
    public void canMove() {
        Player p1 = new Player(4,4);
        Position pos1 = p1.getPosition();
        Place pl1 =  new Place(40,40,p1);
        Assert.assertEquals(true, pl1.canPlayerMove(p1.getPosition()));
    }
    @Test
    public void movePlayerTest() {
        Player p1 = new Player(3,3);
        Position pos2 = new Position(5,6);
        Place pl1 = new Place(40,40, p1);
        pl1.movePlayer(pos2);
        Assert.assertEquals(pos2, p1.getPosition());
    }
    @Test
    public void inventoryTest() {
        Player p1 = new Player(0,0);
        Inventory i1 = new Inventory();
        Assert.assertEquals(i1.getProductsByQuantity(),p1.getInventory().getProductsByQuantity());
    }
    @Test
    public void collideTest() {
        Player p1 = new Player(4, 4);
        Monster m1 = new Stalker(3, 4);
        m1.setDamage(15);
        Assertions.assertEquals(15,m1.getDamage());
        Place pl1 = new Place(30,30,p1);
        Assertions.assertEquals(true, pl1.collision(m1));
    }
    @Test
    public void processKeyTester() {
        KeyStroke k1 = Mockito.mock(KeyStroke.class);
        Mockito.when(k1.getKeyType()).thenReturn(KeyType.ArrowDown);
        KeyStroke k2 = Mockito.mock(KeyStroke.class);
        Mockito.when(k2.getKeyType()).thenReturn(KeyType.ArrowUp);
        KeyStroke k3 = Mockito.mock(KeyStroke.class);
        Mockito.when(k3.getKeyType()).thenReturn(KeyType.ArrowLeft);
        KeyStroke k4 = Mockito.mock(KeyStroke.class);
        Mockito.when(k4.getKeyType()).thenReturn(KeyType.ArrowRight);
    }
}