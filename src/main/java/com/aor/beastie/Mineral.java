package com.aor.beastie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Mineral extends Element{
    private String name;

    public Mineral(int x, int y){
        super(x, y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void draw(TextGraphics graphics) {
        if(name == "Gold")
            graphics.setForegroundColor(TextColor.Factory.fromString("#fce353"));
        if(name == "Iron")
            graphics.setForegroundColor(TextColor.Factory.fromString("#808080"));
        if(name == "Diamond")
            graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "o");
    }
}