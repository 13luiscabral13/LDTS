package com.aor.beastie;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {
    @Test
    public void constructorTest() {
        Tree t1 = new Tree(10,10);
        Assertions.assertEquals(20,t1.getDamage());
    }
    @Test
    public void damageDecreaserTest() {
        Tree t1 = new Tree(12,13);
        t1.decreaseDamage(5);
        Assertions.assertEquals(15,t1.getDamage());
    }
    @Test
    public void drawTester() {
        TextGraphics board = Mockito.mock(TextGraphics.class);
        Tree t1 = new Tree(14,15);
        t1.draw(board);
        Mockito.verify(board, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#000000"));
    }

    @Test
    public void symbolTester() {
        Tree t1 = new Tree(4,4);
        t1.setSymbol('l');
        Assertions.assertEquals('l', t1.getSymbol());
    }
}