package com.folder.Object.Enemy;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.folder.Boot;
import com.folder.Object.MainCharacter;
import com.folder.Screen.GameScreen;


public abstract class Enemy extends Sprite {
    protected World world;
    protected Body body;
    protected Vector2 velocity;
    protected float hitCount;
    protected Light enemyLight;
    protected RayHandler rayHandler;

    public Enemy(GameScreen screen, float posX, float posY) {
        world = screen.getWorld();
        velocity = new Vector2(0, 0);
        hitCount = 0;
        setPosition(posX, posY);
        setUpBody();

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0, 0, 0, 0.2f);

        enemyLight = new PointLight(rayHandler, 64, new Color(1, 1, 1, 0.7f), 4, body.getPosition().x, body.getPosition().y);
        enemyLight.attachToBody(body);
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
