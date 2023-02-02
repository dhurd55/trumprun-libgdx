package com.mygdx.trumprun.states;

import static com.mygdx.trumprun.handlers.B2dVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.trumprun.Game;
import com.mygdx.trumprun.handlers.B2dVars;
import com.mygdx.trumprun.handlers.GameStateManager;
import com.mygdx.trumprun.handlers.MyContactListener;
import com.mygdx.trumprun.handlers.MyInput;
public class Play extends GameState {
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera b2dCam;
	
	private Body playerBody;
	private MyContactListener cl;
	
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tmr;
	
	private float tileSize;
	
	public Play(GameStateManager gsm) {
		super(gsm);
		
		world = new World(new Vector2(0, -9.81f), true);
		cl = new MyContactListener();
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();
		
		// create simple platform 
		
		// static body -- dont move unnaffected by forces
		// kinematic body - odnt get affected by forces  ex.. moving platform
		// dynamic body -- ex .. player
		//create body definintion
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		// create fixture definition and assign to body
		PolygonShape shape = new PolygonShape();

		
		//create player 
		bdef.position.set(160 / PPM,200 / PPM);
		bdef.type = BodyType.DynamicBody;
		playerBody = world.createBody(bdef);
		
		// create fixture definition and assign to body
		shape.setAsBox(5 / PPM, 5/ PPM);
		fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2dVars.BIT_PLAYER;
		fdef.filter.maskBits = B2dVars.BIT_GROUND;

		playerBody.createFixture(fdef).setUserData("player");
		
		// create foot sensor 
		shape.setAsBox(2 / PPM, 2/PPM, new Vector2(0, -5 / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2dVars.BIT_PLAYER;
		fdef.filter.maskBits = B2dVars.BIT_GROUND;
		fdef.isSensor = true;
		playerBody.createFixture(fdef).setUserData("foot");
		
		// config box2d cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
		
		
		/*
		 * Tiled
		 */
		
		//load tilemap
		tileMap = new TmxMapLoader().load(Gdx.files.internal("maps/test.tmx").path());
		tmr = new OrthogonalTiledMapRenderer(tileMap);
		
		TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get(0);
		//TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().
		
		tileSize = layer.getTileWidth();
		
		// go through all cells in layer
		for (int row = 0; row < layer.getHeight(); row++) {
			for (int col = 0; col < layer.getWidth(); col++) {
				
				// get cell 
				Cell cell = layer.getCell(col, row);
				
				// check if cell exists
				if (cell == null) continue;
				if (cell.getTile() == null) continue;
				
				// create a body
				bdef.type = BodyType.StaticBody;
				bdef.position.set(
						(col + 0.5f) * tileSize / PPM,
						(row + 0.5f) * tileSize / PPM
				);
						
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[3];
				v[0] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM);
				v[1] = new Vector2(-tileSize / 2 / PPM , tileSize / 2 / PPM );
				v[2] = new Vector2(tileSize / 2 / PPM, tileSize / 2 / PPM);
				
				cs.createChain(v);
				fdef.friction = 0;
				fdef.shape = cs;
				fdef.filter.categoryBits = B2dVars.BIT_GROUND;
				fdef.filter.maskBits = B2dVars.BIT_PLAYER;
				fdef.isSensor = false;
				
				world.createBody(bdef).createFixture(fdef);
			}
		}
		

	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if(MyInput.isPressed(MyInput.UP) && cl.isPlayerGrounded() ) {
			playerBody.applyForceToCenter(0, 200, true );
		}

	}

	@Override
	public void update(float dt) {
		handleInput();
		world.step(dt,  6, 2);
	}
	
	@Override
	public void render() {
		
		// clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//draw tile map
		tmr.setView(cam);
		tmr.render();
		// draw dox2d world
		b2dr.render(world, b2dCam.combined);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
