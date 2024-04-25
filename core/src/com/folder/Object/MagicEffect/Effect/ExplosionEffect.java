package com.folder.Object.MagicEffect.Effect;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.folder.Boot;
import com.folder.Object.MagicEffect.MagicEffect;
import com.folder.Screen.GameScreen;

public class ExplosionEffect extends MagicEffect {

    public ExplosionEffect(GameScreen screen) {
        super(screen);

        setBounds(0, 0, 72 / Boot.PPM, 72 / Boot.PPM);

        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(screen.getEffectAtlas().findRegion("Explosion"), i * 72, 0, 72, 72));
        animation = new Animation<TextureRegion>(1 /20f, frames);
        frames.clear();
    }

    @Override
    public void update(float deltaTime, float posX, float posY) {
        stateTime += deltaTime;
        setBounds(posX - getWidth() / 2, posY - getHeight() / 2, 120 / Boot.PPM, 120 / Boot.PPM);

        TextureRegion region;
        region = animation.getKeyFrame(stateTime, true);
        setRegion(region);
    }
}
