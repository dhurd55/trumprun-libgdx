package com.mygdx.trumprun.states;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.trumprun.handlers.GameStateManager;
public class Menu extends GameState {
	
	private BitmapFont font = new BitmapFont();
	
	public Menu(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void render() {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		font.draw(sb,"Menu state", 100, 100);
		sb.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
