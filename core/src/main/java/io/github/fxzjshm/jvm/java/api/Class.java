package io.github.fxzjshm.jvm.java.api;

import java.util.Set;

import io.github.fxzjshm.jvm.java.classfile.Bitmask;
import io.github.fxzjshm.jvm.java.runtime.data.Field;
import io.github.fxzjshm.jvm.java.runtime.data.Method;

public abstract class Class {
    public int accessFlags;
    public ClassLoader loader;

    // remember to wrap all external methods/fields/classes/etc......
    public Set<Method> methods;
    public Set<Field> fields;
    public Class superClass;
    public Class[] interfaces;

    public String name;
    public String packageName;

    public boolean isSubClassOf(Class target) {
        for (Class c = superClass; c != null; c = c.superClass) {
            if (c == target) // TODO check here
                return true;
        }
        return false;
    }

    public boolean isAssignableFrom(Class other) {
        if (other == this)
            return true;
        if ((accessFlags & Bitmask.ACC_INTERFACE) == 0) // this class is not an interface
            return other.isSubClassOf(this);
        else
            return other.isImplements(this);
    }

    public boolean isImplements(Class iface) {
        for (Class c = this; c != null; c = c.superClass)
            for (Class i : c.interfaces)
                if (i == iface || i.isSubInterfaceOf(iface))
                    return true;
        return false;
    }

    public boolean isSubInterfaceOf(Class iface) {
        for (Class superInterface : interfaces)
            if (superInterface == iface || superInterface.isSubInterfaceOf(iface))
                return true;
        return false;
    }
}
