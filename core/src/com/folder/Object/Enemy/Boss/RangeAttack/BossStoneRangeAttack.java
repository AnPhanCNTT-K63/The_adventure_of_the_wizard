package com.folder.Object.Enemy.Boss;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.folder.Boot;
import com.folder.Screen.GameScreen;


import java.util.Scanner;

public class BossStoneRangeAttack extends Sprite {
    private Body body;
    private World world;

    private Animation<TextureRegion> animation;
    private Array<TextureRegion> frames;

    private float stateTime;
    private float actionDuration;

    private float posX;
    private float posY;
    private Vector2 velocity;

    private short categoryBit;

    private boolean destroy;
    private boolean setToDestroy;
    private boolean ready;

    public BossStoneRangeAttack(GameScreen screen, float posX, float posY, short categoryBit) {
        world = screen.getWorld();
        velocity = new Vector2();

        this.posX = posX;
        this.posY = posY;
        destroy = false;
        setToDestroy = false;
        ready = false;
        this.categoryBit = categoryBit;

        stateTime = 0;
        actionDuration = 0;

        frames = new Array<>();
        for (int i = 8; i < 11; i++)
            frames.add(new TextureRegion(screen.getEffectAtlas2().findRegion("tornado"), i * 128, 0, 128, 128));
        animation = new Animation<TextureRegion>(1 / 4f, frames);
    }

    public void defineBody() {
        setBounds(posX, posY, 300 / Boot.INSTANCE.getPPM(), 300 / Boot.INSTANCE.getPPM());
        velocity.set(5, 0);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX(), getY());
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 7, getHeight() / 10);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = categoryBit;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void update(float deltaTime) {

        if (ready) {
            defineBody();
            ready = false;
        }

        TextureRegion region;
        stateTime += deltaTime;
        region = animation.getKeyFrame(stateTime, true);
        setRegion(region);
        body.setTransform(6 * stateTime, 100 / Boot.INSTANCE.getPPM(), 0);
        setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 25 / Boot.INSTANCE.getPPM(), 300 / Boot.INSTANCE.getPPM(), 300 / Boot.INSTANCE.getPPM());
    }

    public void destroyBody() {
        setToDestroy = true;
        if (!destroy) {
            world.destroyBody(body);
            destroy = true;
        }
    }

    public void setReady(boolean bool) {
        ready = bool;
    }

    public float getActionDuration() {
        return actionDuration;
    }

    public void setActionDuration(float time) {
        actionDuration = time;
    }

    public void updateActionDuration(float time){
        actionDuration += time;
    }

}
