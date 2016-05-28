package com.run.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.run.game.Runner;

/**
 * Created by jc on 28.05.16.
 */
public abstract class Worlds {

    protected static String worldName;
    protected static Texture backMain;

    public static Texture getBackMain() {return backMain;}
    public abstract void utilityDrawings(SpriteBatch sb);

    public Worlds(String worldName) {
        this.worldName = worldName;
        backMain = new Texture(this.worldName + "/back1.png");
    }

    public class WorldMiddle extends Objects {

        public static final int MOVEMENT = -10;

        public WorldMiddle(float x) {
            texture = new Texture(worldName + "/back2.png");
            position = new Vector2(x, 0);
            speed = new Vector2(0, 0);
        }

        @Override
        public void update(float delta) {position.add(MOVEMENT * delta, speed.y);}
    }

    public class WorldPlatform extends Objects {

        public static final int MOVEMENT = -200;
        public static final int GAP = 30;
        private int distance;
        private float randWidth;

        public float getRandWidth() {return randWidth;}
        public int getDistance() {return distance;}

        public WorldPlatform(float x) {
            texture = new Texture(worldName + "/plat1.png");
            position = new Vector2(x, 0);
            speed = new Vector2(0, 0);

            randWidth = (float) Math.random() * (Runner.WIDTH / 2) + 200;

            distance = (int) (Math.random() * 60 + 50);

            frame = new Rectangle(x + GAP, Runner.HEIGHT / 3 - 15,
                    randWidth - 2 * GAP, 1);
        }

        @Override
        public void update(float delta) {
            position.add(MOVEMENT * delta, speed.y);
            frame.setPosition(position.x + GAP, Runner.HEIGHT / 3 - 15);
        }
    }
}
