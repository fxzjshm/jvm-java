package io.github.fxzjshm.jvm.java.runtime.ref;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.classfile.Bitmask;
import io.github.fxzjshm.jvm.java.runtime.data.Class;
import io.github.fxzjshm.jvm.java.runtime.data.RuntimeConstantPool;

public class SymRef {
    public RuntimeConstantPool cp;
    public String className;
    public Class clazz;

    public Class resolvedClass() throws IOException {
        if (clazz == null) {
            Class d = cp.clazz, c = d.loader.loadClass(className);
            if (!Bitmask.isAccessibleTo(c, d, d.classFile.accessFlags))
                throw new IllegalAccessError("Class " + c.classFile.name + " cannot access to class " + d.classFile.name + ".");
            clazz = c;
        }
        return clazz;
    }
}
