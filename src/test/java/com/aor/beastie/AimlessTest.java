package com.aor.beastie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.w3c.dom.Text;

import static org.junit.jupiter.api.Assertions.*;

class AimlessTest {
    @Test
    public void drawTester() {
        TextGraphics board = Mockito.mock(TextGraphics.class);
        Aimless a1 = new Aimless(4,4);
        a1.draw(board);
        Mockito.verify(board, Mockito.times(1)).enableModifiers(SGR.BOLD);
    }

}