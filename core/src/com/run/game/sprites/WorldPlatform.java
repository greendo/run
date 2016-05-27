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
    public static final int gap = 50;

    private float randWidth;

    public WorldPlatform(float x) {
        texture = new Texture(World.getWorldName() + "/platform.png");
        position = new Vector2(x, 0);
        speed = new Vector2(0, 0);

        randWidth = (float) Math.random() * (Runner.WIDTH / 2) + 200;

        frame = new Rectangle(x + gap, Runner.HEIGHT / 3 - 15,
                randWidth - 2 * gap, 1);
    }

    @Override
    public void update(float delta) {
        position.add(MOVEMENT * delta, speed.y);
        frame.setPosition(position.x + gap, Runner.HEIGHT / 3 - 15);
    }

    public float getRandWidth() {
        return randWidth;
    }
}
