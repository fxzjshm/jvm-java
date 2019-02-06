package io.github.fxzjshm.jvm.java.runtime.ref;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.api.VClass;
import io.github.fxzjshm.jvm.java.classfile.Bitmask;
import io.github.fxzjshm.jvm.java.runtime.data.RuntimeConstantPool;

public class SymRef {
    public RuntimeConstantPool rcp;
    public String className;
    public VClass clazz;

    public SymRef(RuntimeConstantPool cp) {
        this.rcp = cp;
    }

    public VClass resolvedClass() {
        if (clazz == null) {
            resolveClassRef();
        }
        return clazz;
    }

    private void resolveClassRef(){
        try {
            VClass d = rcp.clazz, c = d.loader.loadClass(className);
            if (!Bitmask.isAccessibleTo(c, d, d.accessFlags))
                throw new IllegalAccessError("Class " + c.name + " cannot access to class " + d.name + ".");
            clazz = c;
        } catch (IOException e) {
            throw new RuntimeException("Cannot load class " + className, e);
        }
    }
}
