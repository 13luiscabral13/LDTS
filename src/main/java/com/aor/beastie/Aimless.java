package com.aor.beastie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Aimless extends Monster{

    public Aimless(int x, int y) {
        super(x, y);
        setSymbol('m');
    }

    @Override
    public Position moveMonster(Map map) {
        Random random = new Random();
        while(true){
            Position ret = new Position(getPosition().getX() + random.nextInt(3) - 1, getPosition().getY() + random.nextInt(3) - 1);
            if(ret.getX() > 0 && ret.getX() < map.getWidth()-1 &&
                    ret.getY() > 3 && ret.getY() < map.getHeight()-1)
                return ret;
        }
    }

    public Position moveAimless(Mines mines) {
        Random random = new Random();
        while(true){
            Position ret = new Position(getPosition().getX() + random.nextInt(3) - 1, getPosition().getY() + random.nextInt(3) - 1);
            if(ret.getX() > 0 && ret.getX() < mines.getWidth()-1 &&
                    ret.getY() > 3 && ret.getY() < mines.getHeight()-1)
                return ret;
        }
    }

    public void draw(TextGraphics graphics){
        String simbolo = "m";
        draw(graphics, simbolo);
    }

}