package com.gdx.phils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nairsk on 07/03/17.
 */

public class DiningPhilosopher {

    final List<AbstractObject> list;

    final Fork[] forks;
    final Philosopher[] phils;

    public void parse() {
        System.out.println();
        list.forEach(abstractObject -> abstractObject.status());
        System.out.println();
    }


    public void parse(int times) {
        AbstractObject cur = list.get(0);
        int size = list.size() * times;

        for (int i = 0; i < size; i++) {
            cur.printLog();
            cur = cur.right;
        }

    }

    public DiningPhilosopher(final int size) {
        list = new ArrayList<>(size * 2);
        phils = new Philosopher[size];
        forks = new Fork[size];
        int lastIndex = size - 1;
        for (int i = 0, id = 1; i < size; i++, id++) {
            forks[i] = new Fork(id);
            phils[i] = new Philosopher(id);

            list.add(forks[i]);
            list.add(phils[i]);
        }

        forks[0].left = phils[lastIndex];
        phils[lastIndex].right = forks[0];

        for (int i = 0, sz = (size * 2) - 1; i < sz; i++) {

            AbstractObject a = list.get(i);
            AbstractObject b = list.get(i + 1);

            a.right = b;
            b.left = a;
        }

    }

    public static void main(String[] args) {

    }
}
