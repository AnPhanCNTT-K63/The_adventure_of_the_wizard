package com.folder.Object.MagicEffect;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.folder.Screen.GameScreen;


public abstract class MagicEffect extends Sprite {
    protected Animation<TextureRegion> animation;
    protected float stateTime;
    protected float actionDuration;

    public MagicEffect(GameScreen screen) {
        actionDuration = 0;
        stateTime = 0;
    }

    abstract public void update(float deltaTime, float posX, float posY);

    public float getActionDuration() {
        return actionDuration;
    }

    public void setActionDuration(float time) {
        actionDuration = time;
    }

    public void updateActionDuration(float time) {
        actionDuration += time;
    }
}
