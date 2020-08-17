package com.theballotbears.hackathon;

import com.tylerroyer.molasses.Config;
import com.tylerroyer.molasses.Game;
import java.awt.Font;

public class Driver {
    public static void main(String[] args) {
        Config.windowHeight = 480; Config.windowWidth = 520;
        Config.windowTitle = "Hackathon";
        Config.firstScreen = new OpeningScreen();
        Config.projectResourcePath = "Hackathon/src/main/java/res/";
        Config.gameFont = new Font("Courier", Font.PLAIN, 22);// TODO: This font doesn't get loaded in the jar.

        Game.start();
    }
}
