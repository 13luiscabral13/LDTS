package com.aor.beastie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Tree extends Element{
    private int damage;
    private char symbol;
    private int counter;

    public Tree(int x, int y){
        super(x, y);
        this.damage = 20;
        this.symbol = '*';
        counter = 0;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), String.valueOf(symbol));
    }

    public int getDamage(){
        return damage;
    }

    public void decreaseDamage(int quantity){
        damage = damage - quantity;
    }

    public int getCounter(){
        return counter;
    }

    public void setCounter(int counter){
        this.counter = counter;
    }
}