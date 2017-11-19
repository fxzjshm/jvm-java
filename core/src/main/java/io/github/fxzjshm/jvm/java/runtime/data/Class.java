package io.github.fxzjshm.jvm.java.runtime.data;

import java.util.HashSet;
import java.util.Set;

import io.github.fxzjshm.jvm.java.classfile.Bitmask;
import io.github.fxzjshm.jvm.java.classfile.ClassFile;
import io.github.fxzjshm.jvm.java.classfile.MemberInfo;
import io.github.fxzjshm.jvm.java.loader.ClassLoader;
import io.github.fxzjshm.jvm.java.runtime.Reflect;

public abstract class Class {

    public ClassLoader loader;
    public Set<Method> methods;
    public Set<Field> fields;
    public ClassFile classFile;
    public Class superClass, interfaces[];
    public int instanceSlotCount, staticSlotCount;
    public Object[] staticVars;
    public RuntimeConstantPool rtcp;

    public Class(ClassFile cf, ClassLoader cl) {
        loader = cl;
        classFile = cf;

        methods = new HashSet<>(classFile.methods.length);
        for (MemberInfo methodInfo : classFile.methods) {
            methods.add(new Method(methodInfo, this));
        }

        fields = new HashSet<>(classFile.fields.length);
        for (MemberInfo fieldInfo : classFile.fields) {
            fields.add(new Field(fieldInfo, this));
        }

        rtcp = new RuntimeConstantPool(this, cf.cp);

        Reflect.classMap.put(cf.name, this);
    }

    public boolean isSubClassOf(Class target) {
        for (Class c = superClass; c != null; c = c.superClass) {
            if (c == target)
                return true;
        }
        return false;
    }

    public boolean isAssignableFrom(Class other) {
        if (other == this)
            return true;
        if ((classFile.accessFlags & Bitmask.ACC_INTERFACE) == 0)
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
