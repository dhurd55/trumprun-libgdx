package com.mygdx.trumprun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.parallax.ParallaxLayer;
import com.mygdx.trumprun.handlers.BoundedCamera;
import com.mygdx.trumprun.handlers.Content;
import com.mygdx.trumprun.handlers.GameStateManager;
import com.mygdx.trumprun.handlers.MyInput;
import com.mygdx.trumprun.handlers.MyInputProcessor;

public class Game extends ApplicationAdapter {
	
	public static final String TITLE = "TRUMP RUN";
	
	public static final int V_WIDTH = 320;
	//public static final int V_WIDTH = 960;

	public static final int V_HEIGHT = 240;
	//public static final int V_HEIGHT = 640;
	
	public static final int SCALE = 2;
	
	public static final float STEP = 1 / 60f;
	private float accum;
	
	private SpriteBatch sb;
	private BoundedCamera cam;
	private OrthographicCamera hudCam;
	
	private GameStateManager gsm;
	
	public static Content playerRes;
	public static Content objectRes;
	public static Content hudRes;
	public static Content projectileRes;
	

	
	public SpriteBatch getSpriteBatch() {return sb;}
	public BoundedCamera getCamera() {return cam;}
	public OrthographicCamera getHUDCamera() {return hudCam;}
		
	@Override
	public void create () {
		
		Gdx.input.setInputProcessor(new MyInputProcessor());
		
		// Loading in sprite content
		//TODO: use texture packer to load one big sheet
		playerRes = new Content();
		playerRes.loadTextureAtlas(Gdx.files.internal("sprites/player/trumpFullSheet.txt").path());
		
		objectRes = new Content();
		objectRes.loadTextureAtlas(Gdx.files.internal("sprites/objects/objects.txt").path());
		
		hudRes = new Content();
		hudRes.loadTextureAtlas(Gdx.files.internal("sprites/objects/hud.txt").path());
		
		projectileRes = new Content();
		projectileRes.loadTextureAtlas(Gdx.files.internal("sprites/objects/MagaProjectile.txt").path());
		
		
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
