package com.aor.beastie;

import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Stalker extends Monster{

    public Stalker(int x, int y) {
        super(x, y);
        setSymbol('M');
    }

    @Override
    public Position moveMonster(Map map){
        return getPosition();
    }

    public void draw(TextGraphics graphics){
        String simbolo = "M";
        draw(graphics, simbolo);
    }
}

