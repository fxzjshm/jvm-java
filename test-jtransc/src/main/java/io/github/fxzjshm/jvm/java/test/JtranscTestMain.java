package io.github.fxzjshm.jvm.java.test;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import io.github.fxzjshm.jvm.java.TestMain;

public class JtranscTestMain {
    public static void main(String[] args) {
        new LwjglApplication(new TestMain.TestMainApplicationAdapter());
    }
}
