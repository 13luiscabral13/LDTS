package com.aor.beastie;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.w3c.dom.Text;

import static org.junit.jupiter.api.Assertions.*;

class StalkerTest {
    @Test
    public void drawTester() {
        TextGraphics board = Mockito.mock(TextGraphics.class);
        Stalker s1 = new Stalker(4, 4);
        s1.draw(board);
        Mockito.verify(board, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#b653fc"));
    }
    @Test
    public void movingTester() {
        Monster m1 = new Stalker(4,4);
        Player p1 = new Player(0,4);
        Map map1 = new Map(30,30,p1);
        Position pos1 = m1.moveMonster(map1);
        Assertions.assertEquals(4,pos1.getX());
        Assertions.assertEquals(4,pos1.getY());
    }
}
