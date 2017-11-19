package io.github.fxzjshm.jvm.java.loader;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.runtime.data.Class;

public interface ClassLoader {
    public Class loadClass(String name) throws IOException;
}
