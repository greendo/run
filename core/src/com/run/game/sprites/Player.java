package com.run.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jc on 25.05.16.
 */
public class Player extends Objects {

    private int path = 0;
    private int speedX = 0;
    private int gravity = -15;

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

        speed.scl(1 / delta);
        frame.setPosition(position);
    }

    public void jump() {
        if(gravity == 0)
            speed.y = 300;
    }

    public void plat(boolean onPl, int h) {
        if(onPl) {
            gravity = 0;
            speed.y = 0;
            position.y = h / 3 - 17;
        }
        else if(position.y > h / 3)
            gravity = -15;
        else {
            gravity = -15;
            if(speed.y == 0)
                speed.y = -450;
        }
    }

    public boolean collides(Rectangle platform) {return platform.overlaps(frame);}
}
