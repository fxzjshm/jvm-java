package io.github.fxzjshm.jvm.java.runtime.data;

import java.util.HashSet;

import io.github.fxzjshm.jvm.java.api.Class;
import io.github.fxzjshm.jvm.java.api.ClassLoader;
import io.github.fxzjshm.jvm.java.classfile.ClassFile;
import io.github.fxzjshm.jvm.java.classfile.MemberInfo;

public class EmuClass extends Class {

    public ClassFile classFile;

    public EmuClass(ClassFile cf, ClassLoader cl) {
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

        accessFlags = classFile.accessFlags;

        // TODO Reflect.classMap.put(cf.name, this);
    }

    @Override
    public boolean isSubClassOf(Class target) {
        for (Class c = superClass; c != null; c = c.superClass) {
            if (c == target) // TODO check here
                return true;
        }
        return false;
    }

    @Override
    public boolean isImplements(Class iface) {
        for (Class c = this; c != null; c = c.superClass)
            for (Class i : c.interfaces)
                if (i == iface || i.isSubInterfaceOf(iface))
                    return true;
        return false;
    }

    @Override
    public boolean isSubInterfaceOf(Class iface) {
        for (Class superInterface : interfaces)
            if (superInterface == iface || superInterface.isSubInterfaceOf(iface))
                return true;
        return false;
    }
}
