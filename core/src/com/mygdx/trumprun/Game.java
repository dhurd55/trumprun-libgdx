package com.mygdx.trumprun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.trumprun.handlers.BoundedCamera;
import com.mygdx.trumprun.handlers.Content;
import com.mygdx.trumprun.handlers.GameStateManager;
import com.mygdx.trumprun.handlers.MyInput;
import com.mygdx.trumprun.handlers.MyInputProcessor;

public class Game extends ApplicationAdapter {
	
	public static final String TITLE = "TRUMP RUN";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;
	
	public static final float STEP = 1 / 60f;
	private float accum;
	
	private SpriteBatch sb;
	private BoundedCamera cam;
	private OrthographicCamera hudCam;
	
	private GameStateManager gsm;
	
	public static Content res;
	
	public SpriteBatch getSpriteBatch() {return sb;}
	public BoundedCamera getCamera() {return cam;}
	public OrthographicCamera getHUDCamera0() {return hudCam;}
		
	@Override
	public void create () {
		
		Gdx.input.setInputProcessor(new MyInputProcessor());
		
		res = new Content();
		res.loadTextureAtlas(Gdx.files.internal("sprites/player/playerSheet.txt").path());
		sb = new SpriteBatch();
		cam = new BoundedCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		gsm = new GameStateManager(this);
	}

	@Override
	public void render () {
		
		accum += Gdx.graphics.getDeltaTime();
		while (accum >= STEP) {
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
			MyInput.update();
		}
	}
	
	@Override
	public void dispose () {
	}
}
