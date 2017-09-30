package io.github.fxzjshm.jvm.java.runtime.data;

import java.util.Objects;

import io.github.fxzjshm.jvm.java.classfile.MemberInfo;

public class Field extends Member {
    // public static Map<String, Field> reflectMap = new Hashtable<>();
    public int slotId, constValueIndex = -1;
    public final boolean isLongOrDouble;

    public Field(MemberInfo info, Class clazz) {
        super(info, clazz);
        isLongOrDouble = Objects.equals(info.descriptor, "L") || Objects.equals(info.descriptor, "D");
    }

}
