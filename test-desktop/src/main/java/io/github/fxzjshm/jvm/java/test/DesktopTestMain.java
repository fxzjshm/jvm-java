package io.github.fxzjshm.jvm.java.test;

import com.badlogic.gdx.backends.headless.HeadlessApplication;

import io.github.fxzjshm.jvm.java.TestMain;

public class DesktopTestMain {
    public static void main(String[] args) {
        new HeadlessApplication(new TestMain.TestMainApplicationAdapter());
    }
}
