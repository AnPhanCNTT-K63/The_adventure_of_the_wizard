package com.folder.Object;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.folder.GameScreen;

public abstract class AnimationTileSet extends Sprite {
    protected Animation<TextureRegion> animation;
    protected TextureRegion region;
    protected Array<TextureRegion> frames;
    protected float stateTime;
    protected GameScreen screen;

    public AnimationTileSet(GameScreen screen, float posX, float posY) {
        this.screen = screen;
        frames = new Array<TextureRegion>();
        stateTime = 0;
        create();
    }

    abstract void create();

    public abstract void update(float deltaTime);

}
