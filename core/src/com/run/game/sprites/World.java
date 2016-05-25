package com.run.game.sprites;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by jc on 24.05.16.
 */
public class World {

    private static Texture backMain;

    private static String worldName;

    public World(String worldName) {
        this.worldName = worldName;

        backMain = new Texture(this.worldName + "/backMain.png");
    }

    public static Texture getBackMain() {
        return backMain;
    }

    public static String getWorldName() {
        return worldName;
    }
}
