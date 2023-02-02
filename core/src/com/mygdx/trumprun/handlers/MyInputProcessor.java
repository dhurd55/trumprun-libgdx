package com.mygdx.trumprun.handlers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class MyInputProcessor extends InputAdapter {
	
	public boolean keyDown(int k) {
		if (k == Keys.UP) { MyInput.setKey(MyInput.UP, true);}
		else if(k == Keys.LEFT) { MyInput.setKey(MyInput.LEFT, true);}
		else if (k == Keys.DOWN) { MyInput.setKey(MyInput.DOWN, true); }
		else if ( k == Keys.RIGHT) { MyInput.setKey(MyInput.RIGHT, true); }
		else if ( k == Keys.A) { MyInput.setKey(MyInput.BUTTON1, true); }
		else if ( k == Keys.F) { MyInput.setKey(MyInput.BUTTON2, true);}
		else if (k == Keys.ENTER) { MyInput.setKey(MyInput.ENTER, true);}
		else if (k == Keys.ESCAPE ) { MyInput.setKey(MyInput.ESCAPE, true);}
		return true;
	}
	 
	public boolean keyUp(int k) {
		if (k == Keys.UP) { MyInput.setKey(MyInput.UP, false);}
		else if(k == Keys.LEFT) { MyInput.setKey(MyInput.LEFT, false);}
		else if (k == Keys.DOWN) { MyInput.setKey(MyInput.DOWN, false); }
		else if ( k == Keys.RIGHT) { MyInput.setKey(MyInput.RIGHT, false); }
		else if ( k == Keys.A) { MyInput.setKey(MyInput.BUTTON1, false); }
		else if ( k == Keys.F) { MyInput.setKey(MyInput.BUTTON2, false);}
		else if (k == Keys.ENTER) { MyInput.setKey(MyInput.ENTER, false);}
		else if (k == Keys.ESCAPE ) { MyInput.setKey(MyInput.ESCAPE, false);}
		return true;
	}
}
