package com.run.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jc on 25.05.16.
 */
public class WorldMiddle {

    private Texture backMiddle;
    private Vector2 coord;

    public WorldMiddle(float x) {
        backMiddle = new Texture(World.getWorldName() + "/backMiddle.png");
        coord = new Vector2(x, 0);
    }

    public Texture getBackMiddle() {
        return backMiddle;
    }

    public Vector2 getCoord() {
        return coord;
    }

    public void reposition(float x) {
        coord.set(x, 0);
    }
}
