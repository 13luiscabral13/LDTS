package com.aor.beastie;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

public class Barn extends House{
    public Barn(int width, int height, Player player) {
        super(width, height, player);
    }

    @Override
    public void drawThings(TextGraphics graphics) throws IOException, InterruptedException {
        drawProducts(getProductScreen().newTextGraphics());
    }
}