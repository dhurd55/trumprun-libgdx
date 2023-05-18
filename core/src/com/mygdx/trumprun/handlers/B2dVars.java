package com.mygdx.trumprun.handlers;

public class B2dVars {
	
	// pixel per meter ratio
	public static final float PPM = 100;
	
	// category bits
	public static final short CATEGORY_GROUND = 2;
	public static final short CATEGORY_PLAYER = 4;
	public static final short CATEGORY_OBJECT = 6;
	
	public final static short MASK_GROUND = -1;
	public final static short MASK_PLAYER = CATEGORY_GROUND | CATEGORY_OBJECT;
	public final static short MASK_OBJECT = CATEGORY_GROUND | CATEGORY_OBJECT;
	
}
