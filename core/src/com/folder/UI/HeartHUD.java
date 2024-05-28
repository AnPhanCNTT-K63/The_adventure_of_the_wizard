package com.folder.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.folder.Boot;
import com.folder.Object.MainCharacter;
import com.folder.Screen.GameScreen;

import java.util.LinkedList;

public class HeartHUD extends Actor {

    private LinkedList<Texture> textures;

    public HeartHUD() {
        textures = new LinkedList<>();
        for (int i = 0; i < 7; i++)
            textures.add(new Texture("SpriteSheet/heart.png"));
        setBounds(0, 0, 128, 128);
    }

    public void update() {
        if (MainCharacter.isHurt) {
            removeHeart();
        }
    }

    public void removeHeart() {
        if (!textures.isEmpty()) {
            textures.removeLast().dispose();

        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = 0; i < textures.size(); i++) {
            batch.draw(textures.get(i), 50 + i * 120, 930, getWidth(), getHeight());
        }
    }

    public void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
    }

}
