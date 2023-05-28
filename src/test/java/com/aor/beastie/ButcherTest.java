package com.aor.beastie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ButcherTest {
    Player p1 = new Player(0,0);
    Butcher b1 = new Butcher(30,30,p1);
    @Test
    public void butcherConstructor() {
        Assertions.assertEquals(30, b1.getWidth());
        Assertions.assertEquals(30, b1.getHeight());
        Assertions.assertNotEquals(20, b1.getHeight());
        Assertions.assertNotEquals(20,b1.getWidth());
    }

}