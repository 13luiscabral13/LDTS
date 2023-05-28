package com.aor.beastie;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public abstract class House extends Place{
    private String name; //Butcher, Barn, Blacksmith
    private Screen screen;
    private Screen productScreen;
    private NPC npc;

    public House(int width, int height, Player player) {
        super(width, height, player);
        npc = new NPC(30, 27); //mudar a posição depois
    }

    public NPC getNpc() {
        return npc;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {return name;}

    public Screen getProductScreen() throws IOException {
        createScreen();
        createHouseScreen();
        return productScreen;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#F5F5DC"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(60, 25), ' ');
        graphics.setBackgroundColor(TextColor.Factory.fromString("#E1C699"));
        graphics.fillRectangle(new TerminalPosition(0, 25), new TerminalSize(60, 30), ' ');
        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.fillRectangle(new TerminalPosition(0, 30), new TerminalSize(60, 35), ' ');
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(1, 30), "PRESS: X(exit)  I(inventory)  B(balance)  E(eat)");
        graphics.setBackgroundColor(TextColor.Factory.fromString("#F5F5DC"));
        getPlayer().draw(graphics);
        npc.draw(graphics);
    }

    public void createHouseScreen(){
        try {
            TerminalSize terminalSize = new TerminalSize(60, 35);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createScreen() throws IOException {
        try {
            TerminalSize terminalSize = new TerminalSize(60, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            productScreen = new TerminalScreen(terminal);
            productScreen.setCursorPosition(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        productScreen.startScreen();
        TextGraphics graphics= productScreen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(60, 20), ' ');
    }

    public void drawProducts(TextGraphics graphics) throws IOException, InterruptedException {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF")); graphics.setForegroundColor(TextColor.Factory.fromString("#000000")); graphics.enableModifiers(SGR.BOLD);
        graphics.putString(0,0, "------------------------------------------------------------");
        graphics.putString(0, 1, "                 Choose the product you want");
        graphics.putString(0, 2, "           Select the product ID and the quantity");
        graphics.putString(0,3, "------------------------------------------------------------");
        graphics.putString(0, 5," ID                 NAME                PRICE               ");
        List<Product> products = new ArrayList<>();
        if(name == "Butcher") products = getInventory().getProductsByType("Meat");
        else if(name == "Barn") {products = getInventory().getProductsByType("Food"); products.addAll(getInventory().getProductsByType("Others"));}
        else if(name == "Blacksmith") products = getInventory().getProductsByType("Tools");
        int line = 7, id=1;
        for(Product product : products){
            graphics.putString(1, line, String.valueOf(id)); graphics.putString(20, line, product.getName());
            graphics.putString(40, line, String.valueOf(product.getPrice()));
            line++; id++;
        }
        graphics.putString(new TerminalPosition(1, 17), "PRESS: X(exit)  I(inventory)  B(balance)"); KeyStroke key;
        while(true){
            graphics.putString(1, 18, "ID:"); graphics.putString(1, 19, "");
            productScreen.refresh();
            key = productScreen.readInput(); checkInventory(key); checkBalance(key);
            int num = getPlayer().checkKey(key);
            if(num == 0) break;
            if(num == -1 || num > line-7) continue;
            graphics.putString(6, 18, String.valueOf(num)); graphics.putString(1, 19, "Quantity:");
            productScreen.refresh();
            String name = products.get(num-1).getName();
            key = productScreen.readInput(); checkInventory(key); checkBalance(key);
            int quantity = getPlayer().checkKey(key);
            if(quantity == 0) break;
            if(quantity == -1) continue;
            int totalPrice = products.get(num-1).getPrice() * quantity;
            if(totalPrice>getPlayer().getBalance()){
                graphics.putString(1,18, "                          ");
                productScreen.refresh(); Thread.sleep(1000);
                graphics.putString(1, 18, "Not enough money to buy the product!");
                productScreen.refresh(); Thread.sleep(1500);
                graphics.putString(1,18, "                                                 ");graphics.putString(1,19, "                                                 ");
                productScreen.refresh();
                continue;
            }

            graphics.putString(12, 19, String.valueOf(quantity)); productScreen.refresh();
            buyProduct(name, quantity, totalPrice); Thread.sleep(1000);
            graphics.putString(1, 18, "Thank you for buying our products!");
            productScreen.refresh(); Thread.sleep(1500);
            graphics.putString(1,18, "                                                 "); graphics.putString(1,19, "                                                 ");
            productScreen.refresh();
        }
        productScreen.close();
    }

    public void buyProduct(String name, int quantity, int price){
        for(int i=0; i<quantity; i++)
            getInventory().addProduct(name);
        getPlayer().decreaseBalance(price);
    }

    public void checkInventory(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'i')
            drawInventory();
    }

    public void checkBalance(KeyStroke key) throws IOException {
        if(key.getKeyType() == KeyType.Character && key.getCharacter() == 'b')
            drawBalance();
    }

    public void run(String type) {
        try {
            getNpc().setTypeNPC(type); setName(type); Music.soundEffect("src/main/resources/enterPlaceSoundEffect.wav");
            while (true) {
                getScreen().clear(); draw(screen.newTextGraphics()); getScreen().refresh();
                if (getPlayer().getPosition().getY() == 24) {
                    getNpc().talk(getScreen().newTextGraphics()); getScreen().refresh();
                    while (true) {
                        KeyStroke key = getScreen().readInput(); checkInventory(key);
                        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'n') {
                            draw(screen.newTextGraphics()); getScreen().refresh(); break;
                        }
                        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'y') {
                            draw(screen.newTextGraphics()); getScreen().refresh(); createScreen(); drawThings(productScreen.newTextGraphics()); break;
                        }
                    }
                }
                KeyStroke key = getScreen().readInput(); processKey(key);
                if (key.getKeyType() == KeyType.EOF || (key.getKeyType() == KeyType.Character && key.getCharacter() == 'x')) {
                    getScreen().close();
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void drawThings(TextGraphics graphics) throws IOException, InterruptedException;
}