package com.aor.beastie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import java.util.List;

public class NPC extends Element{
    String typeNPC;

    public NPC(int x, int y) {
        super(x, y);
        typeNPC = "Not defined yet";
    }

    public String getTypeNPC() {
        return typeNPC;
    }

    public void setTypeNPC(String typeNPC) {
        this.typeNPC = typeNPC;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#E1C699")); graphics.setForegroundColor(TextColor.Factory.fromString("#000000")); graphics.enableModifiers(SGR.BOLD);
        if (typeNPC == "Blacksmith") graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "1");
        else if (typeNPC == "Butcher") graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "2");
        else if (typeNPC == "Barn") graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "3");
        else if (typeNPC == "Builder") graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "4");
        else if (typeNPC == "Seller") graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "5");
    }

    public void talk(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF"));graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));graphics.enableModifiers(SGR.BOLD);String speak1 = "", speak2 = "";
        if (typeNPC == "Butcher") {speak1 = "Hi! Welcome to the best butcher in the world!";speak2 = "How can I help you? Do you need to buy something?";Music.soundEffect("src/main/resources/npcHello3.wav");}
        else if (typeNPC == "Blacksmith") {speak1 = "Hello, there! I have the best tools around...";speak2 = "Do you want to buy some?";Music.soundEffect("src/main/resources/npcHello2.wav");}
        else if (typeNPC == "Barn") {speak1 = "Howtie mate!";speak2 = "Do you want some food?";Music.soundEffect("src/main/resources/npcHello1.wav");}
        else if (typeNPC == "Builder") {speak1 = "What's up, boss?";speak2 = "What would you like to build?";Music.soundEffect("src/main/resources/npcHello5.wav");}
        else if (typeNPC == "Seller") {speak1 = "Oh! Hi, buddy!";speak2 = "Looking for something to buy?";Music.soundEffect("src/main/resources/npcHello4.wav");}
        String speak3 = "Yes(Y)   No(N)"; graphics.putString(new TerminalPosition(0, 31), speak1); graphics.putString(new TerminalPosition(0, 32), speak2); graphics.putString(new TerminalPosition(0, 33), speak3);
    }

}