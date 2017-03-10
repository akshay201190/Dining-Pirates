package com.gdx.phils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by nairsk on 07/03/17.
 */

public class Philosopher extends AbstractObject implements Runnable {
    public static final int DIRECTIONAL_ANGLE = 50;
    private final long SLEEP_TIME;
    private final long EAT_TIME;

    private final Array<Movable> movables = new Array<>();

    public Philosopher(int id) {
        super("P" + id);
        EAT_TIME = 3000 + MathUtils.random(500);
        SLEEP_TIME = 2000 + MathUtils.random(500);
        setState(STATE.THINKING);
    }

    private void think() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eat() {
        try {
            Thread.sleep(EAT_TIME);
        } catch (InterruptedException e) {
        }
    }

    public void addMovable(Movable m) {
        this.movables.add(m);
    }

    @Override
    public void run() {
        synchronized (this) {
            while (true) {
                movables.forEach(movable -> movable.changeSprite(this.sprite, this.getState()));
                think();
                synchronized (left) {
                    left.setState(STATE.FORK_ACQUIRED);
                    this.setState(STATE.THINKING);
                    movables.forEach(movable -> movable.moveLeftFork(left.sprite, Movable.DIRECTION_NEAR));

                    synchronized (right) {
                        right.setState(STATE.FORK_ACQUIRED);
                        this.setState(STATE.EATING);
                        movables.forEach(movable -> {
                            movable.moveRightFork(right.sprite, Movable.DIRECTION_NEAR);
                            movable.changeSprite(this.sprite, this.getState());
                        });
                        eat();
                        right.setState(STATE.FORK_RELEASED);
                        this.setState(STATE.THINKING);
                        movables.forEach(movable -> {
                            movable.changeSprite(this.sprite, this.getState());
                            movable.moveRightFork(right.sprite, Movable.DIRECTION_AWAY);
                        });
                    }
                    left.setState(STATE.FORK_RELEASED);
                    movables.forEach(movable -> movable.moveLeftFork(left.sprite, Movable.DIRECTION_AWAY));

                }
            }
        }
    }
}
