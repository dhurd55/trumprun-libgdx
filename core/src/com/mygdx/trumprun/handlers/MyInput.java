package com.mygdx.trumprun.handlers;

public class MyInput {
	public static final int NUM_KEYS = 16;
	
	public static boolean keys[] = new boolean[NUM_KEYS];
	public static boolean pkeys[] = new boolean[NUM_KEYS];
	
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public static final int BUTTON1 = 4;
	public static final int BUTTON2 = 5;
	public static final int ENTER = 6;
	public static final int ESCAPE = 7;
	
	/*
	public static void keySet(int i, boolean b) {
		if (i == KeyEvent.VK_UP) { keyState[UP] = b; }
		else if(i == KeyEvent.VK_LEFT) { keyState[LEFT] = b; }
		else if (i == KeyEvent.VK_DOWN) { keyState[DOWN] = b; }
		else if ( i == KeyEvent.VK_RIGHT) { keyState[RIGHT] = b; }
		else if ( i == KeyEvent.VK_A) { keyState[BUTTON1] = b; }
		else if ( i == KeyEvent.VK_F) { keyState[BUTTON2] = b; }
		else if (i == KeyEvent.VK_ENTER) { keyState[ENTER] = b; }
		else if (i == KeyEvent.VK_ESCAPE ) { keyState[ESCAPE] = b; }
	}
	*/
	
	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			pkeys[i] = keys[i];
		}
	}
	
	public static void setKey( int i, boolean b ) { keys[i] = b; }
	public static boolean isDown(int i ) { return keys[i]; }
	public static boolean isPressed(int i) {return keys[i] && !pkeys[i];}
	
	public static boolean anyKeyPress() {
		for (boolean key: keys) {
			if (key) return true;
		}
		return false;
	}
}
