package com.gdx.phils;

/**
 * Created by nairsk on 07/03/17.
 */

public class Philosopher extends AbstractObject implements Runnable {
    public static final int DIRECTIONAL_ANGLE = 50;

    public Philosopher(int id) {
        super("P" + id);
    }

    @Override
    public void run() {
        while (true) {
            this.state = STATE.thinking;
            synchronized (left) {
                left.state = STATE.fork_acquired;
                this.state = STATE.waiting;
                sprite.setTexture(MyGdxGame.waiting);
                synchronized (right) {
                    right.state = STATE.fork_acquired;
                    state = STATE.eating;
                    sprite.setTexture(MyGdxGame.eating);
                    try {
                        Thread.sleep(EAT_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    right.state = STATE.fork_release;
                    sprite.setTexture(MyGdxGame.waiting);
                }
                left.state = STATE.fork_release;
                this.state = STATE.thinking;
                sprite.setTexture(MyGdxGame.thinking);
            }


            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
