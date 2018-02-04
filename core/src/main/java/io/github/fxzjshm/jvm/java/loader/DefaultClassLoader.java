package io.github.fxzjshm.jvm.java.loader;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.api.ClassLoader;
import io.github.fxzjshm.jvm.java.api.Classpath;
import io.github.fxzjshm.jvm.java.api.Class;
import io.github.fxzjshm.jvm.java.api.ReflectHelper;
import io.github.fxzjshm.jvm.java.runtime.VM;

public class DefaultClassLoader implements ClassLoader {
    private VM vm;
    private EmuClassLoader emuClassLoader;
    private ExternalClassLoader externalClassLoader;

    public DefaultClassLoader(VM vm, Classpath classpath, ReflectHelper helper) {
        this.vm = vm;
        emuClassLoader = new EmuClassLoader(vm, classpath);
        externalClassLoader = new ExternalClassLoader(vm, helper);
    }

    public Class loadClass(String name) throws IOException {
        try {
            return externalClassLoader.loadClass(name);
        } catch (Exception e) {
            return emuClassLoader.loadClass(name);
        }
    }

    @Override
    public VM vm() {
        return vm;
    }
}
