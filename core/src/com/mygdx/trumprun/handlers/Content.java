package com.mygdx.trumprun.handlers;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class Content {
	
	private HashMap<String, Texture> textures;
	
	public Content() {
		textures = new HashMap<String, Texture>();
	}
	
	public void loadTexture(String path, String key) {}
	public Texture getTexture(String key) {return textures.get(key);}
	public void disposeTexture(String key) {
		Texture tex = textures.get(key);
		if (tex != null) tex.dispose();
	}
	
}