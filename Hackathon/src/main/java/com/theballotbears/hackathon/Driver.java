package com.theballotbears.hackathon;

import com.tylerroyer.molasses.Config;
import com.tylerroyer.molasses.Game;

public class Driver {
    public static void main(String[] args) {
        Config.windowHeight = 480; Config.windowWidth = 480;
        Config.windowTitle = "From Russia With Love";
        Config.firstScreen = new GameScreen();
        Config.projectResourcePath = "Hackathon/src/main/java/res/";

        Game.start();
    }
}
