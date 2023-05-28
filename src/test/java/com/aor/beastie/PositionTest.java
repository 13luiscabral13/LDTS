package com.aor.beastie;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    @Test
    public void getX() {
        Position p1 = new Position(3, 0);
        int q = p1.getX();
        Assert.assertEquals(3, q);
    }
    @Test
    public void getY() {
        Position p1 = new Position(0, 2);
        int q = p1.getY();
        Assert.assertEquals(2, q);
    }
    @Test
    public void setY() {
        Position p1 = new Position(0, 2);
        p1.setY(3);
        int q = p1.getY();
        Assert.assertEquals(3, q);
    }
    @Test
    public void setX() {
        Position p1 = new Position(0, 2);
        p1.setX(3);
        int q = p1.getX();
        Assert.assertEquals(3, q);
    }
    @Test
    public void equals() {
        Position p1 = new Position(0,4);
        Position p2 = new Position(0,4);
        Assert.assertEquals(true, p1.equals(p2));
    }
}