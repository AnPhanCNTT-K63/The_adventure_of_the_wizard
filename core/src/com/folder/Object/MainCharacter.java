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
    enum STATE {IDLE, RUN, JUMP, FALL, ATTACK_FLAME, ATTACK_NORMAL, ATTACK_HEAVY, LOOK_UP, LOOK_DOWN}

    World world;
    static public Body body;

    STATE previousState;
    STATE currenState;

    Animation<TextureRegion> idle;
    Animation<TextureRegion> run;
    Animation<TextureRegion> flameAttack;
    Animation<TextureRegion> normalAttack;
    Animation<TextureRegion> heavyAttack;
    Animation<TextureRegion> jump;
    Animation<TextureRegion> fall;

    TextureRegion lookUp;
    TextureRegion lookDown;

    static public float stateTime;

    //State handle
    public static boolean isTurningRight;
    public static boolean isMoving;
    public static boolean isJumping;
    public static boolean isAttacking_Flame;
    public static boolean isAttacking_Normal;
    public static boolean isAttacking_heavy;
    public static boolean isLooking;
    public static boolean isLookingUp;
    public static boolean isReturn;
    public static boolean isFalling;

    // Move distance
    public static float posX;
    public static float posY;

    //Character handle
    static public boolean canMove;
    static public boolean isAllowedJumping;

    public MainCharacter(GameScreen screen) {
        world = screen.getWorld();

        isTurningRight = true;
        isJumping = false;
        isMoving = false;
        isAttacking_Flame = false;
        isAttacking_Normal = false;
        isAttacking_heavy = false;
        isLooking = false;
        isLookingUp = false;
        isReturn = false;
        isFalling = false;
        isAllowedJumping = false;
        canMove = true;

        currenState = previousState = STATE.IDLE;
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 7; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Idle"), i * 128, 0, 128, 128));
        idle = new Animation<TextureRegion>(1 / 3f, frames);
        setBounds(0, 0, 128 / Boot.PPM, 128 / Boot.PPM);
        frames.clear();

        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Run"), i * 128, 0, 128, 128));
        run = new Animation<TextureRegion>(1 / 8f, frames);
        frames.clear();

        for (int i = 0; i < 14; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Flame_jet"), i * 128, 0, 128, 128));
        flameAttack = new Animation<TextureRegion>(1 / 14f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Attack1"), i * 128, 0, 128, 128));
        normalAttack = new Animation<TextureRegion>(1 / 10f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Attack2"), i * 128, 0, 128, 128));
        heavyAttack = new Animation<TextureRegion>(1 / 10f, frames);
        frames.clear();

        for (int i = 3; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jump"), i * 128, 0, 128, 128));
        jump = new Animation<TextureRegion>(1 / 4f, frames);
        frames.clear();

        for (int i = 4; i < 6; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jump"), i * 128, 0, 128, 128));
        fall = new Animation<TextureRegion>(1 / 4f, frames);
        frames.clear();

        lookUp = new TextureRegion(screen.getAtlas().findRegion("Idle"), 16, 0, 128, 128);

        lookDown = new TextureRegion(screen.getAtlas().findRegion("Idle"), 16, 0, 128, 128);

        MainCharacterDef();
    }

    public void MainCharacterDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Boot.screenWidth / 2 / Boot.PPM, Boot.screenHeight / 2 / Boot.PPM);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2 - 40 / Boot.PPM, getHeight() / 2 - 30 / Boot.PPM);

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
            case ATTACK_FLAME:
                region = flameAttack.getKeyFrame(stateTime);
                break;
            case ATTACK_NORMAL:
                region = normalAttack.getKeyFrame(stateTime);
                break;
            case ATTACK_HEAVY:
                region = heavyAttack.getKeyFrame(stateTime);
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
        else if (isAttacking_Flame) return STATE.ATTACK_FLAME;
        else if (isAttacking_Normal) return STATE.ATTACK_NORMAL;
        else if (isAttacking_heavy) return STATE.ATTACK_HEAVY;
        else if (isLookingUp) return STATE.LOOK_UP;
        else if (isLooking) return STATE.LOOK_DOWN;
        else if (isFalling) return STATE.FALL;
        else return STATE.IDLE;
    }

    public void inputHandle() {
        Gdx.input.setInputProcessor(new InputAdvance());
        posX = body.getPosition().x;
        posY = body.getPosition().y;

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
            if (GameScreen.camera.position.y >= 309.99998f / Boot.PPM)
                GameScreen.camera.position.y = 309.99998f / Boot.PPM;
            else if (GameScreen.camera.position.y <= 159.99998f / Boot.PPM)
                GameScreen.camera.position.y = 159.99998f / Boot.PPM;
        }

        if (isReturn) {
            if (GameScreen.camera.position.y > 234.99998f / Boot.PPM) {
                GameScreen.camera.position.y -= 2.5f / Boot.PPM;
            } else if (GameScreen.camera.position.y < 232 / Boot.PPM)
                GameScreen.camera.position.y += 2.5f / Boot.PPM;
        }
        if (isMoving && isJumping) isMoving = false;
        if (isAttacking_Flame && isMoving) {
            isMoving = false;
            canMove = false;
        }
        if (isAttacking_Flame && stateTime >= 0.8f)
            isAttacking_Flame = false;
        if (isFalling && isMoving) isMoving = false;
        body.setTransform(posX, posY, 0);
    }

    public void update(float deltaTime) {
        inputHandle();
//        if (isAttacking_Flame)
//            if (isTurningRight)
//                setBounds(body.getPosition().x - getWidth() / 2 + 32 / Boot.PPM, body.getPosition().y - getHeight() / 2, 128 / Boot.PPM, 80 / Boot.PPM);
//            else
//                setBounds(body.getPosition().x - getWidth() / 2 - 32 / Boot.PPM, body.getPosition().y - getHeight() / 2, 128 / Boot.PPM, 80 / Boot.PPM);
//        else if (isAttacking_Normal || isAttacking_heavy)
//            if (isTurningRight)
//                setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2, 128 / Boot.PPM, 80 / Boot.PPM);
//            else
//                setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2, 128 / Boot.PPM, 80 / Boot.PPM);
        if (isTurningRight)
            setBounds(body.getPosition().x - getWidth() / 2 + 16 / Boot.PPM, body.getPosition().y - getHeight() / 2 + 32 / Boot.PPM, 128 / Boot.PPM, 128 / Boot.PPM);
        else
            setBounds(body.getPosition().x - getWidth() / 2 - 16 / Boot.PPM, body.getPosition().y - getHeight() / 2 + 32 / Boot.PPM, 128 / Boot.PPM, 128 / Boot.PPM);

        setRegion(getStatus(deltaTime));
    }

    public Body getBody() {
        return body;
    }

}
