package io.github.fxzjshm.jvm.java.runtime;

import io.github.fxzjshm.jvm.java.classfile.MemberInfo;

public class Member {
    public MemberInfo info;
    public Class clazz;

    public Member(MemberInfo info, Class clazz) {
        this.info = info;
        this.clazz = clazz;
    }
}
