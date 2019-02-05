package io.github.fxzjshm.jvm.java.loader;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.api.ClassLoader;
import io.github.fxzjshm.jvm.java.api.Class;
import io.github.fxzjshm.jvm.java.api.ExternalReflectHelper;
import io.github.fxzjshm.jvm.java.runtime.VM;
import io.github.fxzjshm.jvm.java.runtime.data.ExternalClass;

public class ExternalClassLoader extends ClassLoader { //TODO impl
    ExternalReflectHelper helper;

    ExternalClassLoader(VM vm,ExternalReflectHelper reflectHelper) {
        this.vm=vm;
        helper = reflectHelper;
    }

    @Override
    public Class loadClass(String name) throws IOException {
        return new ExternalClass(this,helper.Class_forName(name));
    }
}
