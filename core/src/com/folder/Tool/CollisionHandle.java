package com.folder.Tool;

import com.badlogic.gdx.physics.box2d.*;
import com.folder.Boot;
import com.folder.Object.HangPoint;
import com.folder.Object.MainCharacter;
import com.folder.Object.Werewolves;

public class CollisionHandle implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int fixC = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (fixC) {
            case Boot.CHARACTER_BIT | Boot.GROUND_BIT:
                MainCharacter.isOnAir = false;
                MainCharacter.isFalling = false;
                MainCharacter.isJumping = false;
                MainCharacter.isTouchAirGround = false;
                break;
            case Boot.CHARACTER_BIT | Boot.TOUCH_POINT_ON_AIR:
                MainCharacter.isFalling = true;
                MainCharacter.isTouchAirGround = true;
                break;
            case Boot.ATTACK_BIT | Boot.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Boot.ENEMY_BIT)
                    ((Werewolves) fixA.getUserData()).beDamaged();
                else if (fixB.getFilterData().categoryBits == Boot.ENEMY_BIT)
                    ((Werewolves) fixB.getUserData()).beDamaged();
                break;
            case Boot.ENEMY_ATTACK_BIT | Boot.CHARACTER_BIT:
                MainCharacter.beDamaged();
                break;
            case Boot.CHARACTER_BIT | Boot.ENEMY_BIT:
            case Boot.CHARACTER_BIT | Boot.WALL_BIT:
                break;
            case Boot.ENEMY_BIT | Boot.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Boot.ENEMY_BIT)
                    ((Werewolves) fixA.getUserData()).reverseVelocity();
                else if (fixB.getFilterData().categoryBits == Boot.ENEMY_BIT)
                    ((Werewolves) fixB.getUserData()).reverseVelocity();
                break;
            case Boot.CHARACTER_BIT | Boot.HANG_POINT_BIT:
                if (fixA.getFilterData().categoryBits == Boot.HANG_POINT_BIT)
                    ((HangPoint) fixA.getUserData()).setPositionHang();
                else
                    ((HangPoint) fixB.getUserData()).setPositionHang();
                MainCharacter.isHanging = true;
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
