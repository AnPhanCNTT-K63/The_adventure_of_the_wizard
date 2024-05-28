package com.folder.Tool;

import com.badlogic.gdx.physics.box2d.*;
import com.folder.Boot;
import com.folder.Object.Enemy.Enemy;
import com.folder.Object.InteractiveObject.HangPoint;
import com.folder.Object.MainCharacter;

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
//            case Boot.CHARACTER_BIT | Boot.ENEMY_BIT:
//                MainCharacter.beDamaged();
//                //MainCharacter.isHeavyHurt = true;
//                if (fixA.getFilterData().categoryBits == Boot.CHARACTER_BIT)
//                    ((MainCharacter) fixA.getUserData()).collideEnemy((Enemy) fixB.getUserData());
//                else
//                    ((MainCharacter) fixB.getUserData()).collideEnemy((Enemy) fixA.getUserData());
//                break;
            case Boot.ATTACK_BIT | Boot.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Boot.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).beDamaged();
                    ((Enemy) fixA.getUserData()).increaseHitCount();
                } else if (fixB.getFilterData().categoryBits == Boot.ENEMY_BIT) {
                    ((Enemy) fixB.getUserData()).beDamaged();
                    ((Enemy) fixB.getUserData()).increaseHitCount();
                }
                break;
            case Boot.ENEMY_ATTACK_BIT | Boot.CHARACTER_BIT:
                MainCharacter.beDamaged();
                if (fixA.getFilterData().categoryBits == Boot.CHARACTER_BIT)
                    ((MainCharacter) fixA.getUserData()).beStrongPushed((Enemy) fixB.getUserData());
                else
                    ((MainCharacter) fixB.getUserData()).beStrongPushed((Enemy) fixA.getUserData());
                break;
            case Boot.CHARACTER_BIT | Boot.WALL_BIT:
                break;
            case Boot.ENEMY_BIT | Boot.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Boot.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity();
                else if (fixB.getFilterData().categoryBits == Boot.ENEMY_BIT)
                    ((Enemy) fixB.getUserData()).reverseVelocity();
                break;
            case Boot.CHARACTER_BIT | Boot.HANG_POINT_BIT:
                if (fixA.getFilterData().categoryBits == Boot.HANG_POINT_BIT)
                    ((HangPoint) fixA.getUserData()).setPositionHang();
                else
                    ((HangPoint) fixB.getUserData()).setPositionHang();
                MainCharacter.isHanging = true;
                break;
            case Boot.CHARACTER_BIT | Boot.LADDER_POINT_BIT:
                MainCharacter.isClingLadder = true;
                break;
            case Boot.CHARACTER_BIT | Boot.DOOR_BIT:
                MainCharacter.isAllowedNextMap = true;
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int fixC = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (fixC) {
            case Boot.CHARACTER_BIT | Boot.LADDER_POINT_BIT:
                MainCharacter.isClingLadder = false;
                break;
            case Boot.CHARACTER_BIT | Boot.GROUND_BIT:
                MainCharacter.isOnAir = true;
                break;

        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


}
