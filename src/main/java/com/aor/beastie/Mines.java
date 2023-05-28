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
import java.util.Random;

public class Mines extends Place{
    private List<Mineral> minerals;
    private List<Aimless> aimlessList;
    private Screen mineScreen;

    public Mines(int width, int height, Player player){
        super(width, height, player);
        minerals = new ArrayList<>();
        aimlessList = new ArrayList<>();
        minerals = createMinerals();
        aimlessList = createAimless();
    }

    public List<Mineral> createMinerals() {
        Random random = new Random();List<Mineral> minerals = new ArrayList<>();
        for (int i = 0; i < 10; i++) {Mineral m1 = new Mineral(random.nextInt(getWidth() - 1) + 1, random.nextInt(getHeight() - 1) + 1);m1.setName("Gold");minerals.add(m1);Mineral m2 = new Mineral(random.nextInt(getWidth() - 1) + 1, random.nextInt(getHeight() - 1) + 1);m2.setName("Iron");minerals.add(m2);Mineral m3 = new Mineral(random.nextInt(getWidth() - 1) + 1, random.nextInt(getHeight() - 1) + 1);m3.setName("Diamond");minerals.add(m3);}
        return minerals;
    }

    public void createMineScreen(){
        try {
            TerminalSize terminalSize = new TerminalSize(getWidth(), 36);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            mineScreen = new TerminalScreen(terminal);
            mineScreen.setCursorPosition(null);
            mineScreen.startScreen();
            Music.soundEffect("src/main/resources/minesSoundEffect.wav");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isPositionAvailable(Position position){
        boolean res = true;
        for(Mineral mineral : minerals)
            if (mineral.getPosition().equals(position)) {
                res = false;
                break;
            }

        for(Aimless aimless : aimlessList)
            if (aimless.getPosition().equals(position)) {
                res = false;
                break;
            }
        return res;
    }

    public void moveMonstersAround(){
        for(Aimless aimless : aimlessList) {
            while(true) {
                Position position = aimless.moveAimless(this);
                if (!isPositionAvailable(position))
                    continue;
                aimless.setPosition(position);
                break;
            }
        }
    }

    public List<Aimless> createAimless() {
        Random random = new Random();
        List<Aimless> aimlessList = new ArrayList<>();
        int num = 6;
        while(true){
            if(num==0)
                break;
            Aimless aimless = new Aimless(random.nextInt(getWidth() - 2) + 1, random.nextInt(getHeight() - 2) + 1);
            if(!isPositionAvailable(aimless.getPosition()))
                continue;
            aimlessList.add(aimless);
            num--;
        }
        return aimlessList;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#964B00"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(getWidth(), 36), ' ');
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(1, 31), "PRESS: X(exit)  I(inventory)  B(balance)   E(eat)");

        getPlayer().draw(graphics);

        for(Mineral mineral : minerals)
            mineral.draw(graphics);

        for(Aimless aimless : aimlessList)
            aimless.draw(graphics);
    }

    public void retriveMinerals(){
        for(Mineral mineral : minerals)
            if (getPlayer().getPosition().equals(mineral.getPosition())){
                getPlayer().getInventory().addProduct(mineral.getName());
                minerals.remove(mineral);
                Music.soundEffect("src/main/resources/mineralGettingSound.wav");
                break;
            }
    }

    public void run(){
        try{
            while(true) {
                mineScreen.clear();
                draw(mineScreen.newTextGraphics());
                mineScreen.refresh();
                KeyStroke key = mineScreen.readInput();
                processKey(key);
                draw(mineScreen.newTextGraphics());
                retriveMinerals();
                moveMonstersAround();
                if (checkCollision()){
                    mineScreen.refresh();
                    Thread.sleep(3000);
                    mineScreen.close();
                    System.exit(0);
                    break;
                }

                if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'x') {
                    mineScreen.close();
                    break;
                }
                if (key.getKeyType() == KeyType.EOF) {
                    mineScreen.close();
                    break;
                }
            }
            System.out.println("Dead");
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public boolean killMonster(Screen screen, Monster monster) throws IOException, InterruptedException {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#964B00"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.enableModifiers(SGR.BOLD);
        while (true) {
            if (monster.getDamage() == 0) {
                Music.soundEffect("src/main/resources/dyingMonster.wav");
                return true;
            }
            List<Product> list = getInventory().getTools();
            if (list.isEmpty()) {
                graphics.putString(1, 33, "There's no tools to kill the monster.                               ");
                screen.refresh();
                Thread.sleep(2000);
                break;
            }
            graphics.putString(1, 33, "Choose the tool you want to use to kill the monster. Press X to quit");
            int pos = 1, indice = 1;
            for (Product tool : list) {
                String string = indice + "- " + tool.getName() + " (" + tool.getValue() + ")";
                graphics.putString(pos, 34, string);
                indice++;
                pos += 17;
            }
            graphics.putString(1, 35, "ID:               Damage caused:" + (30 - monster.getDamage()) + "/30");
            screen.refresh();
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.EOF)
                break;
            if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'i')
                drawInventory();
            if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'x')
                break;
            if (key.getKeyType() == KeyType.Character && !((key.getCharacter() - '0') > 0 && (key.getCharacter() - '0') < 10))
                continue;
            if (key.getKeyType() == KeyType.ArrowDown || key.getKeyType() == KeyType.ArrowUp || key.getKeyType() == KeyType.ArrowLeft || key.getKeyType() == KeyType.ArrowRight)
                continue;
            graphics.putString(4, 35, String.valueOf(key.getCharacter()));
            screen.refresh();
            int num = (key.getCharacter() - '0') - 1;
            monster.setDamage(list.get(num).getValue());
            Music.soundEffect("src/main/resources/mineralGettingSound.wav");
            getInventory().removeProduct(list.get(num).getName(), 1);
            graphics.putString(1, 34, "                                                          ");
        }
        return false;
    }

    public boolean checkCollision() throws IOException, InterruptedException {
        List<Product> list = getInventory().getTools();
        TextGraphics graphics = mineScreen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#964B00"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.enableModifiers(SGR.BOLD);
        System.out.println(getPlayer().getLife().length());

        for(Aimless aimless : aimlessList){
            if(collision(aimless) && !list.isEmpty()){
                draw(mineScreen.newTextGraphics());
                mineScreen.refresh();
                boolean res = killMonster(mineScreen, aimless);
                draw(mineScreen.newTextGraphics());
                mineScreen.refresh();
                if(res) aimlessList.remove(aimless); draw(mineScreen.newTextGraphics()); break;
            }
            else if(collision(aimless) && list.isEmpty()){
                if(getPlayer().getLife().length() > 1) {
                    getPlayer().setLife(getPlayer().getLife().substring(0, getPlayer().getLife().length() - 1));
                    draw(mineScreen.newTextGraphics());
                    graphics.putString(1, 34, "You lost a life");
                    Music.soundEffect("src/main/resources/lostLifeSound.wav"); mineScreen.refresh(); Thread.sleep(1500);
                }
                else{
                    draw(mineScreen.newTextGraphics()); graphics.putString(1, 40, "You died!");
                    Music.soundEffect("src/main/resources/gameOver.wav"); Thread.sleep(4000); mineScreen.refresh(); return true;
                }
                mineScreen.refresh();
            }
        }
        return false;
    }
}