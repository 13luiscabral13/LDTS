package com.aor.beastie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class NPCTest {
    @Test
    public void getTypeNPC() {
        NPC p1 = new NPC(0,0);
        p1.setTypeNPC("Butcher");
        String q = p1.getTypeNPC();
        Assert.assertEquals("Butcher", q);
    }
    @Test
    public void drawNPC() {
        TextGraphics board = Mockito.mock(TextGraphics.class);
        NPC n1 = new NPC(0,0);
        n1.draw(board);
        Mockito.verify(board, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#000000"));
    }
    @Test
    public void talkNPC() {
        TextGraphics board = Mockito.mock(TextGraphics.class);
        NPC n1 = new NPC(1,1);
        n1.talk(board);
        Mockito.verify(board, Mockito.times(1)).enableModifiers(SGR.BOLD);
    }
}