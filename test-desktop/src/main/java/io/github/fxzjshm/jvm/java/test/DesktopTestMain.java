package io.github.fxzjshm.jvm.java.test;

import com.badlogic.gdx.backends.headless.HeadlessApplication;

import io.github.fxzjshm.jvm.java.TestMain;

public class DesktopTestMain {
    public static void main(String[] args) {
        TestMain.TestMainApplicationAdapter adapter = new TestMain.TestMainApplicationAdapter();
        HeadlessApplication app = new HeadlessApplication(adapter);
        // This runs on a separate thread, so check the signal until it finishes.
        while (adapter.running) {
            Thread.yield();
        }
        if (adapter.throwable != null) {
            throw new RuntimeException(adapter.throwable);
        }
    }
}
