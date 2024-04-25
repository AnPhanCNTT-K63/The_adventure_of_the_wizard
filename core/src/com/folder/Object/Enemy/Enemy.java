package com.folder.Object.Enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.folder.Boot;
import com.folder.Screen.GameScreen;


public abstract class Enemy extends Sprite {
    protected World world;
    protected Body body;
    protected Vector2 velocity;
    protected float hitCount;

    public Enemy(GameScreen screen, float posX, float posY) {
        world = screen.getWorld();
        velocity = new Vector2(0, 0);
        hitCount = 0;
        setPosition(posX, posY);
        setUpBody();
    }

    protected abstract void setUpBody();

    public abstract void beDamaged();

    public abstract void reverseVelocity();

    public abstract void update(float deltaTime);

    public abstract void destroyWhenNextMap();

    public void increaseHitCount() {
        hitCount++;
    }

    public float getHitCount() {
        return hitCount;
    }

    public float getPosX() {
        return body.getPosition().x;
    }

    public float getPosY() {
        return body.getPosition().y;
    }
}
