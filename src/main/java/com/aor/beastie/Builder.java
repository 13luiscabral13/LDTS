package com.aor.beastie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;

public class Builder extends House{
    public Builder(int width, int height, Player player) {
        super(width, height, player);
    }


    public void drawThings(TextGraphics graphics) throws IOException {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF")); graphics.setForegroundColor(TextColor.Factory.fromString("#000000")); graphics.enableModifiers(SGR.BOLD);
        graphics.putString(0,0, "------------------------------------------------------------");
        graphics.putString(0, 1, "             Choose the product you want to build");
        graphics.putString(0, 2, "                    Select the product ID");
        graphics.putString(0,3, "------------------------------------------------------------");
        graphics.putString(0, 5," ID     NAME                COMBINATION               ");
        graphics.putString(1, 7, "1");
        graphics.putString(8, 7, "Soup                3xPotato + 3xCarrot");
        graphics.putString(1, 8, "2");
        graphics.putString(8, 8, "Axe                 3xWood   + 2xIron");
        graphics.putString(1, 9, "3");
        graphics.putString(8, 9, "Sword               3xWood   + 3xDiamond");
        graphics.putString(1, 10, "4");
        graphics.putString(8, 10, "Pickaxe             3xWood   + 1xIron");
        graphics.putString(new TerminalPosition(1, 17), "PRESS: X(exit)  I(inventory)  B(balance)");
        getProductScreen().refresh();
        KeyStroke key;
        while(true){
            graphics.putString(1, 18, "ID:                                                 "); graphics.putString(1, 19, "                                   "); getProductScreen().refresh();
            key = getProductScreen().readInput(); checkInventory(key);
            int num = getPlayer().checkKey(key);
            if(num == 0) break;
            if(num == -1 || num > 4) continue;
            graphics.putString(6, 18, String.valueOf(num)); graphics.putString(1, 19, "Quantity:                      "); getProductScreen().refresh();
            key = getProductScreen().readInput(); checkInventory(key); checkBalance(key);
            int quantity = getPlayer().checkKey(key);
            if(quantity == 0) break;
            if(quantity == -1) continue; String message = "You don't have enough materials to build this item.";
            if(num==1 && !(getInventory().getProductQuantity("Potato")>=3*quantity && getInventory().getProductQuantity("Carrot")>=3*quantity)){
                graphics.putString(6, 19, message);
                continue;
            }
            else if(num==2 && !(getInventory().getProductQuantity("Wood")>=3*quantity && getInventory().getProductQuantity("Iron")>=2*quantity)){
                graphics.putString(6, 19, message);
                continue;
            }
            else if(num==3 && !(getInventory().getProductQuantity("Wood")>=3*quantity && getInventory().getProductQuantity("Diamond")>=3*quantity)){
                graphics.putString(6, 19, message);
                continue;
            }
            else if(num==4 && !(getInventory().getProductQuantity("Wood")>=3*quantity && getInventory().getProductQuantity("Iron")>=1*quantity)){
                graphics.putString(6, 19, message);
                continue;
            }
            getProductScreen().refresh(); String name = "";
            switch (num){
                case 1 -> name = "Soup";
                case 2 -> name = "Axe";
                case 3 -> name = "Sword";
                case 4 -> name = "Pickaxe";
            }
            buy(name, quantity); graphics.putString(1,18, "                                                 "); graphics.putString(1,19, "                                                 ");
            getProductScreen().refresh();
        }
        getProductScreen().close();
    }

    public void buy(String name, int quantity){
        for(int i=0; i<quantity; i++)
            getInventory().addProduct(name);
        if(name=="Soup"){
            getInventory().removeProduct("Potato", 3*quantity);
            getInventory().removeProduct("Carrot", 3*quantity);
        }
        else if(name == "Axe"){
            getInventory().removeProduct("Wood", 3*quantity);
            getInventory().removeProduct("Iron", 2*quantity);
        }
        else if(name == "Sword"){
            getInventory().removeProduct("Wood", 3*quantity);
            getInventory().removeProduct("Diamond", 3*quantity);
        }
        else if(name == "Pickaxe"){
            getInventory().removeProduct("Wood", 3*quantity);
            getInventory().removeProduct("Iron", 1*quantity);
        }
    }

}