package com.theballotbears.hackathon;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.tylerroyer.molasses.Button;
import com.tylerroyer.molasses.Config;
import com.tylerroyer.molasses.Game;
import com.tylerroyer.molasses.GameGraphics;
import com.tylerroyer.molasses.Screen;
import com.tylerroyer.molasses.events.IncrementIntegerEvent;
import com.tylerroyer.molasses.events.SetIntegerEvent;

import org.apache.commons.lang3.mutable.MutableInt;

public class EndScreen extends Screen {
    private float off = 0;
    private float speed = 1.5f;
    private ArrayList<HashMap<Point, String>> diag = new ArrayList<>();
    private MutableInt diagIndex = new MutableInt(0);
    private Button next;
    private MutableInt num = new MutableInt(-1);
    private int choice1 = 0, choice2 = 0;

    @Override
    public void onFocus() {
        Game.setBackgroundColor(Color.BLACK);
        next = new Button("Next", Config.gameFont.deriveFont(30.0f), new Color(50, 50, 50), new Color(200, 200, 200), 100, 50, 200, 400, new IncrementIntegerEvent(diagIndex, 1));
        next.addEvent(new SetIntegerEvent(num, -1));

        Game.getKeyboardHandler().addEvent(KeyEvent.VK_0, new SetIntegerEvent(num, 0));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_1, new SetIntegerEvent(num, 1));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_2, new SetIntegerEvent(num, 2));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_3, new SetIntegerEvent(num, 3));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_4, new SetIntegerEvent(num, 4));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_5, new SetIntegerEvent(num, 5));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_6, new SetIntegerEvent(num, 6));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_7, new SetIntegerEvent(num, 7));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_8, new SetIntegerEvent(num, 8));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_9, new SetIntegerEvent(num, 9));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_NUMPAD0, new SetIntegerEvent(num, 0));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_NUMPAD1, new SetIntegerEvent(num, 1));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_NUMPAD2, new SetIntegerEvent(num, 2));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_NUMPAD3, new SetIntegerEvent(num, 3));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_NUMPAD4, new SetIntegerEvent(num, 4));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_NUMPAD5, new SetIntegerEvent(num, 5));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_NUMPAD6, new SetIntegerEvent(num, 6));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_NUMPAD7, new SetIntegerEvent(num, 7));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_NUMPAD8, new SetIntegerEvent(num, 8));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_NUMPAD9, new SetIntegerEvent(num, 9));
        Game.getKeyboardHandler().addEvent(KeyEvent.VK_BACK_SPACE, new SetIntegerEvent(num, -2));

        HashMap<Point, String> diag0 = new HashMap<>();
        HashMap<Point, String> diag1 = new HashMap<>();
        HashMap<Point, String> diag2 = new HashMap<>();
        HashMap<Point, String> diag3 = new HashMap<>();

        final int OFF = 90;

        diag0.put(new Point(OFF, 50),  "Colonel, this is Agent X, I have");
        diag0.put(new Point(OFF, 75),  "an update…");

        diag1.put(new Point(OFF, 50), "The Russians are planning an");
        diag1.put(new Point(OFF, 75), "attack on Nuclear Site ____!");
        diag1.put(new Point(OFF, 150), "Quick! Enter the site number!");

        diag2.put(new Point(OFF, 50), "You did it! The day is saved! ");
        
        diag3.put(new Point(OFF, 50), "That wasn't it.  The wrong site");
        diag3.put(new Point(OFF, 75), "was notified.  RIP America.");

        diag.add(diag0);
        diag.add(diag1);
        diag.add(diag2);
        diag.add(diag3);
    }

    @Override
    public void render(GameGraphics g) {
        if (diagIndex.getValue() > diag.size()-1) return;

        if (diagIndex.getValue() == 2) {
            if (choice1 != 5 || choice2 != 7) {
                return;
            }
        }

        if (diagIndex.getValue() == 3) {
            if (choice1 == 5 && choice2 == 7) {
                return;
            }
        }

        next.render(g);

        g.setColor(Color.WHITE);
        g.setFont(Config.gameFont);
        for (Entry<Point, String> e : diag.get(diagIndex.getValue()).entrySet()) {
            g.drawString(e.getValue(), e.getKey().getX() + off, e.getKey().getY());
        }

        if (diagIndex.getValue() == 1) {
            g.setFont(Config.gameFont.deriveFont(108.0f));
            g.drawString(choice1 + " " + choice2, 150 + off, 300);
        }
    }

    @Override
    public void update() {
        if (diagIndex.getValue() >= diag.size()) Game.close();

        if (num.getValue() > -1) {
            if (choice1 == 0) {
                choice1 = num.getValue();
            } else {
                choice2 = num.getValue();
            }
            num.setValue(-1);
        }

        if (diagIndex.getValue() == 1) {
            off += speed;
            if (off > 2 || off < -2) {
                speed = -speed;
            }
        }

        if (diagIndex.getValue() == 2) {
            if (choice1 != 5 || choice2 != 7) {
                diagIndex.setValue(3);
            }
        } else if (diagIndex.getValue() == 3) {
            if (choice1 == 5 && choice2 == 7) {
                Game.close();
            }
        }

        if (num.getValue() == -2) {
            if (choice2 != 0) {
                choice2 = 0;
            } else {
                choice1 = 0;
            }
            num.setValue(-1);
        }

        next.update();
    }
}