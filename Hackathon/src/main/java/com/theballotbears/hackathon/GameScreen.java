package com.theballotbears.hackathon;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.tylerroyer.molasses.Config;
import com.tylerroyer.molasses.Game;
import com.tylerroyer.molasses.GameGraphics;
import com.tylerroyer.molasses.Screen;
import com.tylerroyer.molasses.Tile;
import com.tylerroyer.molasses.TileMap;
import com.tylerroyer.molasses.events.*;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class GameScreen extends Screen {
    private boolean scissors = false;
    private boolean key = false;
    private boolean rope = false;
    private boolean earPiece = false;
    private String info = "Use arrow keys to move.  Use Space bar to inspect.";
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
    private MutableBoolean doInteract = new MutableBoolean(false);

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

        Event interactEvent = new ToggleOnEvent(doInteract);

        playerYUp.addCondition(playerCanMoveUp);
        playerXUp.addCondition(playerCanMoveRight);
        playerYDown.addCondition(playerCanMoveDown);
        playerXDown.addCondition(playerCanMoveLeft);

        Game.getKeyboardHandler().addEvent(KeyEvent.VK_UP, playerDirectionUp);
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_UP, playerYUp);

        Game.getKeyboardHandler().addEvent(KeyEvent.VK_RIGHT, playerDirectionRight);
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_RIGHT, playerXUp);

        Game.getKeyboardHandler().addEvent(KeyEvent.VK_DOWN, playerDirectionDown);
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_DOWN, playerYDown);

        Game.getKeyboardHandler().addEvent(KeyEvent.VK_LEFT, playerDirectionLeft);
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_LEFT, playerXDown);

        Game.getKeyboardHandler().addEvent(KeyEvent.VK_SPACE, interactEvent);

        Game.getAudioHandler().loadMusic("ride.wav");
        Game.getAudioHandler().loadSoundEffect("cut.wav");
        Game.getAudioHandler().loadSoundEffect("key_in_door.wav");
        Game.getAudioHandler().loadSoundEffect("chest.wav");
        Game.getAudioHandler().loadSoundEffect("lasso.wav");
        Game.getAudioHandler().loadSoundEffect("end.wav");

        Game.getAudioHandler().playMusic("ride.wav");
    }

    @Override
    public void render(GameGraphics g) {
        tileMap.render(g);
        player.render(g);

        g.setColor(Color.WHITE);
        g.setFont(Config.gameFont.deriveFont(12.0f));
        g.drawString(info, 5, 20);
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

        if (doInteract.isTrue()) {
            if (tileInFront != null) {
                String tileInFrontName = tileInFront.getFlipBookName();

                if (tileInFrontName.equals("16.mfb")) { // Guard
                    if (!scissors) {
                        info = "The guard's asleep. Better not wake him. He has a key around his neck, though.";
                    } else if (!key) {
                        info = "You snip the cord holding the key. Bingo! Now to get out of here";
                        key = true;
                        Game.getAudioHandler().playSoundEffect("cut.wav");
                    } else {
                        info = "He's still sleeping. I better leave before he wakes up!";
                    }
                } else if (tileInFrontName.equals("22.mfb")) { // Door
                    if (!key) {
                        info = "They're keeping my earpiece back there, but the door is locked.";
                    } else {
                        info = "You put the key in the lock. The door quietly swings open.";
                        tileMap.setTile(11, 1, "23.mfb");
                        tileMap.getTile(11, 1).setIsSolid(false);
                        tileMap.setTile(11, 0, "2.mfb");
                        tileMap.getTile(11, 0).setIsSolid(true);
                        Game.getAudioHandler().playSoundEffect("key_in_door.wav");
                    }
                } else if (tileInFrontName.equals("1.mfb")) { // Pipe
                    if (!rope) {
                        info = "You manage to fray the rope on the rusty pipe. Eureka! Your hands are free.";
                        rope = true;
                        Game.getAudioHandler().playSoundEffect("cut.wav");
                    } else {
                        info = "No use for this anymore. Hope I didn't get tetnus.";
                    }
                } else if (tileInFrontName.equals("27.mfb")) { // Normal crates
                    info = "Just a bunch of useless crates.  Nothing seems useful.";
                } else if (tileInFrontName.equals("28.mfb")) { // Special crate ;)
                    if (!rope) {
                        info = "You peer behind the crates. There’s scissors on the table. If only your hands were free.";
                    } else {
                        info = "You tie the rope into a lasso to snag the scissors. These would've been handy earlier...";
                        scissors = true;
                        tileMap.setTile(1, 7, "11B.mfb");
                        Game.getAudioHandler().playSoundEffect("lasso.wav");
                    }
                } else if (tileInFrontName.equals("6.mfb") || tileInFrontName.equals("9.mfb")) { // Chest
                    info = "There's a note in the box. \"To do: Fix sharp rusty pipe. Someone could get cut!\"";
                    Game.getAudioHandler().playSoundEffect("chest.wav");
                } else if (tileInFrontName.equals("29.mfb")) { // Junk
                    info = "There's junk everywhere!";
                } else if (tileInFrontName.equals("2.mfb")) { // Earpiece
                    earPiece = true;
                    info = "You contact the U.S. and save the day!";
                    Game.getAudioHandler().playSoundEffect("end.wav");
                    Game.getAudioHandler().pauseMusic();
                } else {
                    info = "There's nothing here.";
                }
            }

            doInteract.setFalse();
        }

        tileMap.update();
        player.update();
    }   
}