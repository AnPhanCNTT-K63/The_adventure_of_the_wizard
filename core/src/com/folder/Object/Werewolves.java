package com.folder.Object;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.folder.Boot;
import com.folder.GameScreen;

public class Werewolves extends Sprite {
    enum STATE {IDLE, WALK, ATTACK}

    STATE currentState;
    STATE previousState;

    World world;

    Body body;

    Animation<TextureRegion> Idle;
    Animation<TextureRegion> Walk;
    Animation<TextureRegion> Attack;

    float stateTime;

    boolean isWalking;
    boolean isAttacking;

    boolean isTurningRight;

    public Werewolves(GameScreen screen) {
        world = screen.getWorld();

        currentState = previousState = STATE.IDLE;

        isWalking = false;
        isAttacking = false;

        isTurningRight = true;

        stateTime = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getEnemyAtlas().findRegion("Idle"), i * 128, 0, 128, 128));
        Idle = new Animation<TextureRegion>(1 / 8f, frames);
        frames.clear();
        setBounds(0, 0, 128 / Boot.PPM, 128 / Boot.PPM);

        for (int i = 0; i < 11; i++)
            frames.add(new TextureRegion(screen.getEnemyAtlas().findRegion("walk"), i * 128, 0, 128, 128));
        Walk = new Animation<TextureRegion>(1 / 11f, frames);
        frames.clear();

        WerewolvesDef();
    }

    public void WerewolvesDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Boot.screenWidth / 2 / Boot.PPM, Boot.screenHeight / 2 / Boot.PPM);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2, getHeight() / 2 - 34 / Boot.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Boot.ENEMY_BIT;
        fixtureDef.filter.maskBits = Boot.GROUND_BIT;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public TextureRegion getStatus(float deltaTime) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case WALK:
                region = Walk.getKeyFrame(stateTime, true);
                break;
            default:
                region = Idle.getKeyFrame(stateTime, true);
                break;
        }

        if (!isTurningRight && !region.isFlipX())
            region.flip(true, false);
        else if (isTurningRight && region.isFlipX())
            region.flip(true, false);

        stateTime = currentState == previousState ? stateTime + deltaTime : 0;
        previousState = currentState;

        return region;
    }

    public STATE getState() {
        if (isWalking)
            return STATE.WALK;
        if (isAttacking)
            return STATE.ATTACK;
        else return STATE.IDLE;
    }

    public void update(float deltaTime) {
        if (isTurningRight)
            setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2, 128 / Boot.PPM, 128 / Boot.PPM);
        else
            setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2, 128 / Boot.PPM, 128 / Boot.PPM);

        setRegion(getStatus(deltaTime));
    }
}
