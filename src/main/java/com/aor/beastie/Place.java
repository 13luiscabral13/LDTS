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
import java.util.List;

public class Place {
    private int width;
    private int height;
    private Player player;

    public Place(int width, int height, Player player){
        this.width = width;
        this.height = height;
        this.player = player;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean canPlayerMove(Position position){
        player.setLastPosition(player.getPosition());
        return (position.getX() >= 0 && position.getX() < width) &&
                (position.getY() >= 0 && position.getY() < height);
    }

    public void movePlayer(Position position){
        if(canPlayerMove(position))
            player.setPosition(position);
        else {
            Music.soundEffect("src/main/resources/wallHitSound.wav");
        }
    }

    public Inventory getInventory(){
        return player.getInventory();
    }

    public void drawInventory() throws IOException {
        player.getInventory().draw();
    }

    public void drawBalance() throws IOException {
        player.showBalance();
    }

    public void drawEat() throws IOException, InterruptedException {
        player.eat();
    }

    public void processKey(KeyStroke key) throws IOException, InterruptedException {
        System.out.println(key);
        switch(key.getKeyType()){
            case ArrowDown -> movePlayer(getPlayer().moveDown());
            case ArrowLeft -> movePlayer(getPlayer().moveLeft());
            case ArrowRight -> movePlayer(getPlayer().moveRight());
            case ArrowUp -> movePlayer(getPlayer().moveUp());
        }

        if(key.getKeyType()== KeyType.Character && key.getCharacter()=='i') {
            player.setViableNumber(1);
            drawInventory();
        }

        if(key.getKeyType() == KeyType.Character && key.getCharacter() == 'b') {
            player.setViableNumber(1);
            drawBalance();
        }

        if(key.getKeyType() == KeyType.Character && key.getCharacter() == 'e') {
            player.setViableNumber(1);
            drawEat();
        }
    }

    public boolean killMonster(Screen screen, Monster monster) throws IOException, InterruptedException {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#9bdb8f"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.enableModifiers(SGR.BOLD);
        while (true) {
            if (monster.getDamage() == 0) {
                Music.soundEffect("src/main/resources/dyingMonster.wav");
                return true;
            }
            List<Product> list = getInventory().getTools();
            if (list.isEmpty()) {
                graphics.putString(1, 39, "There's no tools to kill the monster.                               ");
                screen.refresh();
                Thread.sleep(1000);
                break;
            }
            graphics.putString(1, 39, "Choose the tool you want to use to kill the monster. Press X to quit");
            int pos = 1, indice = 1;
            for (Product tool : list) {
                String string = indice + "- " + tool.getName() + " (" + tool.getValue() + ")";
                graphics.putString(pos, 40, string);
                indice++;
                pos += 17;
            }
            graphics.putString(1, 41, "ID:               Damage caused:" + (30 - monster.getDamage()) + "/30");
            screen.refresh();
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'i')
                drawInventory();
            if (key.getKeyType() == KeyType.EOF || (key.getKeyType() == KeyType.EOF && key.getKeyType() == KeyType.Character && key.getCharacter() == 'x'))
                break;
            if (key.getKeyType() == KeyType.Character && !((key.getCharacter() - '0') > 0 && (key.getCharacter() - '0') < 10))
                continue;
            if (key.getKeyType() == KeyType.ArrowDown || key.getKeyType() == KeyType.ArrowUp || key.getKeyType() == KeyType.ArrowLeft || key.getKeyType() == KeyType.ArrowRight)
                continue;
            graphics.putString(4, 41, String.valueOf(key.getCharacter()));
            screen.refresh();
            int num = (key.getCharacter() - '0') - 1;
            monster.setDamage(list.get(num).getValue());
            Music.soundEffect("src/main/resources/mineralGettingSound.wav");
            getInventory().removeProduct(list.get(num).getName(), 1);
            graphics.putString(1, 40, "                                                          ");
        }
        return false;
    }

    public boolean collision(Monster monster){
        Position p = getPlayer().getPosition();
        Position p1= new Position(p.getX(), p.getY()+1);
        Position p2= new Position(p.getX(), p.getY()-1);
        Position p3= new Position(p.getX()+1, p.getY());
        Position p4= new Position(p.getX()-1, p.getY());
        Position m = monster.getPosition();

        //FOI CONTRA O MONSTRO
        if (m.equals(p) || m.equals(p1) || m.equals(p2) || m.equals(p3) || m.equals(p4))
            return true;
        return false;
    }
}