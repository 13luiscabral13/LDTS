package com.aor.beastie;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.w3c.dom.Text;

import static org.junit.jupiter.api.Assertions.*;

class CoinTest {
    @Test
    public void coinDraw() {
        TextGraphics board = Mockito.mock(TextGraphics.class);
        Coin c1 = new Coin(2,1);
        c1.draw(board);
        Mockito.verify(board, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#fce353"));
    }
}