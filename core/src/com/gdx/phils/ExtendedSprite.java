package com.gdx.phils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Surya on 09/03/17.
 */
public class ExtendedSprite extends Sprite {
    public final AbstractObject object;

    public ExtendedSprite(Texture texture, AbstractObject obj) {
        super(texture);
        this.object = obj;
        this.object.setSprite(this);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
