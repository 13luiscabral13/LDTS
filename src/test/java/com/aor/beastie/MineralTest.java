package com.aor.beastie;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class MineralTest {
    @Test
    public void getName() {
        Mineral m1 = new Mineral(12, 14);
        String q = m1.getName();
        Assert.assertEquals(m1.getName(), q);
    }
    @Test
    public void setName() {
        Mineral m1 = new Mineral(13,15);
        m1.setName("Gold");
        String q = m1.getName();
        Assert.assertEquals("Gold", q);
    }
    @Test
    public void mineralDraw() {
        TextGraphics board = Mockito.mock(TextGraphics.class);
        Mineral m1 = new Mineral(10,12);
        m1.draw(board);
        Mockito.verify(board, Mockito.times(1)).enableModifiers(SGR.BOLD);
    }
}