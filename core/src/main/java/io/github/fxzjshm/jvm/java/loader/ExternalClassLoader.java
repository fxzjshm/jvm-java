package io.github.fxzjshm.jvm.java.loader;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.api.VClassLoader;
import io.github.fxzjshm.jvm.java.api.VClass;
import io.github.fxzjshm.jvm.java.api.ExternalReflectHelper;
import io.github.fxzjshm.jvm.java.runtime.VirtualMachine;
import io.github.fxzjshm.jvm.java.runtime.data.ExternalClass;

public class ExternalClassLoader extends VClassLoader { //TODO impl
    ExternalReflectHelper helper;

    ExternalClassLoader(VirtualMachine vm, ExternalReflectHelper reflectHelper) {
        this.vm=vm;
        helper = reflectHelper;
    }

    @Override
    public VClass loadClass(String name) throws IOException {
        return new ExternalClass(this,helper.Class_forName(name));
    }
}
