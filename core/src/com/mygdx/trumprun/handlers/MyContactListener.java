package com.mygdx.trumprun.handlers;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements ContactListener {
	
	private int numFootContacts;
	private Array<Body> bodiesToRemove;
	Array<Fixture> fixturesToRemove;
	
	public MyContactListener() {
		super();
		bodiesToRemove = new Array<Body>();
		fixturesToRemove = new Array<Fixture>();
	}
	
	// called when two fixtures begin collision
	@Override
	public void beginContact(Contact contact) {
		
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		//Ground and foot interaction
		if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
			numFootContacts++;
			
		}
		if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
			numFootContacts++;
		}
		
		//Player interactions
		if(fa.getUserData() != null && fa.getUserData().equals("magaHat")) {
			if(fb.getUserData() != null && fb.getUserData().equals("player"))
				fixturesToRemove.add(fa);
		}
		
		if(fb.getUserData() != null && fb.getUserData().equals("magaHat")) {
			if(fa.getUserData() != null && fa.getUserData().equals("player"))
				fixturesToRemove.add(fb);
			
		}
		if(fa.getUserData() != null && fa.getUserData().equals("ballot")) {
			if(fb.getUserData() != null && fb.getUserData().equals("player"))

				fixturesToRemove.add(fa);
		}
		
		if(fb.getUserData() != null && fb.getUserData().equals("ballot")) {
			if(fa.getUserData() != null && fa.getUserData().equals("player"))
				fixturesToRemove.add(fb);
			
		}
		if(fa.getUserData() != null && fa.getUserData().equals("money")) {
			if(fb.getUserData() != null && fb.getUserData().equals("player"))
				fixturesToRemove.add(fa);
		}
		
		if(fb.getUserData() != null && fb.getUserData().equals("money")) {
			if(fa.getUserData() != null && fa.getUserData().equals("player"))
				fixturesToRemove.add(fb);
			
		}
		
	}

	// called when two colliding fixtures end collision
	@Override
	public void endContact(Contact contact) {
		
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		//Ground and player interaction
		if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
			numFootContacts--;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
			numFootContacts--;
			
		}
	}
	
	public Array<Body> getBodies() { return bodiesToRemove; }	
	public Array<Fixture> getFixtures() { return fixturesToRemove; }
	
	public boolean isPlayerGrounded() {
		return numFootContacts > 0;
	}
	
	
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
