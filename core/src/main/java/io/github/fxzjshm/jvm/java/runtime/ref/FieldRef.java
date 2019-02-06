package io.github.fxzjshm.jvm.java.runtime.ref;

import java.util.Objects;

import io.github.fxzjshm.jvm.java.api.VField;
import io.github.fxzjshm.jvm.java.api.VClass;
import io.github.fxzjshm.jvm.java.classfile.Bitmask;
import io.github.fxzjshm.jvm.java.classfile.cp.MemberRefInfo;
import io.github.fxzjshm.jvm.java.runtime.data.RuntimeConstantPool;

public class FieldRef extends MemberRef {
    private VField field;

    public FieldRef(RuntimeConstantPool rcp, MemberRefInfo refInfo) {
        super(rcp, refInfo);
    }

    public VField resolvedField() {
        if (field == null) {
            VClass d = rcp.clazz, c = resolvedClass();
            VField field = lookupField(c, name, descriptor);
            if (field == null) {
                throw new NoSuchFieldError(name + " : " + descriptor);
            }
            if (!Bitmask.isAccessibleTo(d, c, field.accessFlags()))
                throw new IllegalAccessError("Cannot access " + c.name + "/" + field.name() + " from " + d.name);
            this.field = field;
        }
        return this.field;
    }

    public static VField lookupField(VClass c, String name, String descriptor) {
        for (VField field : c.fields)
            if (Objects.equals(field.name(), name) && Objects.equals(field.descriptor(), descriptor))
                return field;
        for (VClass interfacei : c.interfaces) {
            VField field = lookupField(interfacei, name, descriptor);
            if (field != null) return field;
        }
        if (c.superClass != null) return lookupField(c.superClass, name, descriptor);
        return null;
    }

    public boolean isInstanceOf(VClass clazz) {
        resolvedField();
        return clazz.isAssignableFrom(this.clazz);
    }
}
