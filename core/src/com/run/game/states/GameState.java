package com.run.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.run.game.Runner;
import com.run.game.sprites.Player;
import com.run.game.sprites.World;
import com.run.game.sprites.WorldMiddle;

public class GameState extends State {

	private World world;
	private Player player;
	private Array<WorldMiddle> worldMiddle;

	public static final int BACK_MIDDLE_COUNT = 3;

	public GameState(StateManager sm) {
		super(sm);
		camera.setToOrtho(false, Runner.WIDTH, Runner.HEIGHT);

		//test world
		world = new World("world_test");
		player = new Player(50, 130);
		worldMiddle = new Array<WorldMiddle>();

		for(int i = 0; i < BACK_MIDDLE_COUNT; i++)
			worldMiddle.add(new WorldMiddle(i * Runner.WIDTH));
	}

	@Override
	protected void handleInput() {
		if(Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
			player.jump();
	}

	@Override
	public void update(float delta) {
		handleInput();
		player.update(delta);
		camera.position.x = player.getPosition().x;

		for(WorldMiddle wm : worldMiddle)
			if(camera.position.x - camera.viewportWidth / 2 > wm.getCoord().x
							+ wm.getBackMiddle().getWidth())
				wm.reposition(wm.getCoord().x + wm.getBackMiddle().getWidth() * BACK_MIDDLE_COUNT);

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

		//sb.draw(world.backMiddle, 0, 0, width, height);
		for(WorldMiddle wm : worldMiddle)
			sb.draw(wm.getBackMiddle(), wm.getCoord().x, wm.getCoord().y, width, height);

		//player
		sb.draw(player.getPlayer(), player.getPosition().x, player.getPosition().y);

		sb.end();
	}

	@Override
	public void dispose() {

	}
}
