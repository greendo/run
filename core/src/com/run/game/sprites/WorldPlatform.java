package com.run.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.run.game.Runner;

/**
 * Created by jc on 26.05.16.
 */
public class WorldPlatform extends Objects {

    public static final int MOVEMENT = -200;

    public WorldPlatform(float x) {
        texture = new Texture(World.getWorldName() + "/platform1.png");
        position = new Vector2(x, 0);
        speed = new Vector2(0, 0);
        frame = new Rectangle(x, 0, Runner.WIDTH / 3, Runner.HEIGHT / 3);
    }

    @Override
    public void update(float delta) {position.add(MOVEMENT * delta, speed.y);}
}
