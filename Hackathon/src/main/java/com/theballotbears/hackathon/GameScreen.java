package com.theballotbears.hackathon;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.tylerroyer.molasses.Game;
import com.tylerroyer.molasses.GameGraphics;
import com.tylerroyer.molasses.Screen;
import com.tylerroyer.molasses.Tile;
import com.tylerroyer.molasses.TileMap;
import com.tylerroyer.molasses.events.*;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class GameScreen extends Screen {
    private TileMap tileMap;
    private Player player;
    private final int START_X = 7, START_Y = 6;
    private final int TILE_SIZE = 32;
    private MutableInt playerX = new MutableInt(START_X), playerY = new MutableInt(START_Y);
    private MutableInt playerDirection = new MutableInt(2);
    private MutableBoolean playerCanMoveUp = new MutableBoolean(false);
    private MutableBoolean playerCanMoveRight = new MutableBoolean(false);
    private MutableBoolean playerCanMoveDown = new MutableBoolean(false);
    private MutableBoolean playerCanMoveLeft = new MutableBoolean(false);
    private MutableBoolean canInspect = new MutableBoolean(false);

    @Override
    public void onFocus() {
        player = new Player(playerX, playerY, playerDirection);

        tileMap = new TileMap(0, 0, "map.mtm");
        ArrayList<Double> zoom = new ArrayList<>();
        zoom.add((double) TILE_SIZE/128);
        tileMap.prepareTileMapForScaling(new MutableInt(0), 0, zoom);

        Event playerYUp = new DecrementIntegerEvent(playerY, 1, 0);
        Event playerXDown = new DecrementIntegerEvent(playerX, 1, 0);
        Event playerYDown = new IncrementIntegerEvent(playerY, 1, tileMap.getHeight() - 1);
        Event playerXUp = new IncrementIntegerEvent(playerX, 1, tileMap.getWidth() - 1);

        Event playerDirectionUp = new SetIntegerEvent(playerDirection, 0);
        Event playerDirectionRight = new SetIntegerEvent(playerDirection, 1);
        Event playerDirectionDown = new SetIntegerEvent(playerDirection, 2);
        Event playerDirectionLeft = new SetIntegerEvent(playerDirection, 3);

        Event inspectEvent = new LoggerEvent("Inspect.");

        playerYUp.addCondition(playerCanMoveUp);
        playerXUp.addCondition(playerCanMoveRight);
        playerYDown.addCondition(playerCanMoveDown);
        playerXDown.addCondition(playerCanMoveLeft);

        inspectEvent.addCondition(canInspect);

        Game.getKeyboardHandler().addEvent(KeyEvent.VK_UP, playerDirectionUp);
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_UP, playerYUp);

        Game.getKeyboardHandler().addEvent(KeyEvent.VK_RIGHT, playerDirectionRight);
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_RIGHT, playerXUp);

        Game.getKeyboardHandler().addEvent(KeyEvent.VK_DOWN, playerDirectionDown);
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_DOWN, playerYDown);

        Game.getKeyboardHandler().addEvent(KeyEvent.VK_LEFT, playerDirectionLeft);
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_LEFT, playerXDown);

        Game.getKeyboardHandler().addEvent(KeyEvent.VK_I, inspectEvent);
    }

    @Override
    public void render(GameGraphics g) {
        tileMap.render(g);
        player.render(g);
    }

    @Override
    public void update() {
        Tile tileUp = tileMap.getTile(playerX.getValue(), playerY.getValue() - 1);
        Tile tileRight = tileMap.getTile(playerX.getValue() + 1, playerY.getValue());
        Tile tileDown = tileMap.getTile(playerX.getValue(), playerY.getValue() + 1);
        Tile tileLeft = tileMap.getTile(playerX.getValue() - 1, playerY.getValue());
        Tile tileInFront = null;
        if (playerDirection.getValue() == 0) tileInFront = tileUp;
        else if (playerDirection.getValue() == 1) tileInFront = tileRight;
        else if (playerDirection.getValue() == 2) tileInFront = tileDown;
        else if (playerDirection.getValue() == 3) tileInFront = tileLeft;

        playerCanMoveUp.setValue(tileUp == null ? false : !tileUp.isSolid());
        playerCanMoveRight.setValue(tileRight == null ? false : !tileRight.isSolid());
        playerCanMoveDown.setValue(tileDown == null ? false : !tileDown.isSolid());
        playerCanMoveLeft.setValue(tileLeft == null ? false : !tileLeft.isSolid());
        canInspect.setValue(tileInFront == null ? false : tileInFront.getFlipBookName().equals("object.mfb"));//todo

        tileMap.update();
        player.update();
    }   
}