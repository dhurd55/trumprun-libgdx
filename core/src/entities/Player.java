package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.trumprun.Game;

public class Player extends B2dSprite {
	
	public Player(Body body) {
		super (body);
		
		Texture tex = Game.res.getTexture("trump");
		TextureRegion[][] sprites = TextureRegion.split(tex, tex.getWidth(), 32);
		
		Texture idleTex = sprites[0][0].getTexture();
		
		TextureRegion[] idleSprites = TextureRegion.split(idleTex, 32, 32)[2];
		
		setAnimation(idleSprites, 1/ 12f);
		
		
		/////
	}

}
