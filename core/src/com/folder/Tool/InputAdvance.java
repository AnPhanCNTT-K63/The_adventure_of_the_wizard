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
        if (keycode == Input.Keys.W) {
            MainCharacter.isLooking = true;
            MainCharacter.isLookingUp = true;
        }
        if (keycode == Input.Keys.S) {
            MainCharacter.isLooking = true;
            MainCharacter.isLookingUp = false;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A || keycode == Input.Keys.D) {
            MainCharacter.isMoving = false;
            MainCharacter.canMove = true;
        }
        if (keycode == Input.Keys.SPACE) {
            MainCharacter.isJumping = false;
            MainCharacter.isFalling = true;
        }
        if (keycode == Input.Keys.Q) {
            MainCharacter.isAttack = false;
        }
        if (keycode == Input.Keys.W || keycode == Input.Keys.S) {
            MainCharacter.isLooking = false;
            MainCharacter.isReturn = true;
        }
        return true;
    }

}
