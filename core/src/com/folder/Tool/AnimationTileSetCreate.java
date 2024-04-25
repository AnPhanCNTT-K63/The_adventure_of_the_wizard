package com.folder.Tool;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.folder.Screen.GameScreen;


public abstract class AnimationTileSetCreate extends Sprite {
    protected Animation<TextureRegion> animation;
    protected TextureRegion region;
    protected Array<TextureRegion> frames;
    protected float stateTime;
    protected GameScreen screen;
    protected boolean isExist;

    public AnimationTileSetCreate(GameScreen screen, float posX, float posY) {
        this.screen = screen;
        frames = new Array<TextureRegion>();
        stateTime = 0;
        isExist = true;
        create();
    }

    public abstract void create();

    public abstract void update(float deltaTime);

    @Override
    public void draw(Batch batch) {
        if (isExist) super.draw(batch);
    }

}
