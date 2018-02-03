package io.github.fxzjshm.jvm.java.loader;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.api.ClassLoader;
import io.github.fxzjshm.jvm.java.runtime.data.Class;

public class DefaultClassLoader implements ClassLoader{
    private EmuClassLoader emuClassLoader;
    private ExternalClassLoader externalClassLoader;

    public DefaultClassLoader() {

    }

    public Class loadClass(String name) throws IOException {
        try {
            return externalClassLoader.loadClass(name);
        } catch (Exception e) {
            return emuClassLoader.loadClass(name);
        }
    }
}
