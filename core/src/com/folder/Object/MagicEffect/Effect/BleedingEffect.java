package com.folder.Object.MagicEffect.Effect;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.folder.Boot;

import com.folder.Object.MagicEffect.MagicEffect;
import com.folder.Screen.GameScreen;

public class BleedingEffect extends MagicEffect {

    public BleedingEffect(GameScreen screen) {
        super(screen);

        Array<TextureRegion> frames = new Array<>();
        setBounds(0, 0, 512 / Boot.PPM, 512 / Boot.PPM);
        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getEffectAtlas().findRegion("bleed"), i * 512, 0, 512, 512));
        animation = new Animation<TextureRegion>(1 / 12f, frames);
        frames.clear();
    }

    @Override
    public void update(float deltaTime, float x, float y) {
        stateTime += deltaTime;
        setBounds(x - getWidth() / 2, y - getHeight() / 2, 208 / Boot.PPM, 208 / Boot.PPM);

        TextureRegion region;
        region = animation.getKeyFrame(stateTime, true);
        setRegion(region);
    }

}
