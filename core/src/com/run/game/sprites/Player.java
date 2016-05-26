package com.run.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jc on 25.05.16.
 */
public class Player extends Objects {

    //public static final int MOVEMENT = 100;
    private int gravity = -7;

    public Player(int x, int y) {
        position = new Vector2(x, y);
        speed = new Vector2(0, 0);
        texture = new Texture("player.png");
        frame = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void update(float delta) {
        if(position.y > 0)
            speed.add(0, gravity);

        speed.scl(delta);

        //position.add(MOVEMENT * delta, speed.y);
        position.add(0, speed.y);

        if(position.y < 0)
            position.y = 0;

        speed.scl(1 / delta);
        frame.setPosition(position);
    }

    public void jump() {
        if(gravity == 0)
            speed.y = 250;
    }

    public void plat(boolean onPl, int h) {
        if(onPl) {
            gravity = 0;
            speed.y = 0;
        }
        else if(position.y > h / 3)
            gravity = -7;
        else {
            gravity = -7;
            if(speed.y == 0)
                speed.y = -450;
        }
    }

    public boolean collides(Rectangle platform) {return platform.overlaps(frame);}
}
