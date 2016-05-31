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

import java.util.Random;

/**
 * Created by jc on 28.05.16.
 */
public abstract class Worlds {

    protected static String worldName;
    protected static Texture backMain;
    protected static boolean debug;
    protected Random rand;

    protected Array<WorldMiddle> worldMiddle;
    protected Array<Worlds.WorldPlatform> worldPlatform;
    protected Array<Ceiling> ceiling;
    protected Array<UpperThings> upperThingsOne;
    protected Array<UpperThings> upperThingsTwo;

    public static final int BACK_MIDDLE_COUNT = 2;
    public static final int PLATFORMS_COUNT = 6;

    public static Texture getBackMain() {return backMain;}

    public Worlds(boolean debug, String worldName) {
        this.worldName = worldName;
        backMain = new Texture(this.worldName + "/back1.png");
        this.debug = debug;
        rand = new Random();

        ceiling = new Array<>();
        worldMiddle = new Array<>();
        worldPlatform = new Array<>();
        upperThingsOne = new Array<>();
        upperThingsTwo = new Array<>();

        for (int i = 0; i < BACK_MIDDLE_COUNT; i++)
            worldMiddle.add(new WorldMiddle(Runner.WIDTH / 2 - Runner.WIDTH + i * Runner.WIDTH));

        for (int i = 0; i < BACK_MIDDLE_COUNT; i++)
            ceiling.add(new Ceiling(Runner.WIDTH / 2 - Runner.WIDTH + i * Runner.WIDTH));

        int container = Runner.WIDTH;
        int y = 0;
        int length = 0;
        int distance = 0;
        while(container > 0) {
            if(y != 0) {
                length += upperThingsOne.get(y - 1).getTexture().getWidth();
                distance += upperThingsOne.get(y - 1).getDistance();
                upperThingsOne.add(new UpperThings(length + distance));
            }
            else
                upperThingsOne.add(new UpperThings(0));
            y++;
            container -= (length + distance);
        }

        container = Runner.WIDTH;
        y = 0;
        length = 0;
        distance = 0;
        while(container > 0) {
            if(y != 0) {
                length += upperThingsTwo.get(y - 1).getTexture().getWidth();
                distance += upperThingsTwo.get(y - 1).getDistance();
                upperThingsTwo.add(new UpperThings(length + distance + Runner.WIDTH));
            }
            else
                upperThingsTwo.add(new UpperThings(Runner.WIDTH));
            y++;
            container -= (length + distance);
        }

        length = 0;
        distance = 0;
        for (int i = 0; i < PLATFORMS_COUNT; i++) {
            if (i != 0) {
                length += worldPlatform.get(i - 1).getRandWidth();
                distance += worldPlatform.get(i - 1).getDistance();
                worldPlatform.add(new WorldPlatform((float) (length + distance)));
            }
            else
                worldPlatform.add(new WorldPlatform(0));
        }
    }

    public class Ceiling extends Objects {

        public static final int MOVEMENT = -200;

        public Ceiling(float x) {
            texture = new Texture(worldName + "/ceiling1.png");
            position = new Vector2(x, Runner.HEIGHT - Runner.HEIGHT / 5);
            speed = new Vector2(0, 0);
        }

        @Override
        public void update(float delta) {
            position.add(MOVEMENT * delta, speed.y);
        }

        public void reposition(float x, float y) {
            position.set(x, y);
        }
    }

    public class UpperThings extends Objects {

        private int distance;
        public static final int MOVEMENT = -200;

        public UpperThings(float x) {
            texture = new Texture(worldName + "/upper" +
                    (rand.nextInt(8) + 1) + ".png");
            position = new Vector2(x, Runner.HEIGHT - Runner.HEIGHT / 5 -
                    texture.getHeight() + 15);
            speed = new Vector2(0, 0);
            distance = rand.nextInt(80) + 40;
        }

        @Override
        public void update(float delta) {position.add(MOVEMENT * delta, speed.y);}
        public void reposition(float x, float y) {position.set(x, y);}
        public int getDistance() {return distance;}
    }

    public boolean update(float delta, Player player, OrthographicCamera camera) {

        player.update(delta);

        for (Worlds.WorldMiddle wm : worldMiddle)
            wm.update(delta);
        for (Worlds.WorldPlatform wm : worldPlatform)
            wm.update(delta);
        for (Ceiling c : ceiling)
            c.update(delta);
        for(UpperThings u : upperThingsOne)
            u.update(delta);
        for(UpperThings u : upperThingsTwo)
            u.update(delta);

        camera.position.x = player.getPosition().x + Runner.WIDTH / 3;

        boolean onPl = false;

        //redraw when out of screen
        for (Worlds.WorldMiddle wm : worldMiddle)
            if (camera.position.x - Runner.WIDTH / 2 > wm.getPosition().x
                    + Runner.WIDTH)
                wm.reposition(wm.getPosition().x + Runner.WIDTH * BACK_MIDDLE_COUNT);

        for (Ceiling c : ceiling)
            if (camera.position.x - Runner.WIDTH / 2 > c.getPosition().x
                    + Runner.WIDTH)
                c.reposition(c.getPosition().x + Runner.WIDTH * BACK_MIDDLE_COUNT,
                        Runner.HEIGHT - Runner.HEIGHT / 5);

        for (UpperThings u : upperThingsOne)
            if (camera.position.x - Runner.WIDTH / 2 > u.getPosition().x
                    + Runner.WIDTH)
                u.reposition(u.getPosition().x + Runner.WIDTH * BACK_MIDDLE_COUNT,
                        Runner.HEIGHT - Runner.HEIGHT / 5 - u.getTexture().getHeight() + 15);

        for (UpperThings u : upperThingsTwo)
            if (camera.position.x - Runner.WIDTH / 2 > u.getPosition().x
                    + Runner.WIDTH)
                u.reposition(u.getPosition().x + Runner.WIDTH * BACK_MIDDLE_COUNT,
                        Runner.HEIGHT - Runner.HEIGHT / 5 - u.getTexture().getHeight() + 15);

        for (Worlds.WorldPlatform wm : worldPlatform) {
            if (camera.position.x - Runner.WIDTH / 2 > wm.getPosition().x
                    + Runner.WIDTH)
                wm.reposition(wm.getPosition().x + Runner.WIDTH * BACK_MIDDLE_COUNT);

            if (player.collides(wm.getFrame()))
                onPl = true;
        }

        player.plat(onPl, Runner.HEIGHT);

        //check if dead
        if(player.getPosition().y <= 0)
            return true;

        return false;
    }

    public void render(SpriteBatch sb, BitmapFont font, OrthographicCamera camera, Player player) {        //background
        sb.draw(getBackMain(), camera.position.x - camera.viewportWidth / 2, 0, Runner.WIDTH, Runner.HEIGHT);

        //unique textures for cave world////////////////////////////////////////////////////////////
        for (Ceiling c : ceiling)
            sb.draw(c.getTexture(), c.getPosition().x, c.getPosition().y, Runner.WIDTH, Runner.HEIGHT / 5);
        //unique textures for cave world////////////////////////////////////////////////////////////

        for (Worlds.WorldMiddle wm : worldMiddle)
            sb.draw(wm.getTexture(), wm.getPosition().x, wm.getPosition().y, Runner.WIDTH, Runner.HEIGHT);
        for (Worlds.WorldPlatform wm : worldPlatform) {
            sb.draw(wm.getTexture(), wm.getPosition().x, wm.getPosition().y, wm.getRandWidth(), Runner.HEIGHT / 4);

            sb.draw(wm.getWallLeft(),
                    wm.getPosition().x - wm.getWallLeft().getRegionWidth() / 3 + 25,
                    wm.getPosition().y,
                    wm.getWallLeft().getRegionWidth() / 2,
                    Runner.HEIGHT / 4);

            sb.draw(wm.getWallRight(),
                    wm.getPosition().x + wm.getRandWidth(),
                    wm.getPosition().y,
                    wm.getWallRight().getRegionWidth() / 2,
                    Runner.HEIGHT / 4);
        }

        //unique textures for cave world////////////////////////////////////////////////////////////
        for (UpperThings u : upperThingsOne)
            sb.draw(u.getTexture(), u.getPosition().x, u.getPosition().y);
        for (UpperThings u : upperThingsTwo)
            sb.draw(u.getTexture(), u.getPosition().x, u.getPosition().y);
        //unique textures for cave world////////////////////////////////////////////////////////////

        if (debug)
            debug(sb, font, player);
    }

    public void debug(SpriteBatch sb, BitmapFont font, Player player) {
        for(int i = 0; i < PLATFORMS_COUNT; i++)
            font.draw(sb, "count = " + i, worldPlatform.get(i).getPosition().x, 30);
        font.draw(sb, "speed boost in: " + (5 - player.getTime()), player.getPosition().x + 200, Runner.HEIGHT / 2);
        font.draw(sb, "total time passed: " + player.getTimeTotal(), player.getPosition().x + 200, Runner.HEIGHT / 2 - 15);
        font.draw(sb, "current speed: " + player.getSpeed(), player.getPosition().x + 200, Runner.HEIGHT / 2 - 30);

    }

    public void dispose() {
        getBackMain().dispose();
        for (Worlds.WorldMiddle wm : worldMiddle)
            wm.getTexture().dispose();
        for (Worlds.WorldPlatform wm : worldPlatform) {
            wm.getTexture().dispose();
            wm.getWallLeft().getTexture().dispose();
            wm.getWallRight().getTexture().dispose();
        }
        for (Ceiling c : ceiling)
            c.getTexture().dispose();
        for(UpperThings u : upperThingsOne)
            u.getTexture().dispose();
        for(UpperThings u : upperThingsTwo)
            u.getTexture().dispose();
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

            randWidth = rand.nextInt(Runner.WIDTH / 2 - 200) + 200;

            distance = rand.nextInt(85) + 70;

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
