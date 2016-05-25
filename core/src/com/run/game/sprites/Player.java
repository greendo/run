package com.run.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jc on 25.05.16.
 */
public class Player {

    public static final int MOVEMENT = 100;
    public static final int GRAVITY = -15;

    private Vector2 position;
    private Vector2 speed;
    private Texture player;

    public boolean isOnTheGround;



    public Player(int x, int y) {
        position = new Vector2(x, y);
        speed = new Vector2(0, 0);
        player = new Texture("player.png");

        isOnTheGround = true;
    }

    public void update(float delta) {
        if(position.y > 0)
            speed.add(0, GRAVITY);
        //delta * speed scalar
        speed.scl(delta);

        position.add(MOVEMENT * delta, speed.y);
        speed.scl(1 / delta);

        if(position.y < 0)
            position.y = 0;

        speed.scl(1 /delta);
    }

    public void jump() {
        speed.y = 250;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getPlayer() {
        return player;
    }
}
