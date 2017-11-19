package io.github.fxzjshm.jvm.java.loader;

import java.util.Hashtable;
import java.util.Map;

import io.github.fxzjshm.jvm.java.api.Classpath;

public class ByteArrayClasspath implements Classpath {
    public Map<String, byte[]> map = new Hashtable<>();

    @Override
    public byte[] readClass(String name) {
        return map.get(name);
    }
}
