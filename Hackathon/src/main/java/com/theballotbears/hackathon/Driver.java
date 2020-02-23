package com.theballotbears.hackathon;

import com.tylerroyer.molasses.Config;
import com.tylerroyer.molasses.Game;
import java.awt.Font;

public class Driver {
    public static void main(String[] args) {
        Config.windowHeight = 480; Config.windowWidth = 480;
        Config.windowTitle = "From Russia With Love";
        Config.firstScreen = new OpeningScreen();
        Config.projectResourcePath = "Hackathon/src/main/java/res/";
        Config.gameFont = new Font("Courier", Font.PLAIN, 22);

        Game.start();
    }
}
