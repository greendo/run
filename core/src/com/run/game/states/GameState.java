package com.run.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.run.game.ActionController;
import com.run.game.Runner;
import com.run.game.sprites.Player;

import com.run.game.sprites.Worlds;
import com.run.game.sprites.Morning;
import com.run.game.sprites.Day;
import com.run.game.sprites.Evening;
import com.run.game.sprites.Night;

import java.util.Random;

public class GameState extends State {

    private Worlds world;
    private Player player;
    private boolean debug = true;
    private Random rand = new Random();

    public GameState(StateManager sm) {
        super(sm);
        camera.setToOrtho(false, Runner.WIDTH, Runner.HEIGHT);

        //world
        switch(rand.nextInt(4)) {
            case 0:
                world = new Morning(debug);
                break;
            case 1:
                world = new Day(debug);
                break;
            case 2:
                world = new Evening(debug);
                break;
            case 3:
                world = new Night(debug);
                break;
        }

        player = new Player(0, Runner.HEIGHT / 3 + 10);

        /** for swipes */
        Gdx.input.setInputProcessor(new ActionController(player));
    }

    public int getRand(int min, int max) {
        return rand.nextInt(max) + min;
    }

    private void pause() {sManager.init(new PauseState(sManager, this));}

    @Override
    protected void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.R))
            sManager.init(new GameState(sManager));
//        if(Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
//            player.jump();
    }

    @Override
    public void update(float delta) {
        handleInput();

        if(world.update(delta, player, camera))
            sManager.init(new DeathState(sManager));

        camera.update();
    }

    @Override
    public void render(SpriteBatch sb, BitmapFont font) {
        sb.setProjectionMatrix(camera.combined);

        sb.begin();

        world.render(sb, font, camera, player);
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);

        sb.end();
    }

    @Override
    public void dispose() {
        player.getTexture().dispose();
        world.dispose();
    }
}
