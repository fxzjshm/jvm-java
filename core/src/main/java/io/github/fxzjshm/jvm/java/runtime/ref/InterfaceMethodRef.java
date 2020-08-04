package io.github.fxzjshm.jvm.java.runtime.ref;

import io.github.fxzjshm.jvm.java.api.VClass;
import io.github.fxzjshm.jvm.java.api.VMethod;
import io.github.fxzjshm.jvm.java.classfile.Bitmask;
import io.github.fxzjshm.jvm.java.classfile.cp.MemberRefInfo;
import io.github.fxzjshm.jvm.java.runtime.data.RuntimeConstantPool;

import static io.github.fxzjshm.jvm.java.runtime.ref.MethodRef.MethodLookup.checkMethod;
import static io.github.fxzjshm.jvm.java.runtime.ref.MethodRef.MethodLookup.lookupMethodInInterfaces;

public class InterfaceMethodRef extends MemberRef {
    private VMethod method = null;

    public InterfaceMethodRef(RuntimeConstantPool rcp, MemberRefInfo info) {
        super(rcp, info);
    }

    public VMethod resolvedMethod() {
        if (method == null) {
            resolveInterfaceMethodRef();
        }
        return method;
    }

    private void resolveInterfaceMethodRef() {
        VClass d = rcp.clazz;
        VClass c = resolvedClass();
        if ((c.accessFlags & Bitmask.ACC_INTERFACE) == 0) {
            throw new IncompatibleClassChangeError(c.name + " should be an interface");
        }
        VMethod method = InterfaceMethodLookup.lookupInterfaceMethod(c, name, descriptor);
        if (method == null) {
            throw new NoSuchMethodError("Cannot find " + className + '.' + name + '(' + descriptor + ')');
        }
        if (!Bitmask.isAccessibleTo(d, c, method.accessFlags())) {
            throw new IllegalAccessError("Cannot access " + c.name + '.' + method.name() + "from" + d.name);
        }
    }

    public static class InterfaceMethodLookup {
        public static VMethod lookupInterfaceMethod(VClass c, String name, String descriptor) {
            for (VMethod method : c.methods) {
                if (checkMethod(method, name, descriptor)) {
                    return method;
                }
            }
            return lookupMethodInInterfaces(c, name, descriptor);
        }
    }
}
