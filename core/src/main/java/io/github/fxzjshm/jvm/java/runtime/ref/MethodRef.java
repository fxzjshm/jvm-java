package io.github.fxzjshm.jvm.java.runtime.ref;

import io.github.fxzjshm.jvm.java.classfile.cp.MemberRefInfo;
import io.github.fxzjshm.jvm.java.runtime.data.RuntimeConstantPool;

public class MethodRef extends MemberRef {
    public MethodRef(RuntimeConstantPool rcp, MemberRefInfo info) {
        super(rcp, info);
    }
}
