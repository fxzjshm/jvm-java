package io.github.fxzjshm.jvm.java.loader;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.api.ClassLoader;
import io.github.fxzjshm.jvm.java.api.Class;
import io.github.fxzjshm.jvm.java.api.ReflectHelper;
import io.github.fxzjshm.jvm.java.runtime.VM;
import io.github.fxzjshm.jvm.java.runtime.data.ExternalClass;

public class ExternalClassLoader implements ClassLoader { //TODO impl
    VM vm;
    ReflectHelper helper;

    ExternalClassLoader(VM vm,ReflectHelper reflectHelper) {
        this.vm=vm;
        helper = reflectHelper;
    }

    @Override
    public Class loadClass(String name) throws IOException {
        return new ExternalClass(this,helper.Class_forName(name));
    }

    @Override
    public VM vm() {
        return vm;
    }
}
