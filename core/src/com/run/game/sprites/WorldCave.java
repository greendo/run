package com.run.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.run.game.Runner;

/**
 * Created by jc on 28.05.16.
 */
public class WorldCave extends Worlds {

    private Texture ceiling;
    public Texture getCeiling() {return ceiling;}

    public WorldCave(String worldName) {super(worldName);}

    @Override
    public void utilityDrawings(SpriteBatch sb) {
        sb.draw(ceiling, 0, Runner.WIDTH - 129, Runner.WIDTH, Runner.HEIGHT / 5);
    }
}