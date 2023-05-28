package com.aor.beastie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.w3c.dom.Text;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SellerTest {
    Player p1 = new Player(0,0);
    Seller s1 = new Seller(30,30, p1);
    @Test
    public void sellTester() throws IOException, InterruptedException {
        p1.getInventory().addProduct("Pickaxe");
        s1.sell("Pickaxe", 1, 25);
        Assertions.assertEquals(25,p1.getBalance());
    }
    @Test
    public void sellDrawer() {
        TextGraphics board = Mockito.mock(TextGraphics.class);
        s1.draw(board);
        Mockito.verify(board, Mockito.times(3)).enableModifiers(SGR.BOLD);
    }

}