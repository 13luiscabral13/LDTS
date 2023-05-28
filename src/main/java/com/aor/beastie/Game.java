package com.aor.beastie;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.security.Key;


public class Game {
    private Screen screen;
    private Map map;
    private Player player;
    private JFrame window;
    private JLabel label;
    private int hour, minute;
    private Timer timer1;

    public Game() {
        try {
            TerminalSize terminalSize = new TerminalSize(80, 42);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player = new Player(5, 1);
        map = new Map(80, 35, player);
    }

    public void draw() throws IOException{
        screen.clear();
        map.draw(screen.newTextGraphics());
        screen.refresh();
    }

    public void Time() {
        hour = 9; label.setText("0" + hour + ":0" + minute);
        timer1 = new javax.swing.Timer(7500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minute++;
                if (minute == 60) minute = 0; hour++; if (hour == 24) hour = 0; if (hour < 10) { if (minute < 10) label.setText("0" + hour + ":0" + minute); else label.setText("0" + hour + ":" + minute);}
                else { if (minute < 10) label.setText(hour + ":0" + minute); else label.setText(hour + ":" + minute); }
            }
        });
    }

    public void timeBegin() {
        window = new JFrame(); window.setSize(100,75);
        KeyListener k1 = new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_T) window.setVisible(false);
            }
        };
        window.addKeyListener(k1);window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); window.setLayout(null);Color color = new Color(255, 255, 255);window.getContentPane().setBackground(color); label = new JLabel("");label.setBounds(20,10,50,25);label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);label.setFont(new Font("Britannica", Font.BOLD, 16)); window.add(label);window.setVisible(false);window.setResizable(false);Time(); timer1.start();
    }

    public void run() {
        Music.playMusic();
        timeBegin();
        try {
            while(true) {
                draw(); KeyStroke key = screen.readInput(); if (map.checkTree() && key.getKeyType()==KeyType.Character && key.getCharacter()=='p') map.plantTree(screen); map.growTree(map.getTrees()); player.decreaseEnergy(player.getEnergyCounter()); map.processKey(key); map.retrieveCoins(); map.retrieveTree(screen); map.openMine(); map.openButcher(); map.openBarn(); map.openBlacksmith(); map.openBuilder(); map.openSeller(); player.setViableNumber(0);
                if (key.getKeyType() == KeyType.Character && key.getCharacter() == 't') {window.setVisible(true); player.setViableNumber(1);}
                if (key.getKeyType()== KeyType.Character && key.getCharacter()=='i') player.setViableNumber(1); if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'b') player.setViableNumber(1); if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'e') player.setViableNumber(1);
                if (key.getKeyType()==KeyType.Character && key.getCharacter()== 'x'){window.dispose();screen.close();System.exit(0);break;}
                if (key.getKeyType()==KeyType.EOF){window.dispose();screen.close();System.exit(0);break;} if (map.checkCollision(screen)){window.dispose();screen.close();System.exit(0);break;} if (player.getViableNumber() == 0) {map.moveMonstersAround();map.moveStalkers();} if (map.checkCollision(screen)){window.dispose();screen.close();System.exit(0);break;}
            } draw(); }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
