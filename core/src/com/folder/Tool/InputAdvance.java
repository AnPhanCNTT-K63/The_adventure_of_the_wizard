package com.folder.Tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.folder.Boot;
import com.folder.GameScreen;
import com.folder.Object.MainCharacter;

public class InputAdvance extends InputAdapter {

    @Override
    public boolean keyDown(int keycode) {
        //MainCharacter.stateTime = 0;
        if (keycode == Input.Keys.W) {
            MainCharacter.isReturn = false;
            MainCharacter.isLooking = true;
            MainCharacter.isLookingUp = true;
        }
        if (keycode == Input.Keys.S) {
            MainCharacter.isReturn = false;
            MainCharacter.isLooking = true;
            MainCharacter.isLookingUp = false;
        }
        if (keycode == Input.Keys.SPACE && MainCharacter.isAllowedJumping) {
            MainCharacter.isJumping = true;
            MainCharacter.body.applyLinearImpulse(new Vector2(0, 5f), MainCharacter.body.getWorldCenter(), true);
            MainCharacter.isAllowedJumping = false;
        }
        if (keycode == Input.Keys.Q) {
            MainCharacter.isAttacking_Flame = true;
        }
        if (keycode == Input.Keys.J)
            MainCharacter.isAttacking_Normal = true;
        if (keycode == Input.Keys.K)
            MainCharacter.isAttacking_heavy = true;

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A || keycode == Input.Keys.D) {
            MainCharacter.isMoving = false;
            MainCharacter.canMove = true;
        }
        if (keycode == Input.Keys.SPACE) {
            MainCharacter.stateTime = 0;
            MainCharacter.isJumping = false;
            MainCharacter.isFalling = true;
            MainCharacter.isAllowedJumping = false;
        }

        if (keycode == Input.Keys.W || keycode == Input.Keys.S) {
            MainCharacter.isLooking = false;
            MainCharacter.isReturn = true;
        }

        if (keycode == Input.Keys.J)
            MainCharacter.isAttacking_Normal = false;
        if (keycode == Input.Keys.K)
            MainCharacter.isAttacking_heavy = false;

        return true;
    }

}
