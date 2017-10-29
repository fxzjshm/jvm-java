package io.github.fxzjshm.jvm.java.runtime.data;

import java.util.Objects;

import io.github.fxzjshm.jvm.java.classfile.MemberInfo;
import io.github.fxzjshm.jvm.java.runtime.Reflect;

public class Field extends Member {
    public int slotId, constValueIndex = -1;
    public final boolean isLongOrDouble;

    public Field(MemberInfo info, Class clazz) {
        super(info, clazz);
        isLongOrDouble = Objects.equals(info.descriptor, "L") || Objects.equals(info.descriptor, "D");
        Reflect.fieldMap.put(info.name, this);
    }

}
