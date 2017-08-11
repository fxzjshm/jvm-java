package io.github.fxzjshm.jvm.java.classfile;

import io.github.fxzjshm.jvm.java.classfile.ConstantPool.ConstantInfo;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttributeInfos.AttributeInfo;

public class MemberInfo {

    public ConstantInfo[] cp;
    public int accessFlags;
    protected int /* Constant pool index*/ nameIndex, descriptorIndex;
    public AttributeInfo[] attributes;
    public ClassFile classFile;
}
