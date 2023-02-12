package com.mygdx.trumprun.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.trumprun.Game;
import com.mygdx.trumprun.handlers.B2dVars;

public class Player extends B2dSprite {
	
	// texture regions for animation
	TextureRegion[] cigar;
	TextureRegion[] falling;
	TextureRegion[] idle;
	TextureRegion[] jump;
	TextureRegion[] run;
	TextureRegion[] walk;
	
	PLAYERSTATE currentState;
	// player stuf
	
	boolean facingRight;
	boolean flipSprite;
	
	
	public Player(Body body) {
		super (body);
		
		//////////////  Animation  \\\\\\\\\\\\\\
		
		// load cigar sheet
		cigar = Game.res.getTextureRegion("Trump_CIGAR-Sheet").split(32, 32)[0];
		falling = Game.res.getTextureRegion("Trump_FALLING-Sheet").split(32, 32)[0];
		idle = Game.res.getTextureRegion("Trump_IDLE-Sheet").split(32, 32)[0];
		jump = Game.res.getTextureRegion("Trump_JUMP-Sheet").split(32, 32)[0];
		run = Game.res.getTextureRegion("Trump_RUN-Sheet").split(32, 32)[0];
		walk = Game.res.getTextureRegion("Trump_WALK-Sheet").split(32, 32)[0];
		
		facingRight = true;
		flipSprite = false;
		
		// set init state
		currentState = PLAYERSTATE.IDLE;
		// set init animation
		setAnimation(idle, 1/ 12f);
	}
	
	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}
	
	public void setState(PLAYERSTATE state) {
		if (currentState != state) {
			currentState = state;
			setAnimation(state);
		} 
	}
	
	public void setAnimation(PLAYERSTATE state) {
		
		switch(state) {
			case CIGAR:
				setAnimation(cigar, 1/12f);
				break;
			case FALLING:
				setAnimation(falling, 1/2f);
				break;
			case IDLE:
				setAnimation(idle, 1/5f);
				break;
			case JUMPING:
				setAnimation(jump, 1/12f);
				break;
			case RUNNING:
				setAnimation(run, 1/12f);
				break;
			case WALKING:
				setAnimation(walk, 1/10f);
				break;
			default:
				break;
		
		}		
	}
	
	public void render(SpriteBatch sb) {
		float x = (int)(body.getPosition().x * B2dVars.PPM - width / 2);
		float y = (int) (body.getPosition().y * B2dVars.PPM - height / 2);
		
		sb.begin();
		//spriteBatch.draw(currentFrame, flip ? x+width : x, y, flip ? -width : width, height);

		sb.draw(animation.getFrame(), facingRight ? x : x + 32f, y , facingRight ? 32f : -32f, 32f);
		//sb.draw(animation.getFrame(), (int)(body.getPosition().x * B2dVars.PPM - width / 2), (int) (body.getPosition().y * B2dVars.PPM - height / 2));
		
		sb.end();
	}
}
