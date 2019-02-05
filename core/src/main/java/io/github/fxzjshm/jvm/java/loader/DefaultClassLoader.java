package io.github.fxzjshm.jvm.java.loader;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.api.ClassLoader;
import io.github.fxzjshm.jvm.java.api.Classpath;
import io.github.fxzjshm.jvm.java.api.Class;
import io.github.fxzjshm.jvm.java.api.ExternalReflectHelper;
import io.github.fxzjshm.jvm.java.runtime.VM;

public class DefaultClassLoader extends ClassLoader {
    private EmulatedClassLoader emulatedClassLoader;
    private ExternalClassLoader externalClassLoader;

    public DefaultClassLoader(VM vm, Classpath classpath, ExternalReflectHelper helper) {
        this.vm = vm;
        emulatedClassLoader = new EmulatedClassLoader(vm, classpath);
        externalClassLoader = new ExternalClassLoader(vm, helper);
    }

    public Class loadClass(String name) throws IOException {
        Class clazz;
        try { // try to load external class first, TODO: check this
            clazz = externalClassLoader.loadClass(name);
        } catch (Exception e) {
            clazz = emulatedClassLoader.loadClass(name);
        }
        vm.reflect.classMap.put(clazz.name, clazz);// TODO should this be moved to ClassLoader?
        return clazz;
    }
}
