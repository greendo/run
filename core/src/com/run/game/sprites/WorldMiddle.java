package com.run.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jc on 25.05.16.
 */
public class WorldMiddle extends Objects {

    public static final int MOVEMENT = -10;

    public WorldMiddle(float x) {
        texture = new Texture(World.getWorldName() + "/backMiddle.png");
        position = new Vector2(x, 0);
        speed = new Vector2(0, 0);
    }

    @Override
    public void update(float delta) {position.add(MOVEMENT * delta, speed.y);}
}
