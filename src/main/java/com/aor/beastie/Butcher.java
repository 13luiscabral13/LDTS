package com.aor.beastie;

import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public class Butcher extends House{
    public Butcher(int width, int height, Player player) {
        super(width, height, player);
    }

    @Override
    public void drawThings(TextGraphics graphics) throws IOException, InterruptedException {
        drawProducts(getProductScreen().newTextGraphics());
    }
}