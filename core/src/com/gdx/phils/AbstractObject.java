package com.gdx.phils;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by nairsk on 07/03/17.
 */

public abstract class AbstractObject implements Printable {
    protected enum STATE {
        NONE,
        WAITING, THINKING, EATING, FORK_RELEASED, FORK_ACQUIRED;
    }

    protected AbstractObject(String id) {
        this.name = id;
    }

    public final String name;
    public AbstractObject left;
    public AbstractObject right;
    private STATE state = STATE.NONE;

    public final Sprite sprite = new Sprite();
    public STATE getState() {
        return state;
    }

    public synchronized void setState(STATE state) {
        this.state = state;
    }

    @Override
    public void printLog() {
        System.out.print("\t" + name);
    }

    @Override
    public void status() {
        if (state == STATE.FORK_ACQUIRED) System.out.print("\t*" + name + "*");
        else System.out.print("\t" + name);
    }


}
