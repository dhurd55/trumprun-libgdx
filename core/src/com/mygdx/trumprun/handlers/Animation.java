package com.mygdx.trumprun.handlers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
	
	private TextureRegion[] frames;
	private int currentFrame;
	
	private float time;
	private float delay;
	
	private boolean playedOnce;
	private int timesPlayed;
	
	public Animation() {
		
		playedOnce = false;
		timesPlayed = 0;
	}
	
	public Animation(TextureRegion[] frames) {
		this(frames, 1 / 12f);
	}
	
	public Animation(TextureRegion[] frames, float delay) {
		setFrames(frames, delay);
	}
	
	public void setFrames(TextureRegion[] frames, float delay) {
		
		this.frames = frames;
		this.delay = delay;
		currentFrame = 0;   
		time = 0;
		timesPlayed = 0;
		
	}
	
	public void setDelay(long d) { delay = d; }
	public void setFrame(int i) { currentFrame = i; }
	public void setCount(int i ) { timesPlayed = i; }
	
	public void update(float dt) {
		
		if(delay <= 0) return;
		
		time += dt;
		if (time >= delay) {
			step();
		}
		
	}
	
	private void step() {
		time -= delay;
		currentFrame ++;
		if (currentFrame == frames.length) {
			currentFrame = 0;
			timesPlayed++;
		}
	}
	
	public TextureRegion getFrame() { return frames[currentFrame]; }
	public int getTimesPlayed() { return timesPlayed; }
}
