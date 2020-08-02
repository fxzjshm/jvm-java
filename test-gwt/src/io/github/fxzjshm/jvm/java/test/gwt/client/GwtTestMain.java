package io.github.fxzjshm.jvm.java.test.gwt.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import io.github.fxzjshm.jvm.java.ResourceClassReader;
import io.github.fxzjshm.jvm.java.TestMain;

public class GwtTestMain extends GwtApplication {
    @Override
    public GwtApplicationConfiguration getConfig() {
        return new GwtApplicationConfiguration(480, 320);
    }

    @Override
    public ApplicationListener createApplicationListener() {
        ResourceClassReader.INSTANCE = GwtResourceClassReader.INSTANCE;
        return new TestMain.TestMainApplicationAdapter();
    }
}
