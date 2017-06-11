package io.github.fxzjshm.jvm.java.classfile;

public class MemberInfo {

    public ConstantPool cp;
    public int accessFlags;
    protected int /* Constant pool index*/ nameIndex,descriptorIndex;
    public AttributeInfo[] attributes;
}
