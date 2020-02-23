package com.theballotbears.hackathon;

import com.tylerroyer.molasses.Button;
import com.tylerroyer.molasses.Config;
import com.tylerroyer.molasses.Game;
import com.tylerroyer.molasses.GameGraphics;
import com.tylerroyer.molasses.Screen;
import com.tylerroyer.molasses.events.IncrementIntegerEvent;

import org.apache.commons.lang3.mutable.MutableInt;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class OpeningScreen extends Screen {
    private ArrayList<HashMap<Point, String>> diag = new ArrayList<>();
    private MutableInt diagIndex = new MutableInt(0);
    private Button next;

    @Override
    public void onFocus() {
        Game.setBackgroundColor(Color.BLACK);
        next = new Button("Next", Config.gameFont.deriveFont(30.0f), new Color(50, 50, 50), new Color(200, 200, 200), 100, 50, 200, 400, new IncrementIntegerEvent(diagIndex, 1));

        HashMap<Point, String> diag0 = new HashMap<>();
        HashMap<Point, String> diag1 = new HashMap<>();
        HashMap<Point, String> diag2 = new HashMap<>();
        HashMap<Point, String> diag3 = new HashMap<>();
        HashMap<Point, String> diag4 = new HashMap<>();
        HashMap<Point, String> diag5 = new HashMap<>();
        HashMap<Point, String> diag6 = new HashMap<>();

        final int OFF = 90;

        diag0.put(new Point(OFF, 50),  "Your name is Agent X. You work");
        diag0.put(new Point(OFF, 75),  "as an undercover operative for");
        diag0.put(new Point(OFF, 100), "the United States government");
        diag0.put(new Point(OFF, 125), "investigating recent intel");
        diag0.put(new Point(OFF, 150), "that indicated that Russia may");
        diag0.put(new Point(OFF, 175), "be launching a nuclear attack.");
        diag0.put(new Point(OFF, 200), "Deep within Russia’s borders,");
        diag0.put(new Point(OFF, 225), "you have been able to");
        diag0.put(new Point(OFF, 250), "infiltrate a top-secret");
        diag0.put(new Point(OFF, 275), "government safehouse where you");
        diag0.put(new Point(OFF, 300), "believe they are planning the");
        diag0.put(new Point(OFF, 325), "potential attack. While");
        diag0.put(new Point(OFF, 350), "sneaking around the safehouse,");
        diag0.put(new Point(OFF, 375), "you overhear some voices.");

        diag1.put(new Point(OFF, 50), "\"Sergeant, Operation Ballot");
        diag1.put(new Point(OFF, 75), "Bear will commence at 10:00");
        diag1.put(new Point(OFF, 100), "AM tomorrow morning\"");

        diag2.put(new Point(OFF, 50), "\"Good, the United States ");
        diag2.put(new Point(OFF, 75), "will be completely ");
        diag2.put(new Point(OFF, 100), "unprepared. Send ");
        diag2.put(new Point(OFF, 125), "confirmation to Nuclear ");
        diag2.put(new Point(OFF, 150), "Site 57 that we are ");
        diag2.put(new Point(OFF, 175), "prepared for the attack\"");
        
        diag3.put(new Point(OFF, 50), "\"Wait, did you hear");
        diag3.put(new Point(OFF, 75), "something?\"");

        diag4.put(new Point(OFF, 50), "They approach you and knock");
        diag4.put(new Point(OFF, 75), "you the f out.");

        diag5.put(new Point(OFF, 50), "A striking pain fills your");
        diag5.put(new Point(OFF, 75), "head as you wake up. Your");
        diag5.put(new Point(OFF, 100), "hands are tied behind you.");
        diag5.put(new Point(OFF, 125), "You appear to be in the");
        diag5.put(new Point(OFF, 150), "center of the safehouse. No");
        diag5.put(new Point(OFF, 175), "one seems to be around,");
        diag5.put(new Point(OFF, 200), "except a sleeping officer");
        diag5.put(new Point(OFF, 225), "at the far wall. A clock");
        diag5.put(new Point(OFF, 250), "high on the safehouse-wall");
        diag5.put(new Point(OFF, 275), "strikes 9:53 AM.");
        
        diag6.put(new Point(OFF, 50), "You have 7 minutes to");
        diag6.put(new Point(OFF, 75), "escape and somehow inform");
        diag6.put(new Point(OFF, 100), "the United States about the");
        diag6.put(new Point(OFF, 125), "pending nuclear strike.");
        
        diag.add(diag0);
        diag.add(diag1);
        diag.add(diag2);
        diag.add(diag3);
        diag.add(diag4);
        diag.add(diag5);
        diag.add(diag6);
    }

    @Override
    public void render(GameGraphics g) {
        if (diagIndex.getValue() > diag.size()-1) return;

        next.render(g);

        g.setColor(Color.WHITE);
        g.setFont(Config.gameFont);
        for (Entry<Point, String> e : diag.get(diagIndex.getValue()).entrySet()) {
            g.drawString(e.getValue(), e.getKey().getX(), e.getKey().getY());
        }
    }

    @Override
    public void update() {
        if (diagIndex.getValue() > diag.size()-1) Game.setCurrentScreen(new GameScreen());

        next.update();
    }
    
}