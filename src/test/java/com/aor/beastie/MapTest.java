package com.aor.beastie;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {
    Position p2 = new Position(456, 131);
    @Test
    public void paintMap() {
        TextGraphics board = Mockito.mock(TextGraphics.class);
        Player p1 = new Player(0,0);
        Map map = new Map(20,20, p1);
        map.draw(board);
        Mockito.verify(board, Mockito.times(2)).setBackgroundColor(TextColor.Factory.fromString("#9bdb8f"));

        TextGraphics board1 = Mockito.mock(TextGraphics.class);
        Player p2 = new Player(2,2);
        Map map2 = new Map(40,30, p2);
        map2.draw(board1);
        Mockito.verify(board1, Mockito.times(99)).enableModifiers(SGR.BOLD);
    }
    @Test
    public void openBarnTest() {
        Player p1 = new Player(30,3);
        Map m1 = new Map(50,50,p1);
        m1.openBarn();
        Position pos1 = new Position(30,3);
        Assertions.assertEquals(pos1.getX(), p1.getPosition().getX());
        Assertions.assertEquals(pos1.getY(), p1.getPosition().getY());
        Assertions.assertNotEquals(pos1, p2);
    }
    @Test
    public void openBlackSmithTest(){
        Player p1 = new Player(10,1);
        Map m1 = new Map(50,50,p1);
        m1.openBlacksmith();
        Position pos1 = new Position(10,1);
        Assertions.assertEquals(pos1, p1.getPosition());
        Assertions.assertNotEquals(pos1, p2);
    }
    @Test
    public void openButcherTest() {
        Player p1 = new Player(30,0);
        Map m1 = new Map(50,50,p1);
        m1.openButcher();
        Position pos1 = new Position(30,0);
        Assertions.assertEquals(pos1.getX(), p1.getPosition().getX());
        Assertions.assertEquals(pos1.getY(), p1.getPosition().getY());
        Assertions.assertNotEquals(pos1, p2);
    }
    @Test
    public void openMineTest() {
        Player p1 = new Player(70,0);
        Map m1 = new Map(50,50,p1);
        m1.openMine();
        Position pos1 = new Position(70,0);
        Assertions.assertEquals(pos1, p1.getPosition());
        Assertions.assertNotEquals(pos1, p2);
    }
    @Test
    public void openBuilderTest() {
        Player p1 = new Player(70,0);
        Map m1 = new Map(50,50,p1);
        m1.openBuilder();
        Position pos1 = new Position(70,0);
        Assertions.assertEquals(pos1, p1.getPosition());
        Assertions.assertNotEquals(pos1, p2);
    }
    @Test
    public void openSellerTest() {
        Player p1 = new Player(70,0);
        Map m1 = new Map(50,50,p1);
        m1.openSeller();
        Position pos1 = new Position(70,0);
        Assertions.assertEquals(pos1, p1.getPosition());
        Assertions.assertNotEquals(pos1, p2);
    }
    @Test
    public void positionTester() {
        Player p1 = new Player(5,5);
        Map m1 = new Map(50,50, p1);
        Position pos1 = new Position(7,7);
        Assertions.assertTrue(m1.isPositionAvailable(pos1));
        Assertions.assertNotEquals(pos1, p2);
    }
    @Test
    public void treeGrowTester() {
        Tree t1 = new Tree(2,1);
        Player p1 = new Player(3,3);
        t1.setSymbol('+');
        t1.setCounter(29);
        List<Tree>to1 = List.of(t1);
        Map m1 = new Map(40,40,p1);
        m1.growTree(to1);
        Assertions.assertEquals('*', to1.get(0).getSymbol());
    }
    @Test
    public void treeReturner() {
        Player p1 = new Player(2,2);
        Map m1 = new Map(30,30,p1);
        Assertions.assertNotNull(m1.getTrees());
    }

}
