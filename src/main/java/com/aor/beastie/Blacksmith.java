package com.aor.beastie;

import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public class Blacksmith extends House {
    public Blacksmith(int width, int height, Player player) {
        super(width, height, player);
    }

    @Override
    public void drawThings(TextGraphics graphics) throws IOException, InterruptedException {
        drawProducts(getProductScreen().newTextGraphics());
    }
}