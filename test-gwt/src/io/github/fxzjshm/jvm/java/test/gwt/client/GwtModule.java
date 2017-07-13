package io.github.fxzjshm.jvm.java.test.gwt.client;

import com.google.gwt.core.client.EntryPoint;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.Main;

public class GwtModule implements EntryPoint{

    @Override
    public void onModuleLoad() {
        try {
            Main.main(null);
        } catch (IOException ignored) {
        }
    }

}