package com.run.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.run.game.Runner;

/**
 * Created by jc on 28.05.16.
 */
public class DeathState extends State {

    private String tmp;

    public DeathState(StateManager sm) {
        super(sm);
        tmp = "Tap to return to menu";
        camera.setToOrtho(false, Runner.WIDTH, Runner.HEIGHT);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
            sManager.init(new MenuState(sManager));
    }

    @Override
    public void update(float delta) {
        camera.update();
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb, BitmapFont font) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        font.draw(sb, tmp, Runner.WIDTH / 2 - 15, Runner.HEIGHT / 2);
        sb.end();
    }

    @Override
    public void dispose() {}
}
