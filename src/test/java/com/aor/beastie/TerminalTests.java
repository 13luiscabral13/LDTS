package com.aor.beastie;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import com.googlecode.lanterna.SGR;

class TerminalTests {

    Player p1 = new Player(0,0);
    Seller s1 = new Seller(30,30, p1);
    @Test
    public void sellDrawThings() throws IOException, InterruptedException {
        TextGraphics board = Mockito.mock(TextGraphics.class);
        s1.drawThings(board);
        Mockito.verify(board, Mockito.times(1)).enableModifiers(SGR.BOLD);
    }
    @Test
    public void drawTest() throws IOException {
        Player p1 = new Player(0,0);
        Builder b1 = new Builder(30,30,p1);
        TextGraphics board = Mockito.mock(TextGraphics.class);
        b1.drawThings(board);
        Mockito.verify(board, Mockito.times(1)).putString(0, 1, "             Choose the product you want to build");
    }
}

