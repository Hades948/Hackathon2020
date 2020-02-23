package com.theballotbears.hackathon;

import com.tylerroyer.molasses.Config;
import com.tylerroyer.molasses.Game;

public class Driver {
    public static void main(String[] args) {
        Config.windowHeight = 352; Config.windowWidth = 352;
        Config.windowTitle = "Hackathon Project";
        Config.firstScreen = new GameScreen();
        Config.projectResourcePath = "Hackathon/src/main/java/res/";

        Game.start();
    }
}
