package io.github.fxzjshm.jvm.java.api;

import java.util.Set;

import io.github.fxzjshm.jvm.java.classfile.Bitmask;

public abstract class VClass {
    public int accessFlags;
    public VClassLoader loader;

    // remember to wrap all external methods/fields/classes/etc......
    public Set<VMethod> methods;
    public Set<VField> fields;
    public VClass superClass;
    public VClass[] interfaces;

    public String name;
    public String packageName;

    public boolean isSubClassOf(VClass target) {
        for (VClass c = superClass; c != null; c = c.superClass) {
            if (c == target) // TODO check here
                return true;
        }
        return false;
    }

    public boolean isAssignableFrom(VClass other) {
        if (other == this)
            return true;
        if ((accessFlags & Bitmask.ACC_INTERFACE) == 0) // this class is not an interface
            return other.isSubClassOf(this);
        else
            return other.isImplements(this);
    }

    public boolean isImplements(VClass iface) {
        for (VClass c = this; c != null; c = c.superClass)
            for (VClass i : c.interfaces)
                if (i == iface || i.isSubInterfaceOf(iface))
                    return true;
        return false;
    }

    public boolean isSubInterfaceOf(VClass iface) {
        for (VClass superInterface : interfaces)
            if (superInterface == iface || superInterface.isSubInterfaceOf(iface))
                return true;
        return false;
    }
}
