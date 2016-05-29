package com.run.game.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.run.game.Runner;

/**
 * Created by jc on 28.05.16.
 */
public class WorldCave extends Worlds {

    private Array<Ceiling> ceiling;

    public WorldCave(String worldName, boolean debug) {
        super(worldName, debug);

        ceiling = new Array<>();
        worldMiddle = new Array<>();
        worldPlatform = new Array<>();

        for(int i = 0; i < BACK_MIDDLE_COUNT; i++)
            worldMiddle.add(new WorldMiddle(Runner.WIDTH / 2 - Runner.WIDTH + i * Runner.WIDTH));

        for(int i = 0; i < BACK_MIDDLE_COUNT; i++)
            ceiling.add(new Ceiling(Runner.WIDTH / 2 - Runner.WIDTH + i * Runner.WIDTH));

        int length = 0;
        int distance = 0;
        for(int i = 0; i < PLATFORMS_COUNT; i++) {
            if (i != 0) {
                length += worldPlatform.get(i - 1).getRandWidth();
                distance += worldPlatform.get(i - 1).getDistance();
                worldPlatform.add(new WorldPlatform((float) (length + distance)));
            } else
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
        public void update(float delta) {position.add(MOVEMENT * delta, speed.y);}

        public void reposition(float x, float y) {position.set(x, y);}
    }

    @Override
    public boolean update(float delta, Player player, OrthographicCamera camera) {

        player.update(delta);

        for(Worlds.WorldMiddle wm : worldMiddle)
            wm.update(delta);
        for(Worlds.WorldPlatform wm : worldPlatform)
            wm.update(delta);
        for(Ceiling c : ceiling)
            c.update(delta);

        camera.position.x = player.getPosition().x + Runner.WIDTH / 3;

        boolean onPl = false;

        //redraw when out of screen
        for(Worlds.WorldMiddle wm : worldMiddle)
            if(camera.position.x - Runner.WIDTH / 2 > wm.getPosition().x
                    + Runner.WIDTH)
                wm.reposition(wm.getPosition().x + Runner.WIDTH * BACK_MIDDLE_COUNT);

        for(Ceiling c : ceiling)
            if(camera.position.x - Runner.WIDTH / 2 > c.getPosition().x
                    + Runner.WIDTH)
                c.reposition(c.getPosition().x + Runner.WIDTH * BACK_MIDDLE_COUNT,
                        Runner.HEIGHT - Runner.HEIGHT / 5);

        for(Worlds.WorldPlatform wm : worldPlatform) {
            if(camera.position.x - Runner.WIDTH / 2 > wm.getPosition().x
                    + Runner.WIDTH)
                wm.reposition(wm.getPosition().x + Runner.WIDTH * BACK_MIDDLE_COUNT);

            if(player.collides(wm.getFrame()))
                onPl = true;
        }

        player.plat(onPl, Runner.HEIGHT);

        //check if dead
        if(player.getPosition().y <= 0)
            return true;

        return false;
    }

    @Override
    public void render(SpriteBatch sb, BitmapFont font, OrthographicCamera camera, Player player) {        //background
        sb.draw(getBackMain(), camera.position.x - camera.viewportWidth / 2, 0, Runner.WIDTH, Runner.HEIGHT);

        //unique textures for cave world////////////////////////////////////////////////////////////
        for(Ceiling c : ceiling)
            sb.draw(c.getTexture(), c.getPosition().x, c.getPosition().y, Runner.WIDTH, Runner.HEIGHT / 5);
        //unique textures for cave world////////////////////////////////////////////////////////////

        for(Worlds.WorldMiddle wm : worldMiddle)
            sb.draw(wm.getTexture(), wm.getPosition().x, wm.getPosition().y, Runner.WIDTH, Runner.HEIGHT);
        for(Worlds.WorldPlatform wm : worldPlatform) {
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

        if(debug)
            debug(sb, font, player);
    }

    @Override
    public void debug(SpriteBatch sb, BitmapFont font, Player player) {
        for(int i = 0; i < PLATFORMS_COUNT; i++)
            font.draw(sb, "count = " + i, worldPlatform.get(i).getPosition().x, 30);
        font.draw(sb, "speed boost in: " + (5 - player.getTime()), player.getPosition().x + 200, Runner.HEIGHT / 2);
        font.draw(sb, "total time passed: " + player.getTimeTotal(), player.getPosition().x + 200, Runner.HEIGHT / 2 - 15);
        font.draw(sb, "current speed: " + player.getSpeed(), player.getPosition().x + 200, Runner.HEIGHT / 2 - 30);

    }

    @Override
    public void dispose() {
        getBackMain().dispose();
        for(Worlds.WorldMiddle wm : worldMiddle)
            wm.getTexture().dispose();
        for(Worlds.WorldPlatform wm : worldPlatform) {
            wm.getTexture().dispose();
            wm.getWallLeft().getTexture().dispose();
            wm.getWallRight().getTexture().dispose();
        }
        for(Ceiling c : ceiling)
            c.getTexture().dispose();
    }
}