package com.aor.beastie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlacksmithTest {
    @Test
    public void blacksmithConstructor() {
        Blacksmith b1 = new Blacksmith(30, 15, new Player(0,0));
        Assertions.assertEquals(30, b1.getWidth());
        Assertions.assertEquals(15, b1.getHeight());
    }

}