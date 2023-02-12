package com.mygdx.trumprun.handlers;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Content {
	
	private HashMap<String, Texture> textures;
	private TextureAtlas atlas;
	
	public Content() {
		textures = new HashMap<String, Texture>();
	}
	
	public void loadTextureAtlas(String path) {
		atlas = new TextureAtlas(path);
	}
	
	public void loadTexture(String path, String key) {
		Texture tex = new Texture(path);
		this.textures.put(key, tex);
	}
	
	public TextureRegion getTextureRegion(String region) {
		return atlas.findRegion(region);
	}
	
	public Texture getTexture(String key) {return textures.get(key);}
	
	public void disposeTexture(String key) {
		Texture tex = textures.get(key);
		if (tex != null) tex.dispose();
	}
	
}
