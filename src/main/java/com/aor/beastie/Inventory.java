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

public class Inventory {
    private Screen inventoryScreen;
    private List<Product> products;
    public Inventory() {
        Product product1 = new Product("Meat", "Beef" , 5, 5);
        Product product2 = new Product("Meat", "Porkchop", 4, 4);
        Product product3 = new Product("Meat", "Mutton", 4, 4);
        Product product4 = new Product("Meat", "Chicken", 2, 2);
        Product product5 = new Product("Meat", "Rabbit", 3, 3);
        Product product6 = new Product("Food", "Potato", 2, 2);
        Product product7 = new Product("Food", "Carrot", 2, 2);
        Product product8 = new Product("Food", "Soup", 5, 5);
        Product product9 = new Product("Food", "Bread", 7, 7);
        Product product10 = new Product("Food", "Cookie", 6, 6);
        Product product11 = new Product("Tools", "Pickaxe", 15, 5);
        Product product12 = new Product("Tools", "Axe", 17, 10);
        Product product13 = new Product("Tools", "Sword", 20, 20);
        Product product14 = new Product("Minerals", "Gold", 40);
        Product product15 = new Product("Minerals", "Iron", 20);
        Product product16 = new Product("Minerals", "Diamond", 60);
        Product product18 = new Product("Others", "Sapling", 15);
        Product product20 = new Product("Others", "Wood", 15);

        products = List.of(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10,
                product11, product12, product13, product14, product15, product16, product18, product20);

        //SÓ PARA TESTAR
        addProduct("Sapling");
        addProduct("Sword");
        addProduct("Sword");
        addProduct("Potato");
        addProduct("Potato");
        addProduct("Potato");
    }


    public void draw() throws IOException {
        try {
            TerminalSize terminalSize = new TerminalSize(60, 30);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            inventoryScreen = new TerminalScreen(terminal);
            inventoryScreen.setCursorPosition(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        inventoryScreen.startScreen();

        TextGraphics graphics= inventoryScreen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(60, 35), ' ');

        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(0,0, "------------------------------------------------------------");
        graphics.putString(0, 1, "                        INVENTORY");
        graphics.putString(0,2, "------------------------------------------------------------");
        graphics.putString(0, 4," NAME               TYPE                QUANTITY            ");
        graphics.putString(0, 28," Press I to exit");
        int line = 6;
        for(Product product : products){
            graphics.putString(1, line, product.getName());
            graphics.putString(20, line, product.getType());
            graphics.putString(40, line, String.valueOf(product.getQuantity()));
            line++;
        }

        inventoryScreen.refresh();

        KeyStroke key = inventoryScreen.readInput();
        if(key.getKeyType()== KeyType.Character && key.getCharacter()=='i')
            inventoryScreen.close();
        if(key.getKeyType()==KeyType.EOF)
            inventoryScreen.close();
    }

    public List<Product> getTools() {
        List<Product> res = new ArrayList<>();
        for (Product product : products)
            if (product.getType() == "Tools" && product.getQuantity() > 0)
                res.add(product);
        return res;
    }

    public int getProductQuantity(String name){
        int quantity = 0;
        for(Product product : products)
            if(product.getName()==name)
                return product.getQuantity();
        return quantity;
    }

    public List<Product> getProductsByQuantity(){
        List<Product> list = new ArrayList<>();
        for(Product product : products){
            if(product.getQuantity()>0)
                list.add(product);
        }
        return list;
    }

    public List<Product> getProductsByType(String type){
        List<Product> list = new ArrayList<>();
        for(Product product : products)
            if(product.getType()==type)
                list.add(product);
        return list;
    }

    public void addProduct(Product p1) {
        for (Product p : products)
            if (p.getName() == p1.getName()) {
                p.setQuantity(p.getQuantity() + 1);
                break;
            }
    }
    public void addProduct(String name){
        for(Product p : products)
            if(p.getName()==name) {
                p.setQuantity(p.getQuantity() + 1);
                break;
            }
    }

    public void removeProduct(String name, int quantity){
        for(Product p : products)
            if(p.getName()==name) {
                p.setQuantity(p.getQuantity() - quantity);
                break;
            }
    }
}