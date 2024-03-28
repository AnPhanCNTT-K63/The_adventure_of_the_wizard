package com.folder.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.folder.Boot;
import com.folder.GameScreen;
import com.folder.Tool.KeyUpHandle;

import java.util.LinkedList;

public class MainCharacter extends Sprite {

    enum STATE {IDLE, RUN, JUMP, FALL, ATTACK_FLAME, ATTACK_NORMAL_1, ATTACK_NORMAL_2, ATTACK_NORMAL_3, ATTACK_HEAVY, LOOK_UP, LOOK_DOWN, DEAD, ROLL}

    World world;
    static public Body body;
    LinkedList<Fixture> fixtures;

    STATE previousState;
    STATE currenState;

    Animation<TextureRegion> idle;
    Animation<TextureRegion> run;
    Animation<TextureRegion> flameAttack;
    Animation<TextureRegion> normalAttack1;
    Animation<TextureRegion> normalAttack2;
    Animation<TextureRegion> normalAttack3;
    Animation<TextureRegion> heavyAttack;
    TextureRegion hurt;
    Animation<TextureRegion> jump;
    Animation<TextureRegion> fall;
    Animation<TextureRegion> dead;
    Animation<TextureRegion> roll;

    TextureRegion lookUp;
    TextureRegion lookDown;

    static public float stateTime;
    public static float actionDuration;

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
    public static boolean isDestroy;
    public static boolean isHurt;
    public static boolean isBleeding;
    public static boolean isDead;
    public static boolean isRolling;
    boolean setToDead;
    boolean isChangeSite;
    boolean isCreateFixture;

    public static Vector2 velocity;

    static public boolean canMove;

    static public boolean isAllowedJump;

    static public float jumpDistance;

    BleedingEffect bleedingEffect;

    int heart;

    int attackCount;

    public MainCharacter(GameScreen screen) {
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
        canMove = false;
        isDestroy = false;
        isCreateFixture = true;
        isChangeSite = true;
        isHurt = false;
        isBleeding = false;
        isRolling = false;

        attackCount = 0;

        isDead = false;
        setToDead = false;

        heart = 50;

        stateTime = 0;

        bleedingEffect = new BleedingEffect(screen);

        jumpDistance = 0;

        actionDuration = 0;

        velocity = new Vector2(0, 0);

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

        hurt = new TextureRegion(screen.getAtlas().findRegion("Hurt"), 0, 0, 128, 128);

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
        fixtureDef.filter.maskBits = Boot.GROUND_BIT | Boot.ENEMY_ATTACK_BIT | Boot.OBJECT_TEST_BIT | Boot.ENEMY_BIT | Boot.WALL_BIT;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void update(float deltaTime) {
        action(deltaTime);
        if (isBleeding && !isDead)
            bleedingEffect.update(deltaTime, body.getPosition().x, body.getPosition().y);

        if (isTurningRight)
            setBounds(body.getPosition().x - getWidth() / 2 + 16 / Boot.PPM, body.getPosition().y - getHeight() / 2 + 32 / Boot.PPM, 128 / Boot.PPM, 128 / Boot.PPM);
        else
            setBounds(body.getPosition().x - getWidth() / 2 - 16 / Boot.PPM, body.getPosition().y - getHeight() / 2 + 32 / Boot.PPM, 128 / Boot.PPM, 128 / Boot.PPM);

        setRegion(getStatus(deltaTime));
    }

    public void action(float deltaTime) {

        if (isDead) {
            isFalling = false;
            isJumping = false;
            isAttacking_Normal = false;
            isAttacking_Flame = false;
            isAttacking_heavy = false;
        }

        if (!isDead) {
            inputHandle();

            if (isJumping || isFalling)
                isMoving = false;

            if (isAttacking_heavy) {
                isRolling = false;
                isAttacking_Normal = false;
                isAttacking_Flame = false;
            }

            if (isAttacking_Normal) {
                isRolling = false;
                isAttacking_Flame = false;
            }

            if (isAttacking_Flame)
                isRolling = false;

            if (isAttacking_heavy || isAttacking_Normal || isAttacking_Flame || isRolling) {
                canMove = false;
                isMoving = false;
            }

            if (isHurt) {
                heart--;
                if (heart == 0)
                    setToDead = true;
                isHurt = false;
            }

            canMove = !isAttacking_Flame && !isAttacking_heavy && !isAttacking_Normal;

            isChangeSite = !isAttacking_Flame && !isAttacking_heavy && !isAttacking_Normal;

            isAllowedJump = !isFalling;

            timeToDestroyFixture();

            setActionDuration(deltaTime);

            setDestroyBody();

            if (isAttacking_heavy || isAttacking_Normal || isAttacking_Flame) setAttackBound();

            if (attackCount == 3)
                attackCount = 0;
        }
    }

    public void inputHandle() {
        Gdx.input.setInputProcessor(new KeyUpHandle());

        rollHandle();

        jumpHandle();

        attackHandle();

        movementHandle();

        siteHandle();

        lookHandle();
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
                isJumping = true;
                body.applyLinearImpulse(new Vector2(0, 4.4f), body.getWorldCenter(), true);
                isAllowedJump = false;
            }
        }
    }

    public void attackHandle() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) MainCharacter.isAttacking_Flame = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) MainCharacter.isAttacking_Normal = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) MainCharacter.isAttacking_heavy = true;
    }

    public void movementHandle() {

        if (canMove) if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D))
            MainCharacter.isMoving = true;

        if (isMoving) if (isTurningRight) velocity.set(4.3f, 0);
        else velocity.set(-4.3f, 0);
        else velocity.set(0, 0);

        velocity.y = body.getLinearVelocity().y;
        body.setLinearVelocity(velocity);

    }

    public void siteHandle() {
        if (isChangeSite) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) MainCharacter.isTurningRight = false;
            if (Gdx.input.isKeyPressed(Input.Keys.D)) MainCharacter.isTurningRight = true;
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
            if (GameScreen.camera.position.y >= 282f / Boot.PPM)
                GameScreen.camera.position.y = 282 / Boot.PPM;
            else if (GameScreen.camera.position.y <= 138 / Boot.PPM)
                GameScreen.camera.position.y = 138 / Boot.PPM;
        }

        if (isReturn) {
            if (GameScreen.camera.position.y > 210f / Boot.PPM) {
                GameScreen.camera.position.y -= 4.5f / Boot.PPM;
            } else if (GameScreen.camera.position.y < 208 / Boot.PPM) GameScreen.camera.position.y += 4.5f / Boot.PPM;
        }
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

        if (isBleeding) {
            if (BleedingEffect.bleedingDuration >= 0.35f) {
                isBleeding = false;
                BleedingEffect.bleedingDuration = 0;
            }
            BleedingEffect.bleedingDuration += deltaTime;
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
            fixtureDef.isSensor = true;
            fixtureDef.filter.categoryBits = categoryBit;
            fixtureDef.filter.maskBits = maskBit;

            fixtures.add(body.createFixture(fixtureDef));

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
        else if (isAttacking_Normal && attackCount == 0) return STATE.ATTACK_NORMAL_1;
        else if (isAttacking_Normal && attackCount == 1) return STATE.ATTACK_NORMAL_2;
        else if (isAttacking_Normal && attackCount == 2) return STATE.ATTACK_NORMAL_3;
        else if (isAttacking_heavy) return STATE.ATTACK_HEAVY;
        else if (isLookingUp) return STATE.LOOK_UP;
        else if (isLooking) return STATE.LOOK_DOWN;
        else if (isFalling) return STATE.FALL;
        else if (isDead) return STATE.DEAD;
        else if (isRolling) return STATE.ROLL;
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
        if (isBleeding && !isDead)
            bleedingEffect.draw(batch);
    }

}
