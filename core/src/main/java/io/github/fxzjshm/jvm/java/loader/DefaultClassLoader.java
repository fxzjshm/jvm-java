package io.github.fxzjshm.jvm.java.loader;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.api.VClassLoader;
import io.github.fxzjshm.jvm.java.api.VClasspath;
import io.github.fxzjshm.jvm.java.api.VClass;
import io.github.fxzjshm.jvm.java.api.ExternalReflectHelper;
import io.github.fxzjshm.jvm.java.runtime.VM;

public class DefaultClassLoader extends VClassLoader {
    private EmulatedClassLoader emulatedClassLoader;
    private ExternalClassLoader externalClassLoader;

    public DefaultClassLoader(VM vm, VClasspath classpath, ExternalReflectHelper helper) {
        this.vm = vm;
        emulatedClassLoader = new EmulatedClassLoader(vm, classpath);
        externalClassLoader = new ExternalClassLoader(vm, helper);
    }

    public VClass loadClass(String name) throws IOException {
        VClass clazz;
        try { // try to load external class first, TODO: check this
            clazz = externalClassLoader.loadClass(name);
        } catch (Exception e) {
            clazz = emulatedClassLoader.loadClass(name);
        }
        vm.reflect.classMap.put(clazz.name, clazz);// TODO should this be moved to ClassLoader?
        return clazz;
    }
}
