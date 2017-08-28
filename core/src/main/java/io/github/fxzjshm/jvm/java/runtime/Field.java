package io.github.fxzjshm.jvm.java.runtime;

import io.github.fxzjshm.jvm.java.classfile.MemberInfo;

public class Field extends Member {
    // public static Map<String, Field> reflectMap = new Hashtable<>();

    public Field(MemberInfo info, Class clazz) {
        super(info, clazz);
    }
}
