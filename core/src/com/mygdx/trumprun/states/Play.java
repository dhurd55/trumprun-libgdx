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
import com.mygdx.trumprun.handlers.BoundedCamera;
import com.mygdx.trumprun.handlers.GameStateManager;
import com.mygdx.trumprun.handlers.MyContactListener;
import com.mygdx.trumprun.handlers.MyInput;
import com.mygdx.trumprun.entities.Player;
import com.mygdx.trumprun.entities.PLAYERSTATE;
import com.badlogic.gdx.physics.box2d.Body;


public class Play extends GameState {
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private BoundedCamera b2dCam;
	private MyContactListener cl;
	
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tmRenderer;
	
	private float tileSize;
	private int tileMapWidth;
	private int tileMapHeight;
	
	private boolean debug = true;
	
	//private Player player;
	private Player player;
	
	public Play(GameStateManager gsm) {
		super(gsm);
		
		 // set up box2d 
		world = new World(new Vector2(0, -9.81f), true);
		cl = new MyContactListener();
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();
		
		// create player
		createPlayer();
		// create tiles
		createWalls();
		
		cam.setBounds(0, tileMapWidth * tileSize, 0, tileMapHeight * tileSize);

		
		// config box2d cam
		b2dCam = new BoundedCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
		b2dCam.setBounds(0, (tileMapWidth * tileSize) / PPM, 0, (tileMapHeight * tileSize) / PPM);
	}

	@Override
	public void handleInput() {
		
		/*player.setWalkLeft(MyInput.LEFT) && cl.isPlayerGrounded());
		player.setWalkRight(MyInput.RIGHT) && cl.isPlayerGrounded());
		player.setJumping(MyInput.isPressed(MyInput.UP) && cl.isPlayerGrounded() );
		
		/*/
		// TODO Auto-generated method stub
		if(MyInput.isPressed(MyInput.UP) && cl.isPlayerGrounded() ) {
			player.getBody().applyForceToCenter(0, 340, true);
			player.setState(PLAYERSTATE.JUMPING);
		}
		if(MyInput.isDown(MyInput.RIGHT) && cl.isPlayerGrounded() ) {
			player.getBody().setLinearVelocity(new Vector2(.4f, 0));
			player.setState(PLAYERSTATE.WALKING);
			player.setFacingRight(true);
			
		}
		if(MyInput.isDown(MyInput.LEFT) && cl.isPlayerGrounded() ) {
			player.getBody().setLinearVelocity(new Vector2(-.4f, 0));
			player.setState(PLAYERSTATE.WALKING);
			player.setFacingRight(false);
			//player.getBody().applyForceToCenter(-5, 0, true);
		}
		if (MyInput.isPressed(MyInput.DOWN)) {
			//System.out.println(player.getPositon().x);
		}
		// todo probably move this
		// handles when no input is selected but player states need to change ex. idling, falling
		else {
			// if player is still idle
			if (player.getBody().getLinearVelocity().x == 0 && cl.isPlayerGrounded()) {
				player.setState(PLAYERSTATE.IDLE);
			}
			if (player.getBody().getLinearVelocity().y < 0) {
				player.setState(PLAYERSTATE.FALLING);
			}
		}
	
	}

	@Override
	public void update(float dt) {
		handleInput();
		world.step(dt,  6, 2);
		
		player.update(dt);
	}
	
	@Override
	public void render() {
		
		// clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// camera follow player
		cam.setPosition(player.getPositon().x * PPM + game.V_WIDTH / 4, player.getPositon().y);
		cam.update();
		
		//draw tile map
		tmRenderer.setView(cam);
		tmRenderer.render();
		
		//draw player
		sb.setProjectionMatrix(cam.combined);
		player.render(sb);
		
		// debug draw box2d
		if(debug) {
			b2dCam.setPosition(player.getPositon().x + Game.V_WIDTH / 4 / PPM, Game.V_HEIGHT / 2 / PPM);
			b2dCam.update();
			b2dr.render(world, b2dCam.combined);
		}
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	/*
	 * Game object creation
	 * BodyDef() --- defins a body.. sets the type and position
	 * Body() ---    the actual body object to be used in game. Methods of this class will be used to manipulate the bodys state in game. ex .. position, velocity, etc. 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	/* method: createPlayer()
	 * scope: private
	 * return: void
	 * method variables: BodyDef bdef, FixtureDef fdef, PolygonShape shape   ( all boxed2d )
	 * description:
	 * 				create player creates and initializes the Player object for the play state. 
	 * 				This is where the players;  position, size, shape, collision (using maskBits), friction, etc. are all initialized.
	 * 				
	 * 				after the Body Definition is configured a boxed2d Body object is then created. 
	 * 				This body object will be the "skeleton" of our player class. We will use many of box2d methods to manipulate the player position, size, etc... in game during gameplay.
	 */
	private void createPlayer() {
		//BodyDef FixtureDef PolygonShape initialization
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		//configure player body definition --- bdef
		bdef.position.set(160 / PPM, 200 / PPM);
		bdef.type = BodyType.DynamicBody;
		
		//initialize body object using body def
		Body body = world.createBody(bdef);  
		
		// create fixture definition and assign to body
		shape.setAsBox(15 / PPM, 15/ PPM);
		fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = 0.7f;
		fdef.filter.categoryBits = B2dVars.BIT_PLAYER;
		fdef.filter.maskBits = B2dVars.BIT_GROUND;

		body.createFixture(fdef).setUserData("player");
		
		// create foot sensor
		shape.setAsBox(8 / PPM, 2/PPM, new Vector2(0, -13 / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2dVars.BIT_PLAYER;
		fdef.filter.maskBits = B2dVars.BIT_GROUND;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("foot");
		
		// create player
		//player = new Player(body);
		player = new Player(body);
	
	}
	
	/**
	 * Sets up the tile map collidable tiles.
	 * Reads in tile map layers and sets up box2d bodies.
	 */
	private void createWalls() {
		
		// load tile map and map renderer
		try {
			tileMap = new TmxMapLoader().load("maps/test.tmx");
		}
		catch(Exception e) {
			System.out.println("Cannot find file: maps/test.tmx");
			Gdx.app.exit();
		}
		tileMapWidth = (int) tileMap.getProperties().get("width");
		tileMapHeight = (int) tileMap.getProperties().get("height");
		tileSize = (int) tileMap.getProperties().get("tilewidth");
		tmRenderer = new OrthogonalTiledMapRenderer(tileMap);
		
		// read each of the "red" "green" and "blue" layers
		TiledMapTileLayer layer;
		layer = (TiledMapTileLayer) tileMap.getLayers().get(0);
		createTiles(layer, B2dVars.BIT_GROUND);
	}
	
	private void createTiles(TiledMapTileLayer layer, short bits) {
		//load tilemap
		
		// tile size
		float ts = layer.getTileWidth();
		
		// go through all cells in layer
		for(int row = 0; row < layer.getHeight(); row++) {
			for(int col = 0; col < layer.getWidth(); col++) {
				
				// get cell
				Cell cell = layer.getCell(col, row);
				
				// check that there is a cell
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				// create body from cell
				BodyDef bdef = new BodyDef();
				bdef.type = BodyType.StaticBody;
				bdef.position.set((col + 0.5f) * ts / PPM, (row + 0.5f) * ts / PPM);
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[3];
				v[0] = new Vector2(-ts / 2 / PPM, -ts / 2 / PPM);
				v[1] = new Vector2(-ts / 2 / PPM, ts / 2 / PPM);
				v[2] = new Vector2(ts / 2 / PPM, ts / 2 / PPM);
				cs.createChain(v);
				FixtureDef fd = new FixtureDef();
				fd.friction = .2f;
				fd.shape = cs;
				fd.filter.categoryBits = bits;
				fd.filter.maskBits = B2dVars.BIT_PLAYER;
				world.createBody(bdef).createFixture(fd);
				cs.dispose();
				
			}
		}
		
	}
}
