package io.github.fxzjshm.jvm.java.runtime.ref;

import io.github.fxzjshm.jvm.java.classfile.cp.MemberRefInfo;
import io.github.fxzjshm.jvm.java.runtime.data.RuntimeConstantPool;

public class MemberRef extends SymRef {
    public String name, descriptor;

    public MemberRef(RuntimeConstantPool rcp, MemberRefInfo info) {
        super(rcp);
        name = info.nameAndType.name;
        descriptor = info.nameAndType.descriptor;
    }
}
