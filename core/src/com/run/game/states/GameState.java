package com.run.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.run.game.Runner;
import com.run.game.sprites.Player;
import com.run.game.sprites.World;
import com.run.game.sprites.WorldMiddle;

public class GameState extends State {

    private World world;
    private Player player;
    private WorldMiddle worldMiddle;

    public static final int BACK_MIDDLE_COUNT = 2;

    public GameState(StateManager sm) {
        super(sm);
        camera.setToOrtho(false, Runner.WIDTH, Runner.HEIGHT);

        //test world
        world = new World("world_test");
        player = new Player(0, 0);
        worldMiddle = new WorldMiddle(Runner.WIDTH / 2 - Runner.WIDTH);
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
        camera.position.x = player.getPosition().x;

        //camera.viewportWidth == Runner.WIDTH
        if(camera.position.x - Runner.WIDTH / 2 > worldMiddle.getCoord().x
                + worldMiddle.getBackMiddle().getWidth())
            worldMiddle.reposition(worldMiddle.getCoord().x + Runner.WIDTH * BACK_MIDDLE_COUNT);//wm.getBackMiddle().getWidth() * BACK_MIDDLE_COUNT);

        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
    }

    public void render(SpriteBatch sb, BitmapFont font, int width, int height) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        //background
        sb.draw(world.getBackMain(), camera.position.x - camera.viewportWidth / 2, 0, width, height);

        sb.draw(worldMiddle.getBackMiddle(), worldMiddle.getCoord().x, worldMiddle.getCoord().y, width, height);
        sb.draw(worldMiddle.getBackMiddle(), 0, 0, Runner.WIDTH, 0, Runner.WIDTH, Runner.HEIGHT);
        //player
        sb.draw(player.getPlayer(), player.getPosition().x, player.getPosition().y);

        sb.end();
    }

    @Override
    public void dispose() {

    }
}
