package com.mygdx.trumprun.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.trumprun.handlers.B2dVars;
import com.mygdx.trumprun.Game;

public class HUD {
	
	private Player player;
	
	//private TextureRegion[] hudNumbers;
	private TextureRegion[] hudMoney;
	private TextureRegion[] hudBallot;
	private TextureRegion[] hudHeart;
	private TextureRegion[] hudMagaHat;
	
	public HUD(Player player) {
		
		this.player = player;
		
		//hudNumbers = Game.hudRes.getTextureRegion("Trump_CIGAR-Sheet").split(16,16)[0];
		hudMoney = Game.hudRes.getTextureRegion("HudMoney").split(16,16)[0];
		hudBallot = Game.hudRes.getTextureRegion("hudBallot").split(16,16)[0];
		hudHeart = Game.hudRes.getTextureRegion("hudHeart").split(16,16)[0];
		hudMagaHat = Game.hudRes.getTextureRegion("hudMagaHat").split(16,16)[0];
		
	}
	
	public void render(SpriteBatch sb) {
		
		sb.begin();
		
		sb.draw(hudMoney[0], 40, 200);
		sb.draw(hudBallot[0], 60, 200);
		sb.draw(hudHeart[0], 80, 200);
		sb.draw(hudMagaHat[0], 100, 200);


		
		// draw blocks
//		short bits = player.getBody().getFixtureList().first().getFilterData().maskBits;
//		if((bits & B2DVars.BIT_RED_BLOCK) != 0) {
//			sb.draw(blocks[0], 40, 208);
//		}
//		else if((bits & B2DVars.BIT_GREEN_BLOCK) != 0) {
//			sb.draw(blocks[1], 40, 208);
//		}
//		else if((bits & B2DVars.BIT_BLUE_BLOCK) != 0) {
//			sb.draw(blocks[2], 40, 208);
//		}
//		
//		// draw crystal
//		sb.draw(crystal, 100, 208);
//		
//		// draw crystal amount
//		drawString(sb, player.getNumCrystals() + " / " + player.getTotalCrystals(), 132, 211);
		
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
