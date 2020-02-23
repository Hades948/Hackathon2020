package com.theballotbears.hackathon;

import java.util.ArrayList;

import com.tylerroyer.molasses.GameGraphics;
import com.tylerroyer.molasses.Screen;
import com.tylerroyer.molasses.TileMap;

import org.apache.commons.lang3.mutable.MutableInt;

public class PuzzleScreen extends Screen {
    private TileMap tileMap;
    private final int TILE_SIZE = 128;

    @Override
    public void onFocus() {
        tileMap = new TileMap(0, 0, "puzzle.mtm");
        ArrayList<Double> zoom = new ArrayList<>();
        zoom.add((double) TILE_SIZE/128);
        tileMap.prepareTileMapForScaling(new MutableInt(0), 0, zoom);
    }

    @Override
    public void render(GameGraphics g) {
        tileMap.render(g);
    }

    @Override
    public void update() {
        
    }
}