package com.aor.beastie;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void moveUp() {
        Player pl1 = new Player(0,2);
        Position p1 = pl1.moveUp();
        int q = p1.getY();
        Assert.assertEquals(1, q);
    }
    @Test
    public void moveDown() {
        Player pl1 = new Player(0, 2);
        Position p1 = pl1.moveDown();
        int q = p1.getY();
        Assert.assertEquals(3, q);
    }
    @Test
    public void moveRight() {
        Player pl1 = new Player(0, 2);
        Position p1 = pl1.moveRight();
        int q = p1.getX();
        Assert.assertEquals(1, q);
    }
    @Test
    public void moveLeft() {
        Player pl1 = new Player(0, 2);
        Position p1 = pl1.moveLeft();
        int q = p1.getX();
        Assert.assertEquals(-1, q);
    }
    @Test
    public void playerDraw() {
        TextGraphics board = Mockito.mock(TextGraphics.class);
        Player p1 = new Player(0,0);
        p1.draw(board);
        Mockito.verify(board, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#871203"));
    }
    @Test
    public void lifeGetTester() {
        Player p1 = new Player(0,0);
        String tester = "|||";
        Assertions.assertEquals(tester,p1.getLife());
        String anotherTest = "||";
        p1.setLife(anotherTest);
        Assertions.assertEquals(anotherTest, p1.getLife());
        Assertions.assertNotEquals(tester, p1.getLife());
    }
    @Test
    public void energySetTester() {
        Player p1 = new Player(0,0);
        p1.setEnergy(40);
        Assertions.assertEquals(40, p1.getEnergy());
        p1.setEnergyCounter(100);
        Assertions.assertEquals(100,p1.getEnergyCounter());
        p1.increaseEnergy(20);
        Assertions.assertEquals(60, p1.getEnergy());
        p1.decreaseEnergy(40);
        Assertions.assertEquals(59, p1.getEnergy());
        p1.decreaseEnergy(20);
        Assertions.assertEquals(59, p1.getEnergy());
    }
    @Test
    public void balanceTester(){
        Player p1 = new Player(0,0);
        p1.increaseBalance(30);
        Assertions.assertEquals(30,p1.getBalance());
        p1.decreaseBalance(20);
        Assertions.assertEquals(10,p1.getBalance());
    }
    @Test
    public void lastPositionTester() {
        Position p1 = new Position(3,3);
        Player pl1 = new Player(5,5);
        pl1.setLastPosition(p1);
        Assertions.assertEquals(true,pl1.getLastPosition().equals(p1));
    }
    @Test
    public void viableNumberTester() {
        Player p1 = new Player(0,0);
        p1.setViableNumber(3);
        Assertions.assertEquals(3, p1.getViableNumber());
    }
}