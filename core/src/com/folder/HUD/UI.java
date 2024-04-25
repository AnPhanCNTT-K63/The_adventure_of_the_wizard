package com.folder.HUD;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class UI extends Actor {

    private Texture texture;

    public UI(Texture texture, float x, float y, float width, float height) {
        this.texture = texture;
        setBounds(x, y, width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
