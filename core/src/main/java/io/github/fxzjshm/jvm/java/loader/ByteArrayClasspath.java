package io.github.fxzjshm.jvm.java.loader;

import java.util.Hashtable;
import java.util.Map;

import io.github.fxzjshm.jvm.java.api.VClasspath;

public class ByteArrayClasspath implements VClasspath {
    public Map<String, byte[]> map = new Hashtable<>();

    @Override
    public byte[] readClass(String name) {
        return map.get(name);
    }
}
