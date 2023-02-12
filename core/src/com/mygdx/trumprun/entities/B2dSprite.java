package com.mygdx.trumprun.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.trumprun.handlers.Animation;
import com.mygdx.trumprun.handlers.B2dVars;

public class B2dSprite {
	// TODO: add numframes so frames dont flash due to blank space
	protected Body body;
	protected Animation animation;
	protected float width;
	protected float height;
	
	public B2dSprite(Body body) {
		this.body = body;
		animation = new Animation();
	}
	
	public void setAnimation(TextureRegion[] reg, float delay) {
		animation.setFrames(reg, delay);
		width = reg[0].getRegionWidth();
		height = reg[0].getRegionHeight();
	}
	
	public void update(float dt) {
		animation.update(dt);
	}
	
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(animation.getFrame(), (int)(body.getPosition().x * B2dVars.PPM - width / 2), (int) (body.getPosition().y * B2dVars.PPM - height / 2));
		sb.end();
	}
	
	public Body getBody() { return body; }
	public Vector2 getPositon() { return body.getPosition(); }
	public float getWidth() { return width; }
	public float getHeight() {return height; }
	
}
