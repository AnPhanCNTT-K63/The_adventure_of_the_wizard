package com.folder.Object.Enemy.Boss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.folder.Boot;
import com.folder.Object.Enemy.Enemy;
import com.folder.Object.MagicEffect.Effect.ExplosionEffect;
import com.folder.Object.MagicEffect.Effect.WaterTrapBallEffect;
import com.folder.Object.MagicEffect.MagicEffect;
import com.folder.Object.MainCharacter;
import com.folder.Screen.GameScreen;


import java.util.LinkedList;

public class BossElementEarth extends Enemy {
    enum STATE {WALK, ATTACK, RUN, HURT, DEAD, SKILL_1, SKILL_2}

    private STATE currentState;
    private STATE previousState;

    LinkedList<Fixture> fixtures;


    private Animation<TextureRegion> Walk;
    private Animation<TextureRegion> Chase;
    private Animation<TextureRegion> Attack;
    private Animation<TextureRegion> Hurt;
    private Animation<TextureRegion> Dead;
    private Animation<TextureRegion> skill_1;
    private Animation<TextureRegion> skill_2;

    public float stateTime;
    float actionDuration;

    private boolean isWalking;
    private boolean isAttacking;
    private boolean isChasing;
    private boolean canMove;
    private boolean isDead;
    private boolean setToDead;
    private boolean isExist;
    private boolean isCreateFixture;
    private boolean isHurt;
    private boolean isBleeding;
    private boolean isHeavyHurt;
    private boolean isReverse;

    private boolean readySkill_1;
    private boolean useSkill_1;
    private boolean readySkill_2;
    private boolean useSkill_2;


    private boolean isTurningRight;

    private MagicEffect explosionEffect;
    private MagicEffect waterTrapBall;
    private BossEarthRangeAttack rangeAttack;

    int heart;

    public BossElementEarth(GameScreen screen, float posX, float posY) {
        super(screen, posX, posY);
        fixtures = new LinkedList<>();

        currentState = previousState = STATE.WALK;

        isDead = false;
        isWalking = true;
        isAttacking = false;
        isChasing = false;
        isHurt = false;
        isHeavyHurt = false;
        canMove = true;
        isBleeding = false;
        setToDead = false;
        isExist = true;
        isCreateFixture = true;
        isReverse = false;
        readySkill_1 = true;
        useSkill_1 = false;
        readySkill_2 = true;
        useSkill_2 = false;

        isTurningRight = true;

        stateTime = 0;
        actionDuration = 0;

        explosionEffect = new ExplosionEffect(screen);
        waterTrapBall = new WaterTrapBallEffect(screen);
        rangeAttack = new BossEarthRangeAttack(screen, getX(), getY(), Boot.ENEMY_ATTACK_BIT);

        heart = 6;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getBossAtlas().findRegion("Walk"), i * 128, 0, 128, 128));
        Walk = new Animation<TextureRegion>(1 / 8f, frames);
        frames.clear();

        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getBossAtlas().findRegion("Run"), i * 128, 0, 128, 128));
        Chase = new Animation<TextureRegion>(1 / 8f, frames);
        frames.clear();

        for (int i = 0; i < 7; i++)
            frames.add(new TextureRegion(screen.getBossAtlas().findRegion("Attack2"), i * 128, 0, 128, 128));
        Attack = new Animation<TextureRegion>(1 / 7f, frames);
        frames.clear();

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(screen.getBossAtlas().findRegion("Attack1"), i * 128, 0, 128, 128));
        skill_1 = new Animation<TextureRegion>(1 / 6f, frames);
        frames.clear();

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(screen.getBossAtlas().findRegion("Attack1"), i * 128, 0, 128, 128));
        skill_2 = new Animation<TextureRegion>(1 / 6f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(screen.getBossAtlas().findRegion("Hurt"), i * 128, 0, 128, 128));
        Hurt = new Animation<TextureRegion>(1 / 4f, frames);
        frames.clear();

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(screen.getBossAtlas().findRegion("Dead"), i * 128, 0, 128, 128));
        Dead = new Animation<TextureRegion>(1 / 4f, frames);
        frames.clear();
    }

    @Override
    public void setUpBody() {
        setBounds(getX(), getY(), 300 / Boot.PPM, 300 / Boot.PPM);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX(), 0.66499996f);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2 - 125 / Boot.PPM, getHeight() / 2 - 100 / Boot.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Boot.ENEMY_BIT;
        fixtureDef.filter.maskBits = Boot.GROUND_BIT | Boot.ATTACK_BIT | Boot.CHARACTER_BIT | Boot.WALL_BIT;

        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

    public TextureRegion getStatus(float deltaTime) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case RUN:
                region = Chase.getKeyFrame(stateTime, true);
                break;
            case ATTACK:
                region = Attack.getKeyFrame(stateTime, true);
                break;
            case HURT:
                region = Hurt.getKeyFrame(stateTime, true);
                break;
            case DEAD:
                region = Dead.getKeyFrame(stateTime);
                break;
            case SKILL_1:
                region = skill_1.getKeyFrame(stateTime);
                break;
            default:
                region = Walk.getKeyFrame(stateTime, true);
                break;
        }

        if (!isTurningRight && !region.isFlipX()) region.flip(true, false);
        else if (isTurningRight && region.isFlipX()) region.flip(true, false);

        stateTime = currentState == previousState ? stateTime + deltaTime : 0;
        previousState = currentState;

        return region;
    }

    public STATE getState() {
        if (isChasing) return STATE.RUN;
        else if (isAttacking) return STATE.ATTACK;
        else if (isHeavyHurt) return STATE.HURT;
        else if (isDead) return STATE.DEAD;
        else if (useSkill_1) return STATE.SKILL_1;
        else return STATE.WALK;
    }

    public void action() {
        setActionDuration();
        skillHandle();

        if (isDead && stateTime >= 3f) isExist = false;

        if (isHurt) {
            heart--;
            if (heart == 5) isHeavyHurt = true;
            if (heart == 0) setToDead = true;
            isHurt = false;
        }

        if (isDead || isHeavyHurt) {
            isChasing = false;
            isAttacking = false;
            canMove = false;
        }

        destroyBody();

        if (!isDead) {
            if (!isHeavyHurt) {
                if (body.getPosition().x - MainCharacter.body.getPosition().x < 70 / Boot.PPM && body.getPosition().x - MainCharacter.body.getPosition().x > 10 / Boot.PPM && !MainCharacter.isDead) {
                    isAttacking = true;
                    isTurningRight = false;
                } else if (body.getPosition().x - MainCharacter.body.getPosition().x > -70 / Boot.PPM && body.getPosition().x - MainCharacter.body.getPosition().x < 10 / Boot.PPM && !MainCharacter.isDead) {
                    isAttacking = true;
                    isTurningRight = true;
                } else {
                    canMove = true;
                }

                if (isAttacking) {
                    isChasing = false;
                    canMove = false;
                }

                if (!isAttacking)
                    if (body.getPosition().x - MainCharacter.body.getPosition().x < 300 / Boot.PPM && body.getPosition().x - MainCharacter.body.getPosition().x > 30 / Boot.PPM && !MainCharacter.isDead) {
                        isChasing = true;
                        isTurningRight = false;
                        velocity.set(-2.3f, 0);
                    } else if (body.getPosition().x - MainCharacter.body.getPosition().x > -300 / Boot.PPM && body.getPosition().x - MainCharacter.body.getPosition().x < 10 / Boot.PPM && !MainCharacter.isDead) {
                        isChasing = true;
                        isTurningRight = true;
                        velocity.set(2.3f, 0);
                    } else isChasing = false;

                isWalking = !isChasing;

                if (isWalking && stateTime >= 3.5f) {
                    isReverse = true;
                    stateTime = 0;
                }

                if (isReverse) {
                    isTurningRight = !isTurningRight;
                    isReverse = false;
                }

                if (useSkill_1 || useSkill_2) {
                    isChasing = false;
                    canMove = false;
                    isAttacking = false;
                }

                if (canMove) {
                    if (isWalking) if (isTurningRight) {
                        velocity.set(1.3f, 0);
                    } else velocity.set(-1.3f, 0);
                } else velocity.set(0, 0);

                velocity.y = body.getLinearVelocity().y;
                body.setLinearVelocity(velocity);

            }
        }
        setAttackBound();
        timeToDestroyFixture();
    }

    public void skillHandle() {
        if (getHitCount() == 2 && readySkill_1) {
            useSkill_1 = true;
            readySkill_1 = false;
            WaterTrapBallEffect.x = MainCharacter.body.getPosition().x;
            WaterTrapBallEffect.y = MainCharacter.body.getPosition().y;
        }

        if (getHitCount() == 3 && readySkill_2) {
            useSkill_2 = true;
            rangeAttack.ready = true;
            readySkill_2 = false;
        }

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

    public void timeToDestroyFixture() {
        setTimeToDestroyFixture(isAttacking, 1f);
        setTimeToDestroyFixture(isHeavyHurt, 0);
        setTimeToDestroyFixture(useSkill_1, 1.65f);
    }

    public void setTimeToDestroyFixture(boolean action, float time) {
        if (action) {
            if (actionDuration >= time) {
                destroyFixture();
                isCreateFixture = true;
            }
        }
    }

    public void destroyFixture() {
        for (Fixture fixture : fixtures)
            if (fixture.isSensor()) body.destroyFixture(fixture);
        fixtures.clear();
    }

    public void setAttackBound() {
        if (isAttacking) createFixture(0.65f, 25, 30, 70, -10, 0, Boot.ENEMY_ATTACK_BIT, Boot.CHARACTER_BIT);

        if (useSkill_1) if (!isTurningRight)
            createFixture(1f, 55, 50, body.getPosition().x * Boot.PPM - MainCharacter.body.getPosition().x * Boot.PPM, body.getPosition().y * Boot.PPM - MainCharacter.body.getPosition().y * Boot.PPM, 0, Boot.ENEMY_ATTACK_BIT, Boot.CHARACTER_BIT);
        else
            createFixture(1f, 55, 50, -body.getPosition().x * Boot.PPM + MainCharacter.body.getPosition().x * Boot.PPM, -body.getPosition().y * Boot.PPM + MainCharacter.body.getPosition().y * Boot.PPM, 0, Boot.ENEMY_ATTACK_BIT, Boot.CHARACTER_BIT);
    }

    @Override
    public void update(float deltaTime) {
        action();
        if (isBleeding) explosionEffect.update(deltaTime, body.getPosition().x, body.getPosition().y);
        if (useSkill_1)
            waterTrapBall.update(deltaTime, MainCharacter.body.getPosition().x, MainCharacter.body.getPosition().y);

        if (useSkill_2)
            rangeAttack.update(deltaTime);

        setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 100 / Boot.PPM, 300 / Boot.PPM, 300 / Boot.PPM);
        setRegion(getStatus(deltaTime));
    }

    @Override
    public void beDamaged() {
        isHurt = true;
        isBleeding = true;
    }

    public void setActionDuration() {
        if (isHeavyHurt) {
            if (actionDuration >= 1.5f) {
                isHeavyHurt = false;
                actionDuration = 0;
            }
            actionDuration += Gdx.graphics.getDeltaTime();
        }

        if (isAttacking) {
            if (actionDuration >= 1f) {
                isAttacking = false;
                actionDuration = 0;
            }
            actionDuration += Gdx.graphics.getDeltaTime();
        }

        if (isBleeding) {
            if (explosionEffect.actionDuration >= 0.25f) {
                isBleeding = false;
                explosionEffect.actionDuration = 0;
            }
            explosionEffect.actionDuration += Gdx.graphics.getDeltaTime();
        }

        if (useSkill_1) {
            if (actionDuration >= 2.4f) {
                useSkill_1 = false;
                actionDuration = 0;
            }
            actionDuration += Gdx.graphics.getDeltaTime();
        }

        if (useSkill_2) {
            if (rangeAttack.actionDuration >= 2.5f) {
                useSkill_2 = false;
                rangeAttack.actionDuration = 0;
                rangeAttack.destroyBody();
            }
            rangeAttack.actionDuration += Gdx.graphics.getDeltaTime();
        }
    }

    public void destroyBody() {
        if (setToDead && !isDead) {
            world.destroyBody(body);
            isDead = true;
        }
    }

    public void destroyWhenNextMap() {
        if (!isDead) {
            world.destroyBody(body);
            isDead = true;
        }
    }

    @Override
    public void draw(Batch batch) {
        if (isExist) super.draw(batch);
        if (isBleeding) explosionEffect.draw(batch);
        if (useSkill_1) waterTrapBall.draw(batch);
        if (useSkill_2) rangeAttack.draw(batch);
    }

    @Override
    public void reverseVelocity() {
        isReverse = true;
    }

}
