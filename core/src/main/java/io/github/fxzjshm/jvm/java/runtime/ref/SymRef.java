package io.github.fxzjshm.jvm.java.runtime.ref;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.api.Class;
import io.github.fxzjshm.jvm.java.classfile.Bitmask;
import io.github.fxzjshm.jvm.java.runtime.data.RuntimeConstantPool;

public class SymRef {
    public RuntimeConstantPool rcp;
    public String className;
    public Class clazz;

    public SymRef(RuntimeConstantPool cp) {
        this.rcp = cp;
    }

    public Class resolvedClass() {
        if (clazz == null) {
            resolveClassRef();
        }
        return clazz;
    }

    private void resolveClassRef(){
        try {
            Class d = rcp.clazz, c = d.loader.loadClass(className);
            if (!Bitmask.isAccessibleTo(c, d, d.classFile.accessFlags))
                throw new IllegalAccessError("Class " + c.classFile.name + " cannot access to class " + d.classFile.name + ".");
            clazz = c;
        } catch (IOException e) {
            throw new RuntimeException("Cannot load class " + className, e);
        }
    }
}
