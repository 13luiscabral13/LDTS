package com.aor.beastie;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Map extends Place{
    private Mines mine;
    private Butcher butcher;
    private Barn barn;
    private Blacksmith blacksmith;
    private Builder builder;
    private Seller seller;
    private List<Coin> coins;
    private List<Tree> trees;
    private List<Aimless> aimlessList;
    private List<Stalker> stalkers;
    private List<Position> housePositions;

    public Map(int width, int height, Player player) {
        super(width, height, player); coins = new ArrayList<>();
        trees = new ArrayList<>(); aimlessList = new ArrayList<>();
        stalkers = new ArrayList<>(); Position p0 = new Position(60, 1);
        Position p1 = new Position(20, 1); Position p2 = new Position(40, 1);
        Position p3 = new Position(30, 7); Position p4 = new Position(40, 5);
        Position p5 = new Position(30, 30); housePositions = List.of(p0, p1, p2, p3, p4, p5);
        trees = createTrees(); coins = createCoins(); aimlessList = createAimless(); stalkers = createStalkers();
    }

    public void openMine(){
        if(getPlayer().getPosition().equals(new Position(60, 1))) {
            getPlayer().setPosition(new Position(59, 0)); mine = new Mines(60, 30, getPlayer());
            mine.createMineScreen(); mine.run(); getPlayer().setPosition(new Position(59, 1));
        }
    }

    public void openBlacksmith(){
        if(getPlayer().getPosition().equals(new Position(20, 1))){
            getPlayer().setPosition(new Position(59, 0));
            blacksmith = new Blacksmith(60, 25, getPlayer());
            blacksmith.createHouseScreen();
            blacksmith.run("Blacksmith");
            getPlayer().setPosition(new Position(19, 1));
        }
    }

    public void openButcher(){
        if(getPlayer().getPosition().equals(new Position(40, 1))) {
            getPlayer().setPosition(new Position(59, 0));
            butcher = new Butcher(60, 25, getPlayer());
            butcher.createHouseScreen();
            butcher.run("Butcher");
            getPlayer().setPosition(new Position(39, 1));
        }
    }

    public void openBarn(){
        if(getPlayer().getPosition().equals(new Position(30, 7))){
            getPlayer().setPosition(new Position(59, 0));
            barn = new Barn(60, 25, getPlayer());
            barn.createHouseScreen();
            barn.run("Barn");
            getPlayer().setPosition(new Position(29, 7));
        }
    }

    public void openBuilder(){
        if(getPlayer().getPosition().equals(new Position(40, 5))){
            getPlayer().setPosition(new Position(59, 0));
            builder = new Builder(60, 25, getPlayer());
            builder.createHouseScreen();
            builder.run("Builder");
            getPlayer().setPosition(new Position(39, 5));
        }
    }

    public void openSeller(){
        if(getPlayer().getPosition().equals(new Position(30, 30))){
            getPlayer().setPosition(new Position(59, 0));
            seller = new Seller(60, 25, getPlayer());
            seller.createHouseScreen();
            seller.run("Seller");
            getPlayer().setPosition(new Position(29, 30));
        }
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#E2FFA8")); graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(getWidth(), 3), ' '); graphics.setForegroundColor(TextColor.Factory.fromString("#000000")); graphics.enableModifiers(SGR.BOLD); graphics.putString(new TerminalPosition(60, 1), "0"); graphics.putString(new TerminalPosition(20, 1), "1"); graphics.putString(new TerminalPosition(40, 1), "2");
        graphics.setBackgroundColor(TextColor.Factory.fromString("#9bdb8f")); graphics.fillRectangle(new TerminalPosition(0, 3), new TerminalSize(getWidth(), 42), ' '); graphics.setForegroundColor(TextColor.Factory.fromString("#000000")); graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(1, 36), "PRESS: X(exit)   I(inventory)   B(balance)   T(timer)   E(eat)"); graphics.putString(new TerminalPosition(1, 37), "0-Mines   1-Blacksmith   2-Butcher   3-Barn   4-Builder   5-Seller");
        graphics.putString(new TerminalPosition(30, 7), "3"); graphics.putString(new TerminalPosition(40, 5), "4"); graphics.putString(new TerminalPosition(30, 30), "5"); //SELLER
        if(checkTree()) graphics.putString(1, 39, "Press P to plant a sampling to your left");
        if(getPlayer().getPosition().getY() < 3) graphics.setBackgroundColor(TextColor.Factory.fromString("#E2FFA8"));
        getPlayer().draw(graphics); graphics.setBackgroundColor(TextColor.Factory.fromString("#9bdb8f"));
        for(Coin coin : coins) coin.draw(graphics);
        for(Tree tree : trees) tree.draw(graphics);
        for(Aimless aimless : aimlessList) aimless.draw(graphics);
        for(Stalker stalker : stalkers) stalker.draw(graphics);
    }

    public boolean isPositionAvailable(Position position){
        boolean res = true;
        if(position.getY()<3) return false;
        for(Tree tree : trees) if (tree.getPosition().equals(position)) { res = false;break; }
        for(Coin coin : coins) if (coin.getPosition().equals(position)) { res = false;break; }
        for(Aimless aimless : aimlessList) if (aimless.getPosition().equals(position)) { res = false;break; }
        for(Stalker stalker : stalkers) if (stalker.getPosition().equals(position)) { res = false;break; }
        for(Position housePosition : housePositions) if (housePosition.equals(position)) { res = false;break; }
        return res;
    }

    private List<Coin> createCoins() {
        Random random = new Random(); List<Coin> coins = new ArrayList<>(); int num=10;
        while(true){ if(num==0) break; Coin coin = new Coin(random.nextInt(getWidth() - 1) + 1, random.nextInt(getHeight() - 1) + 1);
            if(!isPositionAvailable(coin.getPosition()) || coin.getPosition().getY()<3)  continue;
            coins.add(coin); num--;
        } return coins;
    }

    public void retrieveCoins(){ //para aumentar o balance
        for(Coin coin:coins)
            if (getPlayer().getPosition().equals(coin.getPosition())){
                coins.remove(coin);
                getPlayer().increaseBalance(1);
                Music.soundEffect("src/main/resources/coinCatch.wav");
                break;
            }
    }

    public List<Aimless> createAimless() {
        Random random = new Random(); List<Aimless> aimlessList = new ArrayList<>(); int num = 7;
        while(true){ if(num==0) break; Aimless aimless = new Aimless(random.nextInt(getWidth() - 2) + 1, random.nextInt(getHeight() - 2) + 1);
            if(!isPositionAvailable(aimless.getPosition()) || aimless.getPosition().getY()<3) continue;
            aimlessList.add(aimless); num--;
        }
        return aimlessList;
    }

    public List<Stalker> createStalkers() { //os stalkers também são criados ao calhas
        Random random = new Random(); List<Stalker> stalkers = new ArrayList<>(); int num = 1;
        while(true){ if(num==0) break; Stalker stalker = new Stalker(random.nextInt(getWidth() - 2) + 1, random.nextInt(getHeight() - 2) + 1);
            if(!isPositionAvailable(stalker.getPosition()) || stalker.getPosition().getY()<3) continue; stalkers.add(stalker); num--;
        } return stalkers;
    }

    public List<Tree> createTrees(){
        Random random = new Random(); List<Tree> trees = new ArrayList<>();
        for(int x=0; x<getWidth(); x++){
            if(x==34 || x==35) continue;
            Tree t1 = new Tree(x, 3); Tree t2 = new Tree(x, 4); trees.add(t1); trees.add(t2);
        }
        int num = 2;
        while(true){
            if(num==0) break; Tree tree = new Tree(random.nextInt(getWidth() - 2) + 1, random.nextInt(getHeight() - 2) + 1);
            if(!isPositionAvailable(tree.getPosition()) || tree.getPosition().getY()<3) continue; trees.add(tree); num--;
        }
        return trees;
    }

    public boolean checkTree(){
        for(Coin coin : coins) if(coin.getPosition().equals(new Position(getPlayer().getPosition().getX()-1, getPlayer().getPosition().getY()))) return false;
        for(Position position : housePositions) if(position.equals(new Position(getPlayer().getPosition().getX()-1, getPlayer().getPosition().getY()))) return false;
        for(Tree tree : trees) if(tree.getPosition().equals(new Position(getPlayer().getPosition().getX()-1, getPlayer().getPosition().getY()))) return false;
        for(Aimless aimless : aimlessList) if(aimless.getPosition().equals(new Position(getPlayer().getPosition().getX()-1, getPlayer().getPosition().getY()))) return false;
        for(Stalker stalker : stalkers) if(stalker.getPosition().equals(new Position(getPlayer().getPosition().getX()-1, getPlayer().getPosition().getY()))) return false;
        if(getPlayer().getPosition().getX()==0 || getInventory().getProductQuantity("Sapling")==0) return false;
        return true;
    }

    public void plantTree(Screen screen) throws IOException {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#9bdb8f"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.enableModifiers(SGR.BOLD);
        Tree tree = new Tree(getPlayer().getPosition().getX()-1, getPlayer().getPosition().getY());
        tree.setSymbol('+');
        trees.add(tree);
        getInventory().removeProduct("Sapling", 1);
        graphics.putString(1, 40, "Sampling planted! Now wait for it to grow into a beautiful tree.");
        screen.refresh();
        Music.soundEffect("src/main/resources/treePlant.wav");
    }

    public void retrieveTree(Screen screen) throws IOException, InterruptedException {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#9bdb8f"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.enableModifiers(SGR.BOLD);

        for (Tree tree : trees) {
            if (getPlayer().getPosition().equals(tree.getPosition())) {
                if(tree.getSymbol()=='*') {
                    while (true) {
                        List<Product> list = getInventory().getTools();
                        if (list.isEmpty()) {
                            graphics.putString(1, 39, "There's no tools to cut the tree.                               ");
                            screen.refresh();
                            Thread.sleep(1500);
                            break;
                        }
                        if (tree.getDamage() <= 0) {
                            Music.soundEffect("src/main/resources/treeBeat.wav");
                            trees.remove(tree);
                            getPlayer().getInventory().addProduct("Wood");
                            getPlayer().getInventory().addProduct("Wood");
                            getPlayer().getInventory().addProduct("Wood");
                            getPlayer().getInventory().addProduct("Sapling");
                            break;
                        }
                        graphics.putString(1, 39, "Choose the tool you want to use to cut the tree. Press X to quit");
                        int pos = 1, indice = 1;
                        for (Product tool : list) {
                            String string = indice + "- " + tool.getName() + " (" + tool.getValue() + ")";
                            graphics.putString(pos, 40, string);
                            indice++;
                            pos += 17;
                        }

                        graphics.putString(1, 41, "ID:               Damage caused:" + (20 - tree.getDamage()) + "/20");
                        screen.refresh();
                        KeyStroke key = screen.readInput();
                        if (key.getKeyType() == KeyType.EOF || (key.getKeyType() == KeyType.Character && key.getCharacter() == 'x'))
                            break;
                        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'i')
                            drawInventory();
                        if (key.getKeyType() == KeyType.Character && !((key.getCharacter() - '0') > 0 && (key.getCharacter() - '0') < 10))
                            continue;
                        if (key.getKeyType() == KeyType.ArrowDown || key.getKeyType() == KeyType.ArrowUp || key.getKeyType() == KeyType.ArrowLeft || key.getKeyType() == KeyType.ArrowRight)
                            continue;
                        graphics.putString(4, 41, String.valueOf(key.getCharacter()));
                        screen.refresh();
                        int num = (key.getCharacter() - '0') - 1;
                        tree.decreaseDamage(list.get(num).getValue());
                        getInventory().removeProduct(list.get(num).getName(), 1);
                        graphics.putString(1, 40, "                                                          ");
                    }
                    getPlayer().setPosition(getPlayer().getLastPosition());
                    break;
                }
                getPlayer().setPosition(getPlayer().getLastPosition());
            }
        }
    }

    public void moveMonstersAround(){
        for(Aimless aimless : aimlessList) {
            while(true) {
                Position position = aimless.moveMonster(this);
                if (!isPositionAvailable(position) || getPlayer().getPosition().equals(position))
                    continue;
                aimless.setPosition(position);
                break;
            }
        }
    }

    public void moveStalkers(){
        for(Stalker stalker : stalkers){
            Position ret = stalker.getPosition();
            Position p=getPlayer().getPosition(), s=stalker.getPosition();
            if((p.getX() == s.getX()  && p.getY() > s.getY()) || (p.getX() < s.getX()  && p.getY() > s.getY()) ||
                    (p.getX() > s.getX()  && p.getY() > s.getY())){
                ret = new Position(s.getX(), s.getY() + 1);
            }
            else if(p.getX() == s.getX()  && p.getY() < s.getY() || (p.getX() > s.getX()  && p.getY() < s.getY()) ||
                    (p.getX() < s.getX()  && p.getY() < s.getY())){
                ret = new Position(s.getX(), s.getY() - 1);
            }
            else if(p.getX() > s.getX()  && p.getY() == s.getY()){
                ret = new Position(s.getX() + 1, s.getY());
            }
            else if(p.getX() < s.getX()  && p.getY() == s.getY()){
                ret = new Position(s.getX() - 1, s.getY());
            }
            if(isPositionAvailable(ret) && !p.equals(ret))
                stalker.setPosition(ret);
        }
    }

    public boolean checkCollision(Screen screen) throws IOException, InterruptedException { //morre quando depois querem ir para o mesmo spot
        List<Product> list = getInventory().getTools(); TextGraphics graphics = screen.newTextGraphics(); graphics.setBackgroundColor(TextColor.Factory.fromString("#9bdb8f")); graphics.setForegroundColor(TextColor.Factory.fromString("#000000")); graphics.enableModifiers(SGR.BOLD);
        for(Aimless aimless : aimlessList){
            if(collision(aimless) && !list.isEmpty()){
                draw(screen.newTextGraphics()); screen.refresh(); boolean res = killMonster(screen, aimless); draw(screen.newTextGraphics()); screen.refresh();
                if(res) aimlessList.remove(aimless); draw(screen.newTextGraphics()); break;
            }
            else if(collision(aimless) && list.isEmpty()){
                if(getPlayer().getLife().length() > 1) {
                    getPlayer().setLife(getPlayer().getLife().substring(0, getPlayer().getLife().length() - 1)); draw(screen.newTextGraphics()); graphics.putString(1, 40, "You lost a life!");
                    Music.soundEffect("src/main/resources/lostLifeSound.wav"); screen.refresh(); Thread.sleep(1500);
                }
                else{
                    draw(screen.newTextGraphics()); graphics.putString(1, 40, "You died!");
                    Music.soundEffect("src/main/resources/gameOver.wav"); Thread.sleep(4000); screen.refresh(); return true;
                }
                screen.refresh();
            }
        }

        for(Stalker stalker : stalkers){
            if(collision(stalker) && !list.isEmpty()){
                draw(screen.newTextGraphics()); screen.refresh(); boolean res = killMonster(screen, stalker);
                draw(screen.newTextGraphics()); screen.refresh();
                if(res) stalkers.remove(stalker); draw(screen.newTextGraphics()); break;
            }
            else if(collision(stalker) && list.isEmpty()){
                if(getPlayer().getLife().length() > 1) {
                    getPlayer().setLife(getPlayer().getLife().substring(0, getPlayer().getLife().length() - 1)); draw(screen.newTextGraphics());
                    graphics.putString(1, 40, "You lost a life!"); Music.soundEffect("src/main/resources/lostLifeSound.wav");
                    screen.refresh(); Thread.sleep(1000);
                }
                else{
                    draw(screen.newTextGraphics()); graphics.putString(1, 40, "You died!");
                    Music.soundEffect("src/main/resources/gameOver.wav");
                    Thread.sleep(4000); screen.refresh(); return true;
                }
            }
        }
        return false;
    }

    public List<Tree> getTrees(){
        return trees;
    }

    public void growTree(List<Tree> trees){
        for(Tree tree:trees)
            if(tree.getSymbol() == '+') {
                tree.setCounter(tree.getCounter() + 1);
                if(tree.getCounter() == 30){
                    tree.setCounter(0);
                    tree.setSymbol('*');
                    Music.soundEffect("src/main/resources/treeGrow.wav");
                }
            }
    }
}