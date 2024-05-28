package com.folder.Object;

import box2dLight.DirectionalLight;
import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.folder.Boot;

import com.folder.Object.Enemy.Boss.BossElementEarth;
import com.folder.Object.Enemy.Creep.Werewolves;
import com.folder.Object.Enemy.Enemy;
import com.folder.Object.MagicEffect.Effect.BleedingEffect;
import com.folder.Object.MagicEffect.Effect.FireShield;
import com.folder.Object.MagicEffect.MagicEffect;
import com.folder.Screen.GameScreen;
import com.folder.Tool.KeyUpHandle;

import java.util.LinkedList;

public class MainCharacter extends Sprite {

    private enum STATE {IDLE, RUN, JUMP, FALL, ATTACK_FLAME, ATTACK_NORMAL_1, ATTACK_NORMAL_2, ATTACK_NORMAL_3, ATTACK_HEAVY, LOOK_UP, LOOK_DOWN, DEAD, ROLL, HANG, PULL_UP, CLIMB, CLING, HEAL, HEAVY_HURT}

    private World world;
    private LinkedList<Fixture> fixtures;

    private STATE previousState;
    private STATE currenState;

    // Animation handle
    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> run;
    private Animation<TextureRegion> flameAttack;
    private Animation<TextureRegion> normalAttack1;
    private Animation<TextureRegion> normalAttack2;
    private Animation<TextureRegion> normalAttack3;
    private Animation<TextureRegion> heavyAttack;
    private Animation<TextureRegion> hang;
    private Animation<TextureRegion> pullUp;
    private Animation<TextureRegion> jump;
    private Animation<TextureRegion> fall;
    private Animation<TextureRegion> dead;
    private Animation<TextureRegion> roll;
    private Animation<TextureRegion> climb;
    private Animation<TextureRegion> heal;

    private TextureRegion lookUp;
    private TextureRegion lookDown;
    private TextureRegion cling;
    private TextureRegion hurt;
    private TextureRegion heavyHurt;

    private float stateTime;
    private float actionDuration;

    //State handle
    private boolean isTurningRight;
    private boolean isAttacking_Flame;
    private boolean isAttacking_Normal;
    private boolean isAttacking_heavy;
    private boolean isDestroy;
    private boolean isRolling;
    private boolean isPullUp;
    private static boolean isHealing;


    private boolean setToDead;
    private boolean isChangeSite;
    private boolean isCreateFixture;

    private Vector2 velocity;

    public static boolean isInMap1;
    public static boolean isInMap2;

    private float jumpDistance;

    private MagicEffect bleedingEffect;
    private MagicEffect fireShield;

    private int heart;

    private int attackCount;

    public static float yHang;
    public static float xHang;

    private Vector2 previousPos;

    private GameScreen screen;

    public static Body body;
    public static boolean isMoving;
    public static boolean isJumping;
    public static boolean isLooking;
    public static boolean isLookingUp;
    public static boolean isReturn;
    public static boolean isFalling;
    public static boolean isHurt;
    public static boolean isBleeding;
    public static boolean isDead;
    public static boolean isClimbing;
    public static boolean isClingLadder;
    public static boolean isHanging;
    public static boolean isHeavyHurt;
    public static boolean isAllowedNextMap;
    public static boolean isAllowedMove;
    public static boolean isAllowedJump;
    public static boolean isOnAir;
    public static boolean isTouchAirGround;

    public MainCharacter(GameScreen screen) {
        this.screen = screen;
        previousPos = new Vector2();
        world = screen.getWorld();

        fixtures = new LinkedList<>();
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
        isAllowedJump = true;
        isAllowedMove = false;
        isDestroy = false;
        isCreateFixture = true;
        isChangeSite = true;
        isHurt = false;
        isBleeding = false;
        isRolling = false;
        isHanging = false;
        isPullUp = false;
        isClimbing = false;
        isClingLadder = false;
        isHealing = false;
        isHeavyHurt = false;

        isAllowedNextMap = false;

        isOnAir = false;
        isTouchAirGround = false;

        isInMap1 = true;
        isInMap2 = false;

        attackCount = 0;

        isDead = false;
        setToDead = false;

        heart = 10;

        yHang = 0;
        xHang = 0;

        stateTime = 0;

        bleedingEffect = new BleedingEffect(screen);
        fireShield = new FireShield(screen);

        jumpDistance = 0;

        actionDuration = 0;

        velocity = new Vector2(0, 0);

        currenState = previousState = STATE.IDLE;
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 7; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Idle"), i * 128, 0, 128, 128));
        idle = new Animation<TextureRegion>(1 / 3f, frames);
        frames.clear();

        setBounds(0, 0, 128 / Boot.PPM, 128 / Boot.PPM);

        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Run"), i * 128, 0, 128, 128));
        run = new Animation<TextureRegion>(1 / 8f, frames);
        frames.clear();

        for (int i = 0; i < 14; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Flame_jet"), i * 128, 0, 128, 128));
        flameAttack = new Animation<TextureRegion>(1 / 14f, frames);
        frames.clear();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Attack_normal1"), i * 128, 0, 128, 128));
        normalAttack1 = new Animation<TextureRegion>(1 / 10f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Attack_normal2"), i * 128, 0, 128, 128));
        normalAttack2 = new Animation<TextureRegion>(1 / 10f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Attack_normal3"), i * 128, 0, 128, 128));
        normalAttack3 = new Animation<TextureRegion>(1 / 10f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Attack2"), i * 128, 0, 128, 128));
        heavyAttack = new Animation<TextureRegion>(1 / 6f, frames);
        frames.clear();

        for (int i = 4; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jump"), i * 128, 0, 128, 128));
        jump = new Animation<TextureRegion>(1, frames);
        frames.clear();

        for (int i = 5; i < 6; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jump"), i * 128, 0, 128, 128));
        fall = new Animation<TextureRegion>(1, frames);
        frames.clear();

        lookUp = new TextureRegion(screen.getAtlas().findRegion("Idle"), 0, 0, 128, 128);

        lookDown = new TextureRegion(screen.getAtlas().findRegion("Idle"), 0, 0, 128, 128);

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Dead"), i * 128, 0, 128, 128));
        dead = new Animation<TextureRegion>(1 / 6f, frames);
        frames.clear();

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Roll"), i * 128, 0, 128, 128));
        roll = new Animation<TextureRegion>(1 / 10f, frames);
        frames.clear();

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Hang"), i * 128, 0, 128, 128));
        hang = new Animation<TextureRegion>(1 / 6f, frames);
        frames.clear();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Pull_up"), i * 128, 0, 128, 128));
        pullUp = new Animation<TextureRegion>(1 / 10f, frames);
        frames.clear();

        hurt = new TextureRegion(screen.getAtlas().findRegion("Hurt"), 0, 0, 128, 128);

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Climb"), i * 128, 0, 128, 128));
        climb = new Animation<TextureRegion>(1 / 6f, frames);
        cling = new TextureRegion(screen.getAtlas().findRegion("Climb"), 128, 0, 128, 128);
        frames.clear();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Elixir"), i * 128, 0, 128, 128));
        heal = new Animation<TextureRegion>(1 / 7f, frames);
        frames.clear();

        heavyHurt = new TextureRegion(screen.getAtlas().findRegion("Area_Attack"), 0, 0, 128, 128);

        setUpBody();
    }

    public void setUpBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(250 / Boot.PPM, 207.5f / Boot.PPM + 100 / Boot.PPM);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2 - 45 / Boot.PPM, getHeight() / 2 - 30 / Boot.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Boot.CHARACTER_BIT;
        fixtureDef.filter.maskBits = Boot.GROUND_BIT | Boot.ENEMY_ATTACK_BIT | Boot.OBJECT_TEST_BIT | Boot.ENEMY_BIT | Boot.WALL_BIT | Boot.TOUCH_POINT_ON_AIR | Boot.HANG_POINT_BIT | Boot.LADDER_POINT_BIT | Boot.DOOR_BIT;

        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

    public void update(float deltaTime) {
        action(deltaTime);
        if (isBleeding && !isDead) bleedingEffect.update(deltaTime, body.getPosition().x, body.getPosition().y);
        if (isHealing && !isDead) fireShield.update(deltaTime, body.getPosition().x, body.getPosition().y);

        if (!isPullUp) if (isTurningRight)
            setBounds(body.getPosition().x - getWidth() / 2 + 16 / Boot.PPM, body.getPosition().y - getHeight() / 2 + 32 / Boot.PPM, 128 / Boot.PPM, 128 / Boot.PPM);
        else
            setBounds(body.getPosition().x - getWidth() / 2 - 16 / Boot.PPM, body.getPosition().y - getHeight() / 2 + 32 / Boot.PPM, 128 / Boot.PPM, 128 / Boot.PPM);
        else {
            if (isTurningRight)
                setBounds(body.getPosition().x - getWidth() / 2 + 16 / Boot.PPM, body.getPosition().y - getHeight() / 2 - 40 / Boot.PPM, 128 / Boot.PPM, 128 / Boot.PPM);
            else
                setBounds(body.getPosition().x - getWidth() / 2 - 16 / Boot.PPM, body.getPosition().y - getHeight() / 2 - 40 / Boot.PPM, 128 / Boot.PPM, 128 / Boot.PPM);
        }

        if (isClimbing || (isClingLadder && isOnAir)) if (isTurningRight)
            setBounds(body.getPosition().x - getWidth() / 2 + 16 / Boot.PPM, body.getPosition().y - getHeight() / 2 + 15 / Boot.PPM, 128 / Boot.PPM, 128 / Boot.PPM);
        else
            setBounds(body.getPosition().x - getWidth() / 2 - 16 / Boot.PPM, body.getPosition().y - getHeight() / 2 + 15 / Boot.PPM, 128 / Boot.PPM, 128 / Boot.PPM);

        if (isHealing) if (isTurningRight)
            setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 32 / Boot.PPM, 128 / Boot.PPM, 128 / Boot.PPM);
        else
            setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 32 / Boot.PPM, 128 / Boot.PPM, 128 / Boot.PPM);

        setRegion(getStatus(deltaTime));
    }

    public void action(float deltaTime) {
        if (!isDead) {
            inputHandle();

            if (isJumping || isFalling) isMoving = false;
            if (isJumping) {
                isClimbing = false;
                isClingLadder = false;
            }

            if (isAttacking_heavy) {
                isRolling = false;
                isAttacking_Normal = false;
                isAttacking_Flame = false;
                isHealing = false;
            }

            if (isAttacking_Normal) {
                isRolling = false;
                isAttacking_Flame = false;
                isHealing = false;
            }

            if (isAttacking_Flame) {
                isHealing = false;
                isRolling = false;
            }

            if (isAttacking_heavy || isAttacking_Normal || isAttacking_Flame || isRolling) isMoving = false;

            if (isHurt) {
                isHanging = false;
                heart--;
                if (heart == 0) setToDead = true;
                isHurt = false;
            }

            if (isPullUp) isHanging = false;

            isAllowedMove = !isAttacking_Flame && !isAttacking_heavy && !isAttacking_Normal;

            isChangeSite = !isAttacking_Flame && !isAttacking_heavy && !isAttacking_Normal && !isHanging;

            isAllowedJump = !isFalling;

            timeToDestroyFixture();
            setActionDuration(deltaTime);
            setDestroyBody();
            setAttackBound();

            if ((isAttacking_heavy || isAttacking_Normal || isAttacking_Flame) && isJumping) isJumping = false;

            if (isOnAir) isAllowedMove = true;

            if (attackCount == 3) attackCount = 0;

            if (isHanging || isPullUp) {
                isAttacking_heavy = false;
                isAttacking_Normal = false;
                isAttacking_Flame = false;
                isJumping = false;
                isRolling = false;
                isFalling = false;
                isAllowedMove = false;
                isMoving = false;
            }

            if (isClimbing || (isClingLadder && isOnAir)) {
                isChangeSite = false;
                isAttacking_Flame = false;
                isAttacking_Normal = false;
                isAttacking_heavy = false;
            }

        }
    }

    public void inputHandle() {
        if (!isHeavyHurt) {
            Gdx.input.setInputProcessor(new KeyUpHandle());

            nextMapHandle();

            healHandle();

            climbHandle();

            hangAndPullUpHandle();

            rollHandle();

            jumpHandle();

            attackHandle();

            movementHandle();

            siteHandle();

            lookHandle();

        }
    }

    public void nextMapHandle() {
        if (isAllowedNextMap) {
            screen.loadMap("bossMap.tmx");
            isAllowedNextMap = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            isInMap2 = false;
            screen.loadMap("Map1.tmx");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            isInMap2 = false;
            screen.loadMap("bossMap.tmx");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            MainCharacter.body.setTransform(new Vector2(50 / Boot.PPM, 570 / Boot.PPM), 0);
            isInMap2 = true;
            screen.loadMap("Map2.tmx");
        }
    }

    public void healHandle() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            isHealing = true;
        }
        System.out.println(body.getPosition().x + " " + body.getPosition().y);
    }

    public void climbHandle() {
        if (Gdx.input.isKeyPressed(Input.Keys.P) && isClingLadder) {
            isClimbing = true;
        }

        if (isClimbing) {
            body.setLinearVelocity(0, 1);
        } else if (isClingLadder && isOnAir) {
            isFalling = false;
            isAllowedMove = false;
            body.setTransform(previousPos, 0);
        }

    }

    public void hangAndPullUpHandle() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && isHanging) {
            isPullUp = true;
        }

        if (isHanging) body.setTransform(body.getPosition().x, yHang - 48 / Boot.PPM, 0);

        else if (isPullUp) body.setTransform(xHang - 1 / Boot.PPM, yHang + 52 / Boot.PPM, 0);
        else {
            velocity.y = body.getLinearVelocity().y;
            body.setLinearVelocity(velocity);
        }
    }

    public void rollHandle() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT) && isMoving) {
            isRolling = true;
        }

    }

    public void jumpHandle() {
//        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && isAllowedJump) {
//            jumpDistance += 14f;
//            MainCharacter.isJumping = true;
//            MainCharacter.body.applyForceToCenter(new Vector2(0, jumpDistance * 0.9f), true);
//            if (jumpDistance >= 98) {
//                jumpDistance = 0;
//                isJumping = false;
//                isFalling = true;
//            }
//        }
        if (isAllowedJump) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                //isOnAir = true;
                isJumping = true;
                body.applyLinearImpulse(new Vector2(0, 4.4f), body.getWorldCenter(), true);
                isAllowedJump = false;
            }
        }
    }

    public void attackHandle() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) isAttacking_Flame = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) isAttacking_Normal = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) isAttacking_heavy = true;
    }

    public void movementHandle() {
        previousPos.set(body.getPosition());
        if (isAllowedMove && !isTouchAirGround)
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D))
                isMoving = true;

        if (isMoving) if (isTurningRight) velocity.set(4.3f, 0);
        else velocity.set(-4.3f, 0);
        else velocity.set(0, 0);
    }

    public void siteHandle() {
        if (isChangeSite) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) isTurningRight = false;
            if (Gdx.input.isKeyPressed(Input.Keys.D)) isTurningRight = true;
        }
    }

    public void lookHandle() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            MainCharacter.isReturn = false;
            MainCharacter.isLooking = true;
            MainCharacter.isLookingUp = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            MainCharacter.isReturn = false;
            MainCharacter.isLooking = true;
            MainCharacter.isLookingUp = false;
        }

        setCameraLooking();
    }

    public void setCameraLooking() {
        if (isLooking && stateTime >= 0.7f) {
            if (isLookingUp) GameScreen.camera.position.y += 4.5f / Boot.PPM;
            else GameScreen.camera.position.y -= 4.5f / Boot.PPM;
            if (GameScreen.camera.position.y >= 282f / Boot.PPM) GameScreen.camera.position.y = 282 / Boot.PPM;
            else if (GameScreen.camera.position.y <= 138 / Boot.PPM) GameScreen.camera.position.y = 138 / Boot.PPM;
        }

        if (isReturn) {
            if (GameScreen.camera.position.y > 211f / Boot.PPM) {
                GameScreen.camera.position.y -= 4.5f / Boot.PPM;
            } else if (GameScreen.camera.position.y < 208 / Boot.PPM) GameScreen.camera.position.y += 4.5f / Boot.PPM;
        }

        if (!isLooking) if (body.getPosition().y >= 420 / Boot.PPM && body.getPosition().y < 724 / Boot.PPM)
            GameScreen.camera.position.y = 500 / Boot.PPM;
        else if (body.getPosition().y < 420 / Boot.PPM) GameScreen.camera.position.y = 210 / Boot.PPM;
        else if (body.getPosition().y >= 724 / Boot.PPM) GameScreen.camera.position.y = 805 / Boot.PPM;


    }

    public void timeToDestroyFixture() {
        setTimeToDestroyFixture(isAttacking_heavy, 0.8f);
        setTimeToDestroyFixture(isAttacking_Normal, 0.45f);
        setTimeToDestroyFixture(isAttacking_Flame, 1f);
    }


    public void setTimeToDestroyFixture(boolean action, float time) {
        if (action) {
            if (actionDuration >= time) {
                destroyFixtureAttack();
                isCreateFixture = true;
            }
        }
    }

    public void destroyFixtureAttack() {
        for (Fixture fixture : fixtures)
            if (fixture.isSensor()) body.destroyFixture(fixture);
        fixtures.clear();
    }

    public void setActionDuration(float deltaTime) {
        if (isAttacking_Flame) {
            if (actionDuration >= 1f) {
                isAttacking_Flame = false;
                actionDuration = 0;
            }
            actionDuration += deltaTime;
        }

        if (isAttacking_Normal) {
            if (actionDuration >= 0.45f) {
                isAttacking_Normal = false;
                actionDuration = 0;
                attackCount++;
            }
            actionDuration += deltaTime;
        }

        if (isAttacking_heavy) {
            if (actionDuration >= 0.8f) {
                isAttacking_heavy = false;
                actionDuration = 0;
            }
            actionDuration += deltaTime;
        }

        if (isRolling) {
            if (actionDuration >= 0.6f) {
                isRolling = false;
                actionDuration = 0;
            }
            actionDuration += deltaTime;
        }

        if (isPullUp) {
            if (actionDuration >= 0.45f) {
                isPullUp = false;
                actionDuration = 0;
            }
            actionDuration += deltaTime;
        }

        if (isHealing) {
            if (actionDuration >= 1.5f) {
                isHealing = false;
                actionDuration = 0;
            }
            actionDuration += deltaTime;
        }

        if (isBleeding) {
            if (bleedingEffect.getActionDuration() >= 0.25f) {
                isBleeding = false;
                bleedingEffect.setActionDuration(0);
            }
            bleedingEffect.updateActionDuration(deltaTime);
        }

        if (isHeavyHurt) {
            if (actionDuration >= 0.45f) {
                isHeavyHurt = false;
                actionDuration = 0;
            }
            actionDuration += deltaTime;
        }
    }

    public void setAttackBound() {
        if (isAttacking_heavy) createFixture(0.58f, 32, 3, 40, 2, 0, Boot.ATTACK_BIT, Boot.ENEMY_BIT);
        else if (isAttacking_Normal) createFixture(0.3f, 30, 15, 30, 0, 0, Boot.ATTACK_BIT, Boot.ENEMY_BIT);
        else if (isAttacking_Flame) createFixture(0.48f, 40, 25, 35, 10, 0, Boot.ATTACK_BIT, Boot.ENEMY_BIT);
    }

    public void createFixture(float time, float width, float height, float x, float y, float angle, short categoryBit, short maskBit) {
        if (isCreateFixture && actionDuration >= time) {
            PolygonShape shape = new PolygonShape();

            if (isTurningRight)
                shape.setAsBox(width / Boot.PPM, height / Boot.PPM, new Vector2(x / Boot.PPM, y / Boot.PPM), angle);
            else shape.setAsBox(width / Boot.PPM, height / Boot.PPM, new Vector2(-x / Boot.PPM, y / Boot.PPM), angle);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = categoryBit;
            fixtureDef.filter.maskBits = maskBit;
            fixtureDef.isSensor = true;

            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setUserData(this);
            fixtures.add(fixture);

            shape.dispose();

            isCreateFixture = false;
        }
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
            case ATTACK_NORMAL_1:
                region = normalAttack1.getKeyFrame(stateTime);
                break;
            case ATTACK_NORMAL_2:
                region = normalAttack2.getKeyFrame(stateTime);
                break;
            case ATTACK_NORMAL_3:
                region = normalAttack3.getKeyFrame(stateTime);
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
            case DEAD:
                region = dead.getKeyFrame(stateTime);
                break;
            case ROLL:
                region = roll.getKeyFrame(stateTime);
                break;
            case HANG:
                region = hang.getKeyFrame(stateTime, true);
                break;
            case PULL_UP:
                region = pullUp.getKeyFrame(stateTime);
                break;
            case CLING:
                region = cling;
                break;
            case CLIMB:
                region = climb.getKeyFrame(stateTime, true);
                break;
            case HEAL:
                region = heal.getKeyFrame(stateTime);
                break;
            case HEAVY_HURT:
                region = heavyHurt;
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
        if (isMoving && !isHeavyHurt) return STATE.RUN;
        else if (isJumping) return STATE.JUMP;
        else if (isAttacking_Flame) return STATE.ATTACK_FLAME;
        else if (isAttacking_Normal && attackCount == 0) return STATE.ATTACK_NORMAL_1;
        else if (isAttacking_Normal && attackCount == 1) return STATE.ATTACK_NORMAL_2;
        else if (isAttacking_Normal && attackCount == 2) return STATE.ATTACK_NORMAL_3;
        else if (isAttacking_heavy) return STATE.ATTACK_HEAVY;
        else if (isLookingUp) return STATE.LOOK_UP;
        else if (isLooking) return STATE.LOOK_DOWN;
        else if (isFalling) return STATE.FALL;
        else if (isDead) return STATE.DEAD;
        else if (isRolling) return STATE.ROLL;
        else if (isHanging) return STATE.HANG;
        else if (isPullUp) return STATE.PULL_UP;
        else if (isClingLadder && isOnAir && !isClimbing) return STATE.CLING;
        else if (isClimbing) return STATE.CLIMB;
        else if (isHealing) return STATE.HEAL;
        else if (isHeavyHurt) return STATE.HEAVY_HURT;
        else return STATE.IDLE;
    }

    public static void beDamaged() {
        isHurt = true;
        isBleeding = true;
    }

    public Body getBody() {
        return body;
    }

    public void setDestroyBody() {
        if (setToDead && !isDead) {
            world.destroyBody(body);
            isDead = true;
        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        if (isBleeding && !isDead) bleedingEffect.draw(batch);
        if (isHealing && !isDead) fireShield.draw(batch);
    }

    public void beStrongPushed(Enemy enemy) {
        if (enemy instanceof BossElementEarth) {
            if (body.getPosition().x > enemy.getPosX()) body.applyForceToCenter(new Vector2(200f, 300f), true);
            else body.applyForceToCenter(new Vector2(-200f, 300f), true);
            MainCharacter.isHeavyHurt = true;
        }
        //else if (enemy instanceof Werewolves) collideEnemy(enemy);

    }

    public void collideEnemy(Enemy enemy) {
        body.setLinearVelocity(new Vector2(0, 0));
        if (body.getPosition().x > enemy.getPosX()) body.applyForceToCenter(new Vector2(160f, 0), true);
            //body.applyLinearImpulse(new Vector2(6f, 0f), MainCharacter.body.getWorldCenter(), true);
        else body.applyForceToCenter(new Vector2(-160f, 0), true);
        //body.applyLinearImpulse(new Vector2(-6f, 0f), MainCharacter.body.getWorldCenter(), true);

    }


}
