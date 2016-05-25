package com.run.game.states;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by jc on 23.05.16.
 */
public class StateManager {

    private Stack<State> states;

    public StateManager() {
        states = new Stack<State>();
    }

    //screen to the top
    public void push(State state) {
        states.push(state);
    }

    //get upper screen and remove it from stack, clr mmr
    public void pop() {
        states.pop().dispose();
    }

    //rm upper screen and get next to up
    public void set(State state) {
        pop();
        states.push(state);
    }

    //get upper screen, update it
    public void update(float delta) {
        states.peek().update(delta);
    }

    //draw upper scr
    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

    public void render(SpriteBatch sb, BitmapFont font, int width, int height) {
        states.peek().render(sb, font, width, height);
    }
}
