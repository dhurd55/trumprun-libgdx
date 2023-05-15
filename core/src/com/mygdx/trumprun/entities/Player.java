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
	
	TextureRegion[] cigarMaga;
	TextureRegion[] fallingMaga;
	TextureRegion[] idleMaga;
	TextureRegion[] jumpMaga;
	TextureRegion[] runMaga;
	TextureRegion[] walkMaga;
	
	PLAYERSTATE currentState;
	// player stuff
	
	boolean facingRight;
	boolean flipSprite;
	
	boolean isMaga;
	public int numMagaHats;
	
	public Player(Body body) {
		super (body);
		
		//////////////  Animation  \\\\\\\\\\\\\\
		
		
		cigar = Game.playerRes.getTextureRegion("cigar").split(32, 32)[0];
		falling = Game.playerRes.getTextureRegion("falling").split(32, 32)[0];
		idle = Game.playerRes.getTextureRegion("idle").split(32, 32)[0];
		jump = Game.playerRes.getTextureRegion("jump").split(32, 32)[0];
		run = Game.playerRes.getTextureRegion("running").split(32, 32)[0];
		walk = Game.playerRes.getTextureRegion("walking").split(32, 32)[0];
		
		cigarMaga = Game.playerRes.getTextureRegion("cigarMaga").split(32, 32)[0];
		fallingMaga = Game.playerRes.getTextureRegion("fallingMaga").split(32, 32)[0];
		idleMaga = Game.playerRes.getTextureRegion("idleMaga").split(32, 32)[0];
		jumpMaga = Game.playerRes.getTextureRegion("jumpMaga").split(32, 32)[0];
		runMaga = Game.playerRes.getTextureRegion("runningMaga").split(32, 32)[0];
		walkMaga = Game.playerRes.getTextureRegion("walkingMaga").split(32, 32)[0];
		
		facingRight = true;
		flipSprite = false;
		isMaga = false;
		
		numMagaHats = 0;
		
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
		
		if (numMagaHats > 0) {
			switch(state) {
				case CIGAR:
					setAnimation(cigarMaga, 1/12f);
					break;
				case FALLING:
					setAnimation(fallingMaga, 1/2f);
					break;
				case IDLE:
					setAnimation(idleMaga, 1/5f);
					break;
				case JUMPING:
					setAnimation(jumpMaga, 1/12f);
					break;
				case RUNNING:
					setAnimation(runMaga, 1/12f);
					break;
				case WALKING:
					setAnimation(walkMaga, 1/10f);
					break;
				default:
					break;
		
		}
			
		}
		else {
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
	}
	
	public void setMagaHats(int numMagaHats) {
		this.numMagaHats = numMagaHats;
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
