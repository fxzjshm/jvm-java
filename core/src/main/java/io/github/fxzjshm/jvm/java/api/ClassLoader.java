package io.github.fxzjshm.jvm.java.api;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.runtime.VM;

public interface ClassLoader {
    Class loadClass(String name) throws IOException;
    VM vm();
}
