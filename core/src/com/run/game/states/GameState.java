package com.run.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.run.game.ActionController;
import com.run.game.Runner;
import com.run.game.sprites.Player;
import com.run.game.sprites.WorldCave;
import com.run.game.sprites.Worlds;

public class GameState extends State {

    private Worlds world;
    private Player player;

    public GameState(StateManager sm) {
        super(sm);
        camera.setToOrtho(false, Runner.WIDTH, Runner.HEIGHT);

        //test world
        world = new WorldCave("Night", true);
        player = new Player(0, Runner.HEIGHT / 3 + 10);

        //for swipes
        Gdx.input.setInputProcessor(new ActionController(new ActionController.DirectionListener() {

            @Override
            public void onLeft() {

            }

            @Override
            public void onRight() {

            }

            @Override
            public void onUp() {
                player.jump();
            }

            @Override
            public void onDown() {

            }
        }, player));
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
