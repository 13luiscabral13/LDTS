package com.aor.beastie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;
import java.util.List;

public class Seller extends House {
    public Seller(int width, int height, Player player) {
        super(width, height, player);
    }

    public void drawThings(TextGraphics graphics) throws IOException, InterruptedException {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF")); graphics.setForegroundColor(TextColor.Factory.fromString("#000000")); graphics.enableModifiers(SGR.BOLD);
        graphics.putString(0,0, "------------------------------------------------------------"); graphics.putString(0,3, "------------------------------------------------------------");
        graphics.putString(0, 1, "            Choose the product you want to sell");
        graphics.putString(0, 2, "           Select the product ID and the quantity");
        graphics.putString(0, 5," ID       NAME               QUANTITY           PRICE       ");
        while(true){
            graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF")); graphics.fillRectangle(new TerminalPosition(0, 6), new TerminalSize(60, 20), ' '); List<Product> products = getInventory().getProductsByQuantity();
            if(products.isEmpty()) {
                graphics.putString(1, 10, "There's no products to sell"); getProductScreen().refresh();
            }
            int line = 7, id=1;
            for(Product product : products){
                graphics.putString(1, line, String.valueOf(id)); graphics.putString(10, line, product.getName()); graphics.putString(29, line, String.valueOf(product.getQuantity())); graphics.putString(48, line, String.valueOf(product.getPrice())); line++; id++;
            }
            graphics.putString(new TerminalPosition(1, 17), "PRESS: X(exit)  I(inventory)  B(balance)"); KeyStroke key; graphics.putString(1, 18, "ID:                                "); graphics.putString(1, 19, "                                   "); getProductScreen().refresh(); key = getProductScreen().readInput(); checkInventory(key); checkBalance(key); int num = getPlayer().checkKey(key);
            if(num == 0) break;
            if(num == -1 || num > line-7) continue;
            graphics.putString(6, 18, String.valueOf(num)); graphics.putString(1, 19, "Quantity:                      "); getProductScreen().refresh(); String name = products.get(num-1).getName(); key = getProductScreen().readInput(); checkInventory(key); checkBalance(key); int quantity = getPlayer().checkKey(key);
            if(quantity == 0) break;
            if(quantity == -1 || quantity>products.get(num-1).getQuantity()) continue;
            graphics.putString(12, 19, String.valueOf(quantity)); getProductScreen().refresh(); sell(name, quantity, products.get(num-1).getPrice()); Thread.sleep(1000); graphics.putString(1, 18, "Thank you for buying our products!"); getProductScreen().refresh(); Thread.sleep(3000); graphics.putString(1,18, "                                                 "); graphics.putString(1,19, "                                                 ");getProductScreen().refresh();
        }
        getProductScreen().close();
    }

    public void sell(String name, int quantity, int price){
        getInventory().removeProduct(name, quantity);
        getPlayer().increaseBalance(price*quantity);
        Music.soundEffect("src/main/resources/buyingSound.wav");
    }
}