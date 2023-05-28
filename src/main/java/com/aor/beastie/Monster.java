package com.aor.beastie;

//Every time you process a key, all monsters move one position. Do this inside a new method called moveMonsters() .
//Every time the Hero touches a com.aor.beastie.Monster the game should terminate and a
// message should be printed to the console. Do this inside a method called verifyMonsterCollisions()

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public abstract class Monster extends Element{
    private int damage;
    private char symbol;

    public Monster(int x, int y) {
        super(x, y);
        this.damage = 30;
    }

    public void draw(TextGraphics graphics, String simbolo) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#b653fc"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), simbolo);
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public int getDamage(){
        return this.damage;
    }

    public void setDamage(int damage){
        this.damage-=damage;
        if(this.damage<0)
            this.damage=0;
    }

    public abstract Position moveMonster(Map map);
}