package com.run.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jc on 25.05.16.
 */
public class Player extends Objects {

    private float timeTotal = 0;
    private float time = 0;
    private int gravity = -15;

    /** GRAVITY CONSTANT */
    public static final int DEF_GRAV = -15;
    /** ACCELERATION CONSTANT */
    public static final int DEF_ACC = 20;
    /** STOP SPEED AT */
    public static final int STOP_SPD = 2000;
    /** JUMP SPEED Oy */
    public static final int JUMP_SPD = 340;

    public float getTime() {return time;}
    public float getTimeTotal() {return timeTotal;}

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
        position.add(speed.x * delta, speed.y);

        if(time < 5) {
            time += delta;
            timeTotal += delta;
        }
        else {
            if(speed.x < STOP_SPD)
                speed.x += DEF_ACC;
            time = 0;
        }

        speed.scl(1 / delta);
        frame.setPosition(position);
    }

    public void jump() {
        if(gravity == 0)
            speed.y = JUMP_SPD;
    }

    public void tackle() {}

    public void plat(boolean onPl, int h) {
        if(onPl) {
            gravity = 0;
            speed.y = 0;
            position.y = h / 4 - 17;
        }
        else if(position.y > h / 4)
            gravity = DEF_GRAV;
        else {
            gravity = DEF_GRAV;
            if(speed.y == 0)
                speed.y = -450;
        }
    }

    public boolean collides(Rectangle platform) {return platform.overlaps(frame);}
}
