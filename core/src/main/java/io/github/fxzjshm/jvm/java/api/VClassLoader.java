package io.github.fxzjshm.jvm.java.api;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;
import io.github.fxzjshm.jvm.java.loader.DefaultClassLoader;
import io.github.fxzjshm.jvm.java.runtime.VM;
import io.github.fxzjshm.jvm.java.runtime.data.EmulatedClass;
import io.github.fxzjshm.jvm.java.runtime.data.ExternalClass;

public abstract class VClassLoader {
    public VM vm;

    /**
     * Load the class by its name.
     * @see DefaultClassLoader for default implement
     * @param name the name of the class to be loaded,
     * @return loaded {@link VClass}, either {@link EmulatedClass} or {@link ExternalClass} (currently)
     * @throws IOException for {@link EmulatedClass} because it uses {@link ByteArrayReader} which requires it
     */
    public abstract VClass loadClass(String name) throws IOException;
}
