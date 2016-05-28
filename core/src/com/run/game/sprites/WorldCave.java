package com.run.game.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.run.game.Runner;

/**
 * Created by jc on 28.05.16.
 */
public class WorldCave extends Worlds {

    private Texture ceiling;

    public WorldCave(String worldName) {
        super(worldName);
        ceiling = new Texture(this.worldName + "/ceiling1.png");
    }

    @Override
    public void utilityDrawings(SpriteBatch sb, OrthographicCamera camera) {
        sb.draw(ceiling, camera.position.x - camera.viewportWidth / 2,
                Runner.HEIGHT - Runner.HEIGHT / 5, Runner.WIDTH, Runner.HEIGHT / 5);
    }
}