package com.run.game.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.run.game.Runner;

/**
 * Created by jc on 28.05.16.
 */
public abstract class Worlds {

    protected static String worldName;
    protected static Texture backMain;
    protected static boolean debug;

    protected Array<WorldMiddle> worldMiddle;
    protected Array<Worlds.WorldPlatform> worldPlatform;

    public static final int BACK_MIDDLE_COUNT = 2;
    public static final int PLATFORMS_COUNT = 6;

    public static Texture getBackMain() {return backMain;}

    public abstract boolean update(float delta, Player player, OrthographicCamera camera);
    public abstract void render(SpriteBatch sb, BitmapFont font, OrthographicCamera camera, Player player);
    public abstract void debug(SpriteBatch sb, BitmapFont font, Player player);
    public abstract void dispose();

    public Worlds(String worldName, boolean debug) {
        this.worldName = worldName;
        backMain = new Texture(this.worldName + "/back1.png");
        this.debug = debug;
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
        public static final int GAP = 25;
        private int distance;
        private float randWidth;
        private TextureRegion wallLeft;
        private TextureRegion wallRight;

        public float getRandWidth() {return randWidth;}
        public int getDistance() {return distance;}
        public TextureRegion getWallLeft() {return wallLeft;}
        public TextureRegion getWallRight() {return wallRight;}

        public WorldPlatform(float x) {
            texture = new Texture(worldName + "/plat1.png");
            position = new Vector2(x, 0);
            speed = new Vector2(0, 0);

            randWidth = (float) Math.random() * (Runner.WIDTH / 2) + 200;

            distance = (int) (Math.random() * 90 + 70);

            frame = new Rectangle(x + GAP, Runner.HEIGHT / 4 - 15,
                    randWidth - 2 * GAP, 1);

            Texture valera = new Texture(worldName + "/wall1.png");

            wallRight = new TextureRegion(valera, 0, 0, valera.getWidth() / 2, valera.getHeight());
            wallLeft = new TextureRegion(valera, valera.getWidth() / 2, 0, valera.getWidth(), valera.getHeight());
        }

        @Override
        public void update(float delta) {
            position.add(MOVEMENT * delta, speed.y);
            frame.setPosition(position.x + GAP, Runner.HEIGHT / 4 - 15);
        }
    }
}
