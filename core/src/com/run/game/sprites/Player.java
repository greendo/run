package com.run.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jc on 25.05.16.
 */
public class Player extends Objects {

    //public static final int MOVEMENT = 100;
    public static final int GRAVITY = -10;

    public Player(int x, int y) {
        position = new Vector2(x, y);
        speed = new Vector2(0, 0);
        texture = new Texture("player.png");
    }

    @Override
    public void update(float delta) {
        if(position.y > 0)
            speed.add(0, GRAVITY);

        speed.scl(delta);

        //position.add(MOVEMENT * delta, speed.y);
        position.add(0, speed.y);

        if(position.y < 0)
            position.y = 0;

        speed.scl(1 / delta);
    }

    public void jump() {speed.y = 250;}
}
