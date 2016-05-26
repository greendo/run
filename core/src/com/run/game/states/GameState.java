package com.run.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.run.game.Runner;
import com.run.game.sprites.Player;
import com.run.game.sprites.World;
import com.run.game.sprites.WorldMiddle;
import com.run.game.sprites.WorldPlatform;

public class GameState extends State {

    private World world;
    private Player player;
    private Array<WorldMiddle> worldMiddle;
    private Array<WorldPlatform> worldPlatform;

    public static final int BACK_MIDDLE_COUNT = 2;
    public static final int PLATFORMS_COUNT = 6;

    public GameState(StateManager sm) {
        super(sm);
        camera.setToOrtho(false, Runner.WIDTH, Runner.HEIGHT);

        //test world
        world = new World("world_test");
        player = new Player(0, Runner.HEIGHT / 2);

        worldMiddle = new Array<WorldMiddle>();
        worldPlatform = new Array<WorldPlatform>();

        for(int i = 0; i < BACK_MIDDLE_COUNT; i++)
            worldMiddle.add(new WorldMiddle(Runner.WIDTH / 2 - Runner.WIDTH + i * Runner.WIDTH));
        for(int i = 0; i < PLATFORMS_COUNT; i++)
            worldPlatform.add(new WorldPlatform((float) (Runner.WIDTH / 2 - Runner.WIDTH + i * 200 +
                        Math.random() * 60 + 20)));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
            player.jump();
    }

    @Override
    public void update(float delta) {
        handleInput();

        player.update(delta);
        for(WorldMiddle wm : worldMiddle)
            wm.update(delta);
        for(WorldPlatform wm : worldPlatform)
            wm.update(delta);

        camera.position.x = player.getPosition().x + Runner.WIDTH / 3;

        boolean onPl = false;

        //redraw when out of screen
        for(WorldMiddle wm : worldMiddle)
            if(camera.position.x - Runner.WIDTH / 2 > wm.getPosition().x
                    + Runner.WIDTH)
                wm.reposition(wm.getPosition().x + Runner.WIDTH * BACK_MIDDLE_COUNT);//wm.getBackMiddle().getWidth() * BACK_MIDDLE_COUNT);
        for(WorldPlatform wm : worldPlatform) {
            if(camera.position.x - Runner.WIDTH / 2 > wm.getPosition().x
                    + Runner.WIDTH)
                wm.reposition(wm.getPosition().x + Runner.WIDTH * BACK_MIDDLE_COUNT);

            if(player.collides(wm.getFrame()))
                onPl = true;
        }

        player.plat(onPl, Runner.HEIGHT);

        camera.update();
    }


    @Override
    public void render(SpriteBatch sb, BitmapFont font, int x, int y) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        //background
        sb.draw(world.getBackMain(), camera.position.x - camera.viewportWidth / 2, 0, Runner.WIDTH, Runner.HEIGHT);

        for(WorldMiddle wm : worldMiddle)
            sb.draw(wm.getTexture(), wm.getPosition().x, wm.getPosition().y, Runner.WIDTH, Runner.HEIGHT);
        for(WorldPlatform wm : worldPlatform)
            sb.draw(wm.getTexture(), wm.getPosition().x, wm.getPosition().y, wm.getTexture().getWidth(), wm.getTexture().getHeight());//Runner.HEIGHT / 3);

        //player
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);

        sb.end();
    }

    @Override
    public void render(SpriteBatch sb) {
    }

    @Override
    public void dispose() {

    }
}
