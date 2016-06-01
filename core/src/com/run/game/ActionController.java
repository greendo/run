package com.run.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.run.game.sprites.Player;

/**
 * Created by jc on 30.05.16.
 */
public class ActionController implements InputProcessor {

    private Vector2 position;
    private Player player;

    public ActionController(Player player) {
        this.player = player;
        position = new Vector2();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        position.x = screenX;
        position.y = screenY;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 valera = new Vector2(screenX, screenY);

        if(valera.y < position.y)
            player.jump();
        if(valera.y > position.y)
            player.tackle();

        position.set(valera);
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
