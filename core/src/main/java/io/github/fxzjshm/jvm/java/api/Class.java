package io.github.fxzjshm.jvm.java.api;

import java.util.Set;

import io.github.fxzjshm.jvm.java.classfile.Bitmask;
import io.github.fxzjshm.jvm.java.runtime.data.Field;
import io.github.fxzjshm.jvm.java.runtime.data.Method;
import io.github.fxzjshm.jvm.java.runtime.data.RuntimeConstantPool;

public abstract class Class {
    public int accessFlags;
    public ClassLoader loader;
    public Set<Method> methods;
    public Set<Field> fields;
    public Class superClass;
    public Class[] interfaces;
    public int instanceSlotCount;
    public int staticSlotCount;
    public Object[] staticVars;
    public RuntimeConstantPool rtcp;
    public java.lang.Class jClass; // TODO impl

    public abstract boolean isSubClassOf(Class target);

    public boolean isAssignableFrom(Class other) {
        if (other == this)
            return true;
        if ((accessFlags & Bitmask.ACC_INTERFACE) == 0)
            return other.isSubClassOf(this);
        else
            return other.isImplements(this);
    }

    public abstract boolean isImplements(Class iface);

    public abstract boolean isSubInterfaceOf(Class iface);
}
