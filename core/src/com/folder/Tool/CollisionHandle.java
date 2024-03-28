package com.folder.Tool;

import com.badlogic.gdx.physics.box2d.*;
import com.folder.Boot;
import com.folder.Object.MainCharacter;
import com.folder.Object.Werewolves;
import jdk.tools.jmod.Main;

public class CollisionHandle implements ContactListener {
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
            case Boot.ATTACK_BIT | Boot.ENEMY_BIT:
                Werewolves.beDamaged();
                System.out.println("ouch");
                break;
            case Boot.ENEMY_ATTACK_BIT | Boot.CHARACTER_BIT:
                MainCharacter.beDamaged();
                break;
            case Boot.CHARACTER_BIT | Boot.ENEMY_BIT:
                break;
            case Boot.ENEMY_BIT | Boot.WALL_BIT:
                Werewolves.isReverse = true;
                break;
            case Boot.CHARACTER_BIT | Boot.WALL_BIT:
                Werewolves.isReverse = true;
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
