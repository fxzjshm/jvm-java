package io.github.fxzjshm.jvm.java.runtime.ref;

import java.util.Objects;

import io.github.fxzjshm.jvm.java.classfile.Bitmask;
import io.github.fxzjshm.jvm.java.classfile.cp.MemberRefInfo;
import io.github.fxzjshm.jvm.java.runtime.data.Field;
import io.github.fxzjshm.jvm.java.runtime.data.RuntimeConstantPool;
import io.github.fxzjshm.jvm.java.runtime.data.Class;


public class FieldRef extends MemberRef {
    private Field field;

    public FieldRef(RuntimeConstantPool rcp, MemberRefInfo refInfo) {
        super(rcp, refInfo);
    }

    public Field resolvedField() {
        if (field == null) {
            Class d = rcp.clazz, c = resolvedClass();
            Field field = lookupField(c, name, descriptor);
            if (field == null){
                throw new NoSuchFieldError(name + " : " + descriptor);
            }
            if (!Bitmask.isAccessibleTo(d, c, field.info.accessFlags))
                throw new IllegalAccessError("Cannot access " + c.classFile.name + "/" + field.info.name + " from " + d.classFile.name);
            this.field = field;
        }
        return this.field;
    }

    public static Field lookupField(Class c, String name, String descriptor) {
        for (Field field : c.fields)
            if (Objects.equals(field.info.name, name) && Objects.equals(field.info.descriptor, descriptor))
                return field;
        for (Class interfacei : c.interfaces) {
            Field field = lookupField(interfacei, name, descriptor);
            if (field != null) return field;
        }
        if (c.superClass != null) return lookupField(c.superClass, name, descriptor);
        return null;
    }
}
