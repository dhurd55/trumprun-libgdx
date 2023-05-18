package com.mygdx.trumprun.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.trumprun.Game;
import com.mygdx.trumprun.handlers.B2dVars;

public class MagaProjectile extends B2dSprite {

	TextureRegion[] idle;
	
	public MagaProjectile(Body body) {
		super(body);
		
//		//load animation /s
//		idle = Game.projectileRes.getTextureRegion("MagaProjectile").split(16, 16)[0];
//
//		//set animation
//		setAnimation(idle, 1/ 7f);
	}
	
	public void render(SpriteBatch sb) {
//		float x = (int)(body.getPosition().x * B2dVars.PPM - width / 2);
//		float y = (int) (body.getPosition().y * B2dVars.PPM - height / 2);
//		
//		sb.begin();
//		
//		sb.draw(animation.getFrame(), x, y);
//		
//		sb.end();
	}

}
