package com.mygdx.trumprun.states;

import static com.mygdx.trumprun.handlers.B2dVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.example.parallax.ParallaxLayer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.trumprun.Game;
import com.mygdx.trumprun.handlers.B2dVars;
import com.mygdx.trumprun.handlers.Background;
import com.mygdx.trumprun.handlers.BoundedCamera;
import com.mygdx.trumprun.handlers.GameStateManager;
import com.mygdx.trumprun.handlers.MyContactListener;
import com.mygdx.trumprun.handlers.TileCollisions;
import com.mygdx.trumprun.handlers.MyInput;
import com.mygdx.trumprun.entities.Player;
import com.mygdx.trumprun.entities.Ballot;
import com.mygdx.trumprun.entities.HUD;
import com.mygdx.trumprun.entities.MagaHat;
import com.mygdx.trumprun.entities.Money;
import com.mygdx.trumprun.entities.PLAYERSTATE;
import com.badlogic.gdx.physics.box2d.Body;


public class Play extends GameState {
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private BoundedCamera b2dCam;
	private MyContactListener contactListener;
	
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tmRenderer;
	
	private float tileSize;
	private int tileMapWidth;
	private int tileMapHeight;
	
	private boolean debug = true;
	Background[] bglayers;
	//hud
	private HUD hud;
	
	//Player
	private Player player;
	
	//GameObjects
	private Array<MagaHat> magaHats;
	private Array<Ballot> ballots;
	private Array<Money> moneys;
	
	public Play(GameStateManager gsm) {
		super(gsm);
		
		 // set up box2d 
		world = new World(new Vector2(0, -9.81f), true);
		contactListener = new MyContactListener();
		world.setContactListener(contactListener);
		b2dr = new Box2DDebugRenderer();
		
		// create player
		createPlayer();

		createGameObjects();
		
		// create tiles
		createWalls();
		
		cam.setBounds(0, tileMapWidth * tileSize, 0, tileMapHeight * tileSize);

		hud = new HUD(player);
		
		// load in background layers for parrlax background
		
		bglayers =  new Background[3];
		
		bglayers[0] = new Background(new Texture(Gdx.files.internal("backgrounds/Background(scratch)/tall/sky.png").path()), cam, 0f);
		bglayers[1] = new Background(new Texture(Gdx.files.internal("backgrounds/Background(scratch)/tall/farBuildings.png").path()), cam, 0.1f);
		bglayers[2] = new Background(new Texture(Gdx.files.internal("backgrounds/Background(scratch)/tall/closeBuildings.png").path()), cam, 0.2f);
		
		// config box2d cam
		b2dCam = new BoundedCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
		b2dCam.setBounds(0, (tileMapWidth * tileSize) / PPM, 0, (tileMapHeight * tileSize) / PPM);
	}

	private void createPlayer() {
		//BodyDef FixtureDef PolygonShape initialization
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		//configure player body definition --- bdef
		bdef.position.set(125 / PPM, 200 / PPM);
		bdef.type = BodyType.DynamicBody;
		
		//initialize body object using body def
		Body body = world.createBody(bdef);  
		
		// create fixture definition and assign to body
		shape.setAsBox(15 / PPM, 15/ PPM);
		fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = 0.7f;
		fdef.filter.categoryBits = B2dVars.CATEGORY_PLAYER;
		fdef.filter.maskBits = B2dVars.MASK_PLAYER;

		body.createFixture(fdef).setUserData("player");
		
		// create foot sensor
		shape.setAsBox(8 / PPM, 2/PPM, new Vector2(0, -13 / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2dVars.CATEGORY_PLAYER;
		fdef.filter.maskBits = B2dVars.MASK_PLAYER;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("foot");
		
		// create player
		player = new Player(body);
	
	}
	
	/**
	 * Sets up the tile map colidable tiles.
	 * Reads in tile map layers and sets up box2d bodies.
	 */
	
	/*
	 * TODO: ability to create many objects of same type 
	 */
	private void createGameObjects() {
		
		
	
		/*
		 * MAGAHATS
		 *
		 */
		magaHats = new Array<MagaHat>();
		
		//BodyDef FixtureDef PolygonShape initialization
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		//configure player body definition --- bdef
		bdef.position.set(200/ PPM, 65 / PPM);
		bdef.type = BodyType.StaticBody;
		
		//initialize body object using body def
		Body body = world.createBody(bdef);  
		
		// create fixture definition and assign to body
		shape.setAsBox(8 / PPM, 8/ PPM);
		fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		fdef.filter.categoryBits = B2dVars.CATEGORY_OBJECT;
		fdef.filter.maskBits = B2dVars.CATEGORY_OBJECT;

		body.createFixture(fdef).setUserData("magaHat");
		MagaHat magaHat = new MagaHat(body);	
		body.setUserData(magaHat);
		// create player
		magaHats.add( magaHat);
		
		
		/*
		 * BALLOTS
		 */
		
		ballots = new Array<Ballot>();
		
		//configure player body definition --- bdef
		bdef.position.set(280/ PPM, 100 / PPM);
		bdef.type = BodyType.StaticBody;
		
		//initialize body object using body def
		body = world.createBody(bdef);  
		
		// create fixture definition and assign to body
		shape.setAsBox(8 / PPM, 8/ PPM);
		fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = 0.7f;
		fdef.isSensor = true;
		fdef.filter.categoryBits = B2dVars.CATEGORY_OBJECT;
		fdef.filter.maskBits = B2dVars.CATEGORY_OBJECT;

		body.createFixture(fdef).setUserData("ballot");
		
		Ballot ballot = new Ballot(body);
		body.setUserData(ballot);
		// create player
		ballots.add(ballot);
//		
//		/*
//		 * MONEY
//		 */
//		
        moneys = new Array<Money>();
		
		//configure player body definition --- bdef
		bdef.position.set(320/ PPM, 225 / PPM);
		bdef.type = BodyType.StaticBody;
		
		//initialize body object using body def
		body = world.createBody(bdef);  
		
		// create fixture definition and assign to body
		shape.setAsBox(8 / PPM, 8/ PPM);
		fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = 0.7f;
		fdef.isSensor = true;
		fdef.filter.categoryBits = B2dVars.CATEGORY_OBJECT;
		fdef.filter.maskBits = B2dVars.CATEGORY_OBJECT;

		body.createFixture(fdef).setUserData("money");
		
		Money money = new Money(body);
		body.setUserData(money);
		moneys.add(money);
		
	}
	private void createWalls() {
		
		// load tile map and map renderer
		try {
			tileMap = new TmxMapLoader().load("maps/test1.tmx");
		}
		catch(Exception e) {
			System.out.println("Cannot find file: maps/test.tmx");
			Gdx.app.exit();
		}
		tileMapWidth = (int) tileMap.getProperties().get("width");
		tileMapHeight = (int) tileMap.getProperties().get("height");
		tileSize = (int) tileMap.getProperties().get("tilewidth");
		tmRenderer = new OrthogonalTiledMapRenderer(tileMap, 1f);
		
		TileCollisions tiles = new TileCollisions(world, tileMap);
	}
	
	@Override
	public void handleInput() {
		
		// TODO MAKE THESE PLAYER ATTRIBUTES
		int playerJumpForce = 400;
		int MAX_SPEED = 50;
		
		/*player.setWalkLeft(MyInput.LEFT) && cl.isPlayerGrounded());
		player.setWalkRight(MyInput.RIGHT) && cl.isPlayerGrounded());
		player.setJumping(MyInput.isPressed(MyInput.UP) && cl.isPlayerGrounded() );
		
		/*/
		// TODO Auto-generated method stub
		if(MyInput.isPressed(MyInput.UP) && contactListener.isPlayerGrounded() ) {
			player.getBody().applyLinearImpulse(new Vector2(0, 4.5f), player.getBody().getWorldCenter(), true);
			player.setState(PLAYERSTATE.JUMPING);
		}
		if(MyInput.isDown(MyInput.RIGHT) && player.getBody().getLinearVelocity().x <= 2 ) {
			player.getBody().applyLinearImpulse(new Vector2(0.1f, 0), player.getBody().getWorldCenter(), true);
			player.setState(PLAYERSTATE.WALKING);
			player.setFacingRight(true);
			
		}
		if(MyInput.isDown(MyInput.LEFT) && player.getBody().getLinearVelocity().x >= -2 ) {
			player.getBody().applyLinearImpulse(new Vector2(-0.1f, 0), player.getBody().getWorldCenter(), true);
			player.setState(PLAYERSTATE.WALKING);
			player.setFacingRight(false);
			//player.getBody().applyForceToCenter(-5, 0, true);
		}
		if (MyInput.isPressed(MyInput.DOWN)) {
			//System.out.println(player.getPositon().x);
			//player.numMagaProjectiles --;
		}
		// todo probably move this
		// handles when no input is selected but player states need to change ex. idling, falling
		else {
			// if player is still idle
			if (player.getBody().getLinearVelocity().x == 0 && contactListener.isPlayerGrounded()) {
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
		
		/// INTERACTION BEWEEN PLAYER AND GAME OBJECTS
		// check for collected gameobjects
		Array<Fixture> fixtures = contactListener.getFixtures();
		for(int i = 0; i < fixtures.size; i++) {
			Fixture b = fixtures.get(i);
			Body body = b.getBody();

			//MAGAHAT
			if (b.getUserData().equals("magaHat")) {
		
				//remove magahat
				magaHats.removeValue((MagaHat) body.getUserData(), true);
				// remove body
				world.destroyBody(body);
				player.collectMagaHat();
				//todo play sound
			}
			
			//BALLOT
			else if(b.getUserData().equals("ballot")) {
				int index = ballots.indexOf((Ballot) body.getUserData(), true);
				
				//player changes
				player.collectBallot();
				
				//remove Ballot
				ballots.removeIndex(index);

				//remove body
				world.destroyBody(body);
			
				//sound
			}	
			
			//MONEY
			else if(b.getUserData().equals("money")) {
				int index = moneys.indexOf((Money) body.getUserData(), true);
				
				//player changes
				player.collectMoney(moneys.get(index).getValue());
				
				//remove money
				//moneys.removeValue((Money) b.getBody().getUserData(), true);
				moneys.removeIndex(index);
				
				//remove body
				world.destroyBody(b.getBody());
			
				//sound
			}
		}
		fixtures.clear();
		
		player.update(dt);
		
		for(int i = 0; i < magaHats.size; i++) {
			magaHats.get(i).update(dt);
		}
		for(int i = 0; i < ballots.size; i++) {
			ballots.get(i).update(dt);
		}
		for(int i = 0; i < moneys.size; i++) {
			moneys.get(i).update(dt);
		}
	}
	
	@Override
	public void render() {
		
		// clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// camera follow player
		cam.setPosition(player.getPositon().x * PPM + game.V_WIDTH / 4, player.getPositon().y*PPM + game.V_HEIGHT/4);
		cam.update();
		
		//draw bg

		sb.setProjectionMatrix(hudCam.combined);
		for (int i = 0; i < bglayers.length; i++) {
			bglayers[i].render(sb);
		}
		
		//draw tile map
		tmRenderer.setView(cam);
		tmRenderer.render();
		
		//draw player
		sb.setProjectionMatrix(cam.combined);
		player.render(sb);
		
		for(int i = 0; i < magaHats.size; i++) {
			magaHats.get(i).render(sb);
		}
		for(int i = 0; i < ballots.size; i++) {
			ballots.get(i).render(sb);
		}
		for(int i = 0; i < moneys.size; i++) {
			moneys.get(i).render(sb);
		}
		
		sb.setProjectionMatrix(hudCam.combined);
		hud.render(sb);

		// debug draw box2d
		if(!debug) {
			b2dCam.setPosition(player.getPositon().x + Game.V_WIDTH / 4 / PPM, player.getPositon().y + Game.V_HEIGHT / 4 / PPM);
			b2dCam.update();
			b2dr.render(world, b2dCam.combined);
		}
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}
