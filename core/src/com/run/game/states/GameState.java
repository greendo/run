package com.run.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.run.game.Runner;
import com.run.game.sprites.Player;
import com.run.game.sprites.WorldCave;
import com.run.game.sprites.Worlds;

public class GameState extends State {

    private Worlds world;
    private Player player;
    private Array<Worlds.WorldMiddle> worldMiddle;
    private Array<Worlds.WorldPlatform> worldPlatform;

    public static final int BACK_MIDDLE_COUNT = 2;
    public static final int PLATFORMS_COUNT = 5;

    public GameState(StateManager sm) {
        super(sm);
        camera.setToOrtho(false, Runner.WIDTH, Runner.HEIGHT);

        //test world
        world = new WorldCave("WorldCave");
        player = new Player(0, Runner.HEIGHT / 3 + 10);

        worldMiddle = new Array<Worlds.WorldMiddle>();
        worldPlatform = new Array<Worlds.WorldPlatform>();


        for(int i = 0; i < BACK_MIDDLE_COUNT; i++)
            worldMiddle.add(world.new WorldMiddle(Runner.WIDTH / 2 - Runner.WIDTH + i * Runner.WIDTH));

        int length = 0;
        int distance = 0;
        for(int i = 0; i < PLATFORMS_COUNT; i++) {
            if (i != 0) {
                length += worldPlatform.get(i - 1).getRandWidth();
                distance += worldPlatform.get(i - 1).getDistance();
                worldPlatform.add(world.new WorldPlatform((float) (length + distance)));
            } else
                worldPlatform.add(world.new WorldPlatform(0));
        }
    }

    private void pause() {sManager.init(new PauseState(sManager, this));}

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
            player.jump();
        if(Gdx.input.isKeyPressed(Input.Keys.R))
            sManager.init(new GameState(sManager));
    }

    @Override
    public void update(float delta) {
        handleInput();

        player.update(delta);
        for(Worlds.WorldMiddle wm : worldMiddle)
            wm.update(delta);
        for(Worlds.WorldPlatform wm : worldPlatform)
            wm.update(delta);

        camera.position.x = player.getPosition().x + Runner.WIDTH / 3;

        boolean onPl = false;

        //redraw when out of screen
        for(Worlds.WorldMiddle wm : worldMiddle)
            if(camera.position.x - Runner.WIDTH / 2 > wm.getPosition().x
                    + Runner.WIDTH)
                wm.reposition(wm.getPosition().x + Runner.WIDTH * BACK_MIDDLE_COUNT);

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
            sManager.init(new DeathState(sManager));

        camera.update();
    }


    @Override
    public void render(SpriteBatch sb, BitmapFont font) {
        sb.setProjectionMatrix(camera.combined);

        sb.begin();

        //background
        sb.draw(world.getBackMain(), camera.position.x - camera.viewportWidth / 2, 0, Runner.WIDTH, Runner.HEIGHT);
        world.utilityDrawings(sb, camera);

        for(Worlds.WorldMiddle wm : worldMiddle)
            sb.draw(wm.getTexture(), wm.getPosition().x, wm.getPosition().y, Runner.WIDTH, Runner.HEIGHT);
        for(Worlds.WorldPlatform wm : worldPlatform)
            sb.draw(wm.getTexture(), wm.getPosition().x, wm.getPosition().y, wm.getRandWidth(), Runner.HEIGHT / 3);

        for(int i = 0; i < PLATFORMS_COUNT; i++)
            font.draw(sb, "count = " + i, worldPlatform.get(i).getPosition().x, 30);

        //player
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);

        sb.end();
    }

    @Override
    public void dispose() {
        world.getBackMain().dispose();
        player.getTexture().dispose();
        for(Worlds.WorldMiddle wm : worldMiddle)
            wm.getTexture().dispose();
        for(Worlds.WorldPlatform wm : worldPlatform)
            wm.getTexture().dispose();
    }
}
