package com.gdx.phils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;


public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;

    static final int RADIUS = 300, RADIUS_MIN = 150;
    static final int DT_ANGLE = 90;
    public static Texture thinking, waiting, eating, fork, noodles;
    Array<Sprite> sprites = new Array<>();
    Array<ExtendedSprite> forks = new Array<>();
    Array<ExtendedSprite> phils = new Array<>();
    Array<Sprite> sphagetti = new Array<>();

    ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;

    @Override
    public void create() {
        cx = Gdx.app.getGraphics().getWidth() / 2f;
        cy = Gdx.app.getGraphics().getHeight() / 2f;

        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(cx, cy, 0);

        thinking = new Texture("thinking.jpg");
        waiting = new Texture("waiting.jpg");
        eating = new Texture("eathing.jpg");
        fork = new Texture("fork2.png");
        noodles = new Texture("noodles.png");

        shapeRenderer = new ShapeRenderer();
        /**
         * game logic
         */
        int size = 5;
        DiningPhilosopher dp = new DiningPhilosopher(size);

        for (Fork f : dp.forks) {
            ExtendedSprite sprite = new ExtendedSprite(fork, f);
            sprite.flip(false, true);
            forks.add(sprite);
        }

        for (Philosopher phil : dp.phils) {
            ExtendedSprite sprite = new ExtendedSprite(thinking, phil);
            phils.add(sprite);
        }

        for (int i = 0; i < forks.size; i++) {
            sprites.add(forks.get(i));
            sprites.add(phils.get(i));

            Sprite sprite = new Sprite(noodles);
            sphagetti.add(sprite);
        }

        System.out.println();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(dp.phils[i]);
            thread.start();
        }

        final int deltaAngle = 360 / sprites.size;

        for (int i = 0; i < sprites.size; i++) {
            int angle = i * deltaAngle;
            Sprite es = sprites.get(i);

            float x, y, w, h;
            float r = RADIUS;
            x = cx + r * MathUtils.cosDeg(angle);
            y = cy + r * MathUtils.sinDeg(angle);
            w = es.getRegionWidth();
            h = es.getRegionHeight();
            es.setPosition(x - w / 2f, y - h / 2f);
            es.setRotation(angle - DT_ANGLE);
        }

        for (int i = 0; i < phils.size; i++) {
            Sprite es = phils.get(i);
            float angle = es.getRotation() + DT_ANGLE;
            Sprite sprite = sphagetti.get(i);

            float x, y, w, h;
            float r = RADIUS_MIN;
            x = cx + r * MathUtils.cosDeg(angle);
            y = cy + r * MathUtils.sinDeg(angle);
            w = sprite.getRegionWidth();
            h = sprite.getRegionHeight();
            sprite.setPosition(x - w / 2f, y - h / 2f);
            sprites.add(sprite);
        }
    }

    float cx, cy;

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setColor(0, 0, 0, .3f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(cx, cy, 300);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprites.forEach(extendedSprite -> extendedSprite.draw(batch));
        batch.end();



    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
