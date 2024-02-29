package com.folder.Object;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.folder.Boot;
import com.folder.GameScreen;
import com.folder.Tool.InputAdvance;

public class MainCharacter extends Sprite {
    enum STATE {IDLE, RUN, JUMP, FALL, ATTACK, LOOK_UP, LOOK_DOWN}

    World world;
    static public Body body;

    STATE previousState;
    STATE currenState;

    Animation<TextureRegion> idle;
    Animation<TextureRegion> run;
    Animation<TextureRegion> flameAttack;
    Animation<TextureRegion> jump;
    Animation<TextureRegion> fall;
    TextureRegion attack;
    TextureRegion lookUp;
    TextureRegion lookDown;

    static public float stateTime;

    //State handle
    static public boolean isTurningRight;
    static public boolean isMoving;
    static public boolean isJumping;
    static public boolean isAttacking;
    static public boolean isLooking;
    static public boolean isLookingUp;
    static public boolean isReturn;
    static public boolean isFalling;

    //Character handle
    static public boolean canMove;
    static public boolean isAllowedJumping;

    public MainCharacter(GameScreen screen) {
        world = screen.getWorld();

        isTurningRight = true;
        isJumping = false;
        isMoving = false;
        isAttacking = false;
        isLooking = false;
        isLookingUp = false;
        isReturn = false;
        isFalling = false;
        isAllowedJumping = false;
        canMove = true;

        currenState = previousState = STATE.IDLE;
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 7; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Idle"), i * 128 + 16, 48, 64, 80));
        idle = new Animation<TextureRegion>(1 / 3f, frames);
        setBounds(0, 0, 64 / Boot.PPM, 80 / Boot.PPM);
        frames.clear();

        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Run"), i * 128 + 16, 48, 64, 80));
        run = new Animation<TextureRegion>(1 / 8f, frames);
        frames.clear();

        for (int i = 0; i < 14; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Flame_jet"), i * 128, 48, 128, 80));
        flameAttack = new Animation<TextureRegion>(1 / 14f, frames);
        frames.clear();

        for (int i = 3; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jump"), i * 128 + 16, 48, 64, 80));
        jump = new Animation<TextureRegion>(1 / 4f, frames);
        frames.clear();

        for (int i = 4; i < 6; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jump"), i * 128 + 16, 48, 64, 80));
        fall = new Animation<TextureRegion>(1 / 4f, frames);
        frames.clear();

        attack = new TextureRegion(screen.getAtlas().findRegion("Flame_jet"), 7 * 128, 48, 128, 80);

        lookUp = new TextureRegion(screen.getAtlas().findRegion("Idle"), 16, 48, 64, 80);

        lookDown = new TextureRegion(screen.getAtlas().findRegion("Idle"), 16, 48, 64, 80);

        MainCharacterDef();

    }

    public void MainCharacterDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Boot.screenWidth / 2 / Boot.PPM, Boot.screenHeight / 2 / Boot.PPM);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2, getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Boot.CHARACTER_BIT;
        fixtureDef.filter.maskBits = Boot.GROUND_BIT;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public TextureRegion getStatus(float deltaTime) {
        currenState = getState();
        TextureRegion region;
        switch (currenState) {
            case RUN:
                region = run.getKeyFrame(stateTime, true);
                break;
            case JUMP:
                region = jump.getKeyFrame(stateTime);
                break;
            case ATTACK:
                region = flameAttack.getKeyFrame(stateTime);
                break;
            case LOOK_UP:
                region = lookUp;
                break;
            case LOOK_DOWN:
                region = lookDown;
                break;
            case FALL:
                region = fall.getKeyFrame(stateTime);
                break;
            default:
                region = idle.getKeyFrame(stateTime, true);
                break;
        }

        if (!isTurningRight && !region.isFlipX()) {
            region.flip(true, false);
        } else if (isTurningRight && region.isFlipX()) {
            region.flip(true, false);
        }

        stateTime = currenState == previousState ? stateTime + deltaTime : 0;
        previousState = currenState;

        return region;
    }

    public STATE getState() {
        if (isMoving) {
            return STATE.RUN;
        } else if (isJumping) return STATE.JUMP;
        else if (isAttacking) return STATE.ATTACK;
        else if (isLookingUp) return STATE.LOOK_UP;
        else if (isLooking) return STATE.LOOK_DOWN;
        else if (isFalling) return STATE.FALL;
        else return STATE.IDLE;
    }

    public void inputHandle() {
        Gdx.input.setInputProcessor(new InputAdvance());
        float posX = body.getPosition().x;
        float posY = body.getPosition().y;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            isMoving = true;
            isTurningRight = true;
            if (canMove)
                posX += (float) (10.5 / Boot.PPM);
        } else if (Gdx.input.isKeyPressed((Input.Keys.A))) {
            isMoving = true;
            isTurningRight = false;
            if (canMove)
                posX += (float) (-10.5 / Boot.PPM);
        }

        isAllowedJumping = !isFalling;
        if (isLooking && stateTime >= 0.7f) {
            if (isLookingUp)
                GameScreen.camera.position.y += 2.5f / Boot.PPM;
            else GameScreen.camera.position.y -= 2.5f / Boot.PPM;
            if (GameScreen.camera.position.y >= 450 / Boot.PPM)
                GameScreen.camera.position.y = 450 / Boot.PPM;
            else if (GameScreen.camera.position.y <= 270 / Boot.PPM)
                GameScreen.camera.position.y = 270 / Boot.PPM;
        }

        if (isReturn) {
            if (GameScreen.camera.position.y >= 360 / Boot.PPM) {
                GameScreen.camera.position.y -= 2.5f / Boot.PPM;
            } else if (GameScreen.camera.position.y < 357 / Boot.PPM)
                GameScreen.camera.position.y += 2.5f / Boot.PPM;
        }
        if (isMoving && isJumping) isMoving = false;
        if (isAttacking && isMoving) {
            isMoving = false;
            canMove = false;
        }
        if (isAttacking && stateTime >= 0.8f)
            isAttacking = false;
        if (isFalling && isMoving) isMoving = false;
        body.setTransform(posX, posY, 0);
    }

    public void update(float deltaTime) {
        inputHandle();
        if (isAttacking)
            if (isTurningRight)
                setBounds(body.getPosition().x - getWidth() / 2 + 32 / Boot.PPM, body.getPosition().y - getHeight() / 2, 128 / Boot.PPM, 80 / Boot.PPM);
            else
                setBounds(body.getPosition().x - getWidth() / 2 - 32 / Boot.PPM, body.getPosition().y - getHeight() / 2, 128 / Boot.PPM, 80 / Boot.PPM);
        else
            setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2, 64 / Boot.PPM, 80 / Boot.PPM);
        setRegion(getStatus(deltaTime));
    }

    public Body getBody() {
        return body;
    }

}
