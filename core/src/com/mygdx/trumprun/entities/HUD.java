package com.mygdx.trumprun.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.trumprun.handlers.B2dVars;
import com.mygdx.trumprun.Game;

public class HUD {
	
	private Player player;
	
	private BitmapFont font;
	
	//private TextureRegion[] hudNumbers;
	private TextureRegion[] hudMoney;
	private TextureRegion[] hudBallot;
	private TextureRegion[] hudHeart;
	private TextureRegion[] hudMagaHat;
	
	public HUD(Player player) {
		
		this.player = player;
		font = new BitmapFont();
		
		//hudNumbers = Game.hudRes.getTextureRegion("Trump_CIGAR-Sheet").split(16,16)[0];
		hudMoney = Game.hudRes.getTextureRegion("HudMoney").split(16,16)[0];
		hudBallot = Game.hudRes.getTextureRegion("hudBallot").split(16,16)[0];
		hudHeart = Game.hudRes.getTextureRegion("hudHeart").split(16,16)[0];
		hudMagaHat = Game.hudRes.getTextureRegion("hudMagaHat").split(16,16)[0];
		
	}
	
	public void render(SpriteBatch sb) {
		
		sb.begin();
		
		// draw hearts
		for (int i = 0; i < player.getHealth(); i++) {
			sb.draw(hudHeart[0], 10 +(i*12), 220);
		}
		// draw Hats
		for (int i = 0; i < player.getNumMagaProjectiles(); i++	) {
			sb.draw(hudMagaHat[0], 10 + (i*10), 200);
		}
		
		// money and value
		sb.draw(hudMoney[0], 10, 180);
		font.draw(sb, "$ " + Integer.toString(player.getNumMoney()), 30, 195);

		sb.draw(hudBallot[0], 10, 160);
		font.draw(sb, "x " + Integer.toString(player.getNumBallots()), 30, 175);
		
		sb.end();
		
	}
	
	private void drawString(SpriteBatch sb, String s, float x, float y) {
//		for(int i = 0; i < s.length(); i++) {
//			char c = s.charAt(i);
//			if(c == '/') c = 10;
//			else if(c >= '0' && c <= '9') c -= '0';
//			else continue;
//			sb.draw(font[c], x + i * 9, y);
//		}
	}
	
}
