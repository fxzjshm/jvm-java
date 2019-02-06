package io.github.fxzjshm.jvm.java.runtime.ref;

import java.util.Objects;

import io.github.fxzjshm.jvm.java.api.VMethod;
import io.github.fxzjshm.jvm.java.api.VClass;
import io.github.fxzjshm.jvm.java.classfile.Bitmask;
import io.github.fxzjshm.jvm.java.classfile.cp.MemberRefInfo;
import io.github.fxzjshm.jvm.java.runtime.data.RuntimeConstantPool;

public class MethodRef extends MemberRef {
    private VMethod method = null;

    public MethodRef(RuntimeConstantPool rcp, MemberRefInfo info) {
        super(rcp, info);
    }

    public VMethod resolvedMethod() {
        if (method == null) {
            resolveMethodRef();
        }
        return method;
    }

    private void resolveMethodRef() {
        VClass d = rcp.clazz;
        VClass c = resolvedClass();
        if ((c.accessFlags & Bitmask.ACC_INTERFACE) != 0) {
            throw new IncompatibleClassChangeError(c.name + " should not be an interface");
        }
        VMethod method = MethodLookup.lookupMethod(c, name, descriptor);
        if (method == null) {
            throw new NoSuchMethodError("Cannot find " + className + '.' + name + '(' + descriptor + ')');
        }
        if (!Bitmask.isAccessibleTo(d, c, method.accessFlags())) {
            throw new IllegalAccessError("Cannot access " + c.name + '.' + method.name() + "from" + d.name);
        }
    }

    private static class MethodLookup {
        private static VMethod lookupMethod(VClass clazz, String name, String descriptor) {
            VMethod method = lookupMethodInClass(clazz, name, descriptor);
            if (method == null) {
                return lookupMethodInInterfaces(clazz, name, descriptor);
            } else {
                return method;
            }
        }

        private static VMethod lookupMethodInClass(VClass clazz, String name, String descriptor) {
            for (VClass c = clazz; c != null; c = c.superClass) {
                for (VMethod method : c.methods) {
                    if (checkMethod(method, name, descriptor)) {
                        return method;
                    }
                }
            }
            return null;
        }

        private static VMethod lookupMethodInInterfaces(VClass clazz, String name, String descriptor) {
            for (VClass iface : clazz.interfaces) {
                for (VMethod method : iface.methods) {
                    if (checkMethod(method, name, descriptor)) {
                        return method;
                    }
                }
                VMethod method = lookupMethodInInterfaces(iface, name, descriptor);
                if (method != null) return method;
            }
            return null;

        }

        private static boolean checkMethod(VMethod method, String name, String descriptor) {
            return Objects.equals(method.name(), name) && Objects.equals(method.descriptor(), descriptor);
        }
    }
}
