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
import java.util.ArrayList;
import java.util.List;

public class Player extends Element{
    private Inventory inventory;
    private Screen balanceScreen, eatScreen;
    private int balance;
    private int energy;
    private String life;
    private Position lastPosition;
    private int energyCounter, viableNumber;

    public Player(int x, int y) {
        super(x, y);
        inventory = new Inventory();
        balance = 0;
        energy = 100;
        energyCounter = 0;
        life = "|||";
        lastPosition = new Position(x, y);
        viableNumber = 0;
    }

    public void setViableNumber(int a) {viableNumber = a;}

    public int getViableNumber() {return viableNumber;}

    public int getBalance(){
        return balance;
    }

    public int setEnergy(int energy){
        return this.energy = energy;
    }

    public int getEnergy(){
        return energy;
    }

    public int setEnergyCounter(int energyCounter){
        return this.energyCounter = energyCounter;
    }

    public int getEnergyCounter(){
        return energyCounter;
    }

    public void increaseEnergy(int energy){
        this.energy+=energy;
        if(this.energy>100)
            this.energy=100;
    }

    public void decreaseEnergy(int energyCounter){
        if(energyCounter < 30){
            setEnergyCounter(getEnergyCounter()+1);
        }
        else{
            setEnergyCounter(0);
            setEnergy(getEnergy()-1);
        }
    }

    public String getLife(){
        return life;
    }

    public void setLife(String life){
        this.life = life;
    }

    public Position getLastPosition(){
        return lastPosition;
    }

    public void setLastPosition(Position position){
        this.lastPosition = position;
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#871203"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "P");
    }

    public Position moveUp(){
        return new Position(getPosition().getX(), getPosition().getY()-1);
    }

    public Position moveDown(){
        return new Position(getPosition().getX(), getPosition().getY()+1);
    }

    public Position moveRight(){
        return new Position(getPosition().getX()+1, getPosition().getY());
    }

    public Position moveLeft(){
        return new Position(getPosition().getX()-1, getPosition().getY());
    }

    public Inventory getInventory(){
        return inventory;
    }

    public void showBalance() throws IOException {
        try {
            TerminalSize terminalSize = new TerminalSize(40, 12); DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize); Terminal terminal = terminalFactory.createTerminal();balanceScreen = new TerminalScreen(terminal); balanceScreen.setCursorPosition(null); balanceScreen.startScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextGraphics graphics= balanceScreen.newTextGraphics();graphics.setBackgroundColor(TextColor.Factory.fromString("#ACE6F7"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(40, 12), ' ');graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(0,0, "----------------------------------------");
        graphics.putString(0, 1, "            BALANCE = ");
        graphics.putString(22, 1, String.valueOf(balance));
        graphics.putString(0,2, "----------------------------------------");
        graphics.putString(0,4, "----------------------------------------");
        graphics.putString(0, 5, "             ENERGY = ");
        graphics.putString(22, 5, String.valueOf(energy));
        graphics.putString(0,6, "----------------------------------------");
        graphics.putString(0,8, "----------------------------------------");
        graphics.putString(0, 9, "               LIFE = ");
        graphics.putString(22, 9, life);
        graphics.putString(0,10, "----------------------------------------");
        balanceScreen.refresh(); KeyStroke key = balanceScreen.readInput();
        if(key.getKeyType()== KeyType.Character && key.getCharacter()=='b') setViableNumber(1);
        balanceScreen.close();
        if(key.getKeyType()==KeyType.EOF) setViableNumber(1);
        balanceScreen.close();
    }

    public void increaseBalance(int quantity){
        balance+=quantity;
    }

    public void decreaseBalance(int quantity){
        Music.soundEffect("src/main/resources/buyingSound.wav");
        balance=balance-quantity;
    }

    public int checkKey(KeyStroke key){
        System.out.println(key);
        if(key.getKeyType()==KeyType.Character && (key.getCharacter() - '0')>0 && (key.getCharacter() - '0')<10) return key.getCharacter() - '0';
        if(key.getKeyType()==KeyType.EOF) return 0;
        if(key.getKeyType() == KeyType.Character && key.getCharacter() == 'x') return 0;
        return -1;
    }
    public void eat() throws IOException, InterruptedException {
        try {
            TerminalSize terminalSize = new TerminalSize(50, 24); DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize); Terminal terminal = terminalFactory.createTerminal(); eatScreen = new TerminalScreen(terminal); eatScreen.setCursorPosition(null); eatScreen.startScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextGraphics graphics= eatScreen.newTextGraphics(); graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF")); graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(50, 24), ' '); graphics.setForegroundColor(TextColor.Factory.fromString("#000000")); graphics.enableModifiers(SGR.BOLD); graphics.putString(0,0, "--------------------------------------------------"); graphics.putString(0,1, "       Choose the product you want to eat"); graphics.putString(0,2, "     Select the product ID and the quantity");graphics.putString(0,3, "--------------------------------------------------"); graphics.putString(0, 5," ID      NAME            QUANTITY        ENERGY   ");
        while(true){
            graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF")); graphics.fillRectangle(new TerminalPosition(0, 6), new TerminalSize(50, 24), ' '); List<Product> temp = getInventory().getProductsByQuantity(); List<Product> products = new ArrayList<>();
            for(Product product : temp) if(product.getType()=="Meat" || product.getType()=="Food") products.add(product);
            if(products.isEmpty()){ graphics.putString(1, 10, "There's no products to eat"); eatScreen.refresh(); }
            int line = 7, id=1;
            for(Product product : products){ graphics.putString(1, line, String.valueOf(id)); graphics.putString(9, line, product.getName()); graphics.putString(25, line, String.valueOf(product.getQuantity())); graphics.putString(41, line, String.valueOf(product.getValue())); line++; id++; }
            graphics.putString(new TerminalPosition(1, 20), "PRESS: X(exit)"); KeyStroke key; graphics.putString(1, 21, "ID:                                "); graphics.putString(1, 22, "                                   "); eatScreen.refresh(); key = eatScreen.readInput(); int num = checkKey(key);
            if(num == 0) break; if(num == -1) continue; if(num > line-7) continue;
            graphics.putString(6, 21, String.valueOf(num)); graphics.putString(1, 22, "Quantity:                      "); eatScreen.refresh(); key = eatScreen.readInput(); int quantity = checkKey(key);
            if(quantity == 0) break; if(quantity == -1) continue; if(quantity>products.get(num-1).getQuantity()) continue;
            graphics.putString(12, 22, String.valueOf(quantity)); eatScreen.refresh(); increaseEnergy(products.get(num-1).getValue()*quantity); getInventory().removeProduct(products.get(num-1).getName(), quantity); Thread.sleep(1000);
            graphics.putString(1,21, "                                                 "); graphics.putString(1,22, "                                                 "); eatScreen.refresh();
            Music.soundEffect("src/main/resources/eatingSound.wav");
        }
        eatScreen.close();
    }
}