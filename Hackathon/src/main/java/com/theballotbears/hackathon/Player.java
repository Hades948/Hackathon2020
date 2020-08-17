package com.theballotbears.hackathon;

import org.apache.commons.lang3.mutable.MutableInt;
import java.util.ArrayList;
import com.tylerroyer.molasses.FlipBook;
import com.tylerroyer.molasses.GameGraphics;
import com.tylerroyer.molasses.GameObject;

public class Player implements GameObject {
    private int x, y;
    private MutableInt actualX, actualY;
    private MutableInt dir;
    private ArrayList<FlipBook> flipBooks = new ArrayList<>();

    public Player(MutableInt actualX, MutableInt actualY, MutableInt dir) {
        this.actualX = actualX;
        this.actualY = actualY;
        this.dir = dir;

        flipBooks.add(new FlipBook("up_stand.mfb"));
        flipBooks.add(new FlipBook("right_stand.mfb"));
        flipBooks.add(new FlipBook("down_stand.mfb"));
        flipBooks.add(new FlipBook("left_stand.mfb"));
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void render(GameGraphics g) {
        g.drawPage(flipBooks.get(dir.getValue()).getCurrentPage(), getX(), getY());
    }

    @Override
    public void update() {
        x = actualX.getValue() * 32;
        y = actualY.getValue() * 32;

        for (FlipBook fb : flipBooks) {
            fb.update();
        }
    }
}