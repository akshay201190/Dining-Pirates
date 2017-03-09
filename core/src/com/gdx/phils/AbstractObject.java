package com.gdx.phils;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

/**
 * Created by nairsk on 07/03/17.
 */

public abstract class AbstractObject implements Printable {

    static final Random random = new Random();

    static enum STATE {
        waiting, thinking, eating,
        fork_release, fork_acquired;
    }

    protected AbstractObject(String id) {
        this.name = id;
        EAT_TIME = 3000 + random.nextInt(500);
        SLEEP_TIME = 2000 + random.nextInt(500);
    }

    public final long SLEEP_TIME;
    public final long EAT_TIME;
    public final String name;
    public AbstractObject left;
    public AbstractObject right;

    public volatile STATE state;

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public volatile Sprite sprite;


    @Override
    public void printLog() {
        System.out.print("\t" + name);
    }

    @Override
    public void status() {
        if (state == STATE.fork_acquired) System.out.print("\t*" + name + "*");
        else System.out.print("\t" + name);

    }
}
