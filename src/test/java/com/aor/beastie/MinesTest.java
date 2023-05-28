package com.aor.beastie;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MinesTest {
    @Test
    public void minesConstructor() {
        Mines m1 = new Mines(20, 30, new Player(2, 2));
        Assertions.assertEquals(30, m1.getHeight());
        Assertions.assertEquals(20, m1.getWidth());
    }
    @Test
    public void retrieveTester() {
        Mines m1 = new Mines(30,30,new Player(3,3));
        m1.createMinerals();
        m1.isPositionAvailable(new Position(0,0));
    }
    @Test
    public void mineDrawer() {
        Mines m1 = new Mines(30,30,new Player(3,3));
        TextGraphics board = Mockito.mock(TextGraphics.class);
        m1.draw(board);
        Mockito.verify(board,Mockito.times(1)).setBackgroundColor(TextColor.Factory.fromString("#964B00"));

    }
}