package com.run.game;

import com.badlogic.gdx.input.GestureDetector;
import com.run.game.sprites.Player;

/**
 * Created by jc on 30.05.16.
 */
public class ActionController extends GestureDetector {

    private static Player player;

    public interface DirectionListener {
        void onLeft();

        void onRight();

        void onUp();

        void onDown();
    }

    public ActionController(DirectionListener directionListener, Player player) {
        super(new DirectionGestureListener(directionListener));
        this.player = player;
    }

    private static class DirectionGestureListener extends GestureAdapter {
        DirectionListener directionListener;

        public DirectionGestureListener(DirectionListener directionListener) {
            this.directionListener = directionListener;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if(Math.abs(velocityX) > Math.abs(velocityY)) {
                if(velocityX > 0) {
                    directionListener.onRight();
                } else {
                    directionListener.onLeft();
                }
            } else {
                if(velocityY > 0) {
                    directionListener.onDown();
                } else {
                    directionListener.onUp();
                }
            }
            return super.fling(velocityX, velocityY, button);
        }
    }
}
