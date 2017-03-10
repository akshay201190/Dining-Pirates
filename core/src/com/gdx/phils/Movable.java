package com.gdx.phils;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Surya on 10/03/17.
 */
public interface Movable {
    public static final int DIRECTION_NEAR = 1, DIRECTION_AWAY = -1;

    void moveLeftFork(Sprite sprite, int direction);

    void moveRightFork(Sprite sprite, int direction);

    void changeSprite(Sprite sprite, AbstractObject.STATE state);

}
