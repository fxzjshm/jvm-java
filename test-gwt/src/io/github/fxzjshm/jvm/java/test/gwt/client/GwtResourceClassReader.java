package io.github.fxzjshm.jvm.java.test.gwt.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.preloader.Blob;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.HashMap;
import java.util.Map;

import io.github.fxzjshm.jvm.java.ResourceClassReader;

public class GwtResourceClassReader extends ResourceClassReader {
    public static GwtResourceClassReader INSTANCE = new GwtResourceClassReader();

    public void dummy() {
        try {
            Gdx.app.debug("GwtResourceClassReader", "%s");
        } catch (Throwable ignored) {
        }

    }

    @Override
    public Map<String, byte[]> readClass() {
        Map<String, byte[]> ret = new HashMap<>();
        Preloader preloader = ((GwtApplication) Gdx.app).getPreloader();
        ObjectMap<String, Blob> binaries = preloader.binaries;
        for (ObjectMap.Entry<String, Blob> entry : binaries) {
            String path = entry.key;
            if (path.endsWith("class") || path.endsWith("bytecode")) {
                String fileName = path.substring(path.lastIndexOf('/') + 1).substring(0, path.lastIndexOf('.'));
                Gdx.app.debug("GwtResourceClassReader", "Loading " + fileName);
                Blob blob = entry.value;
                int l = blob.length();
                byte[] content = new byte[l];
                for (int i = 0; i < l; i++) {
                    content[i] = blob.get(i);
                }
                ret.put(fileName, content);
            }
        }
        return ret;
    }
}
