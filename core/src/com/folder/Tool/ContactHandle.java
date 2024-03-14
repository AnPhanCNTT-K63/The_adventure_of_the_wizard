package com.folder.Tool;

import com.badlogic.gdx.physics.box2d.*;
import com.folder.Boot;
import com.folder.Object.MainCharacter;

public class ContactHandle implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int fixC = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (fixC) {
            case Boot.CHARACTER_BIT | Boot.GROUND_BIT:
                MainCharacter.isFalling = false;
                MainCharacter.isJumping = false;
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


}
