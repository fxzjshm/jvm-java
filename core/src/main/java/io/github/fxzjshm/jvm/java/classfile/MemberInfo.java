package io.github.fxzjshm.jvm.java.classfile;

import io.github.fxzjshm.jvm.java.classfile.ConstantPool.ConstantInfo;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttributeInfos.AttributeInfo;

public class MemberInfo {

    public ConstantPool cp;
    public int accessFlags;
    public int /* Constant pool index*/ nameIndex, descriptorIndex;
    public AttributeInfo[] attributes;
    public ClassFile classFile;
    public String name;

    public MemberInfo(ConstantPool cp, int accessFlags, int nameIndex, int descriptorIndex, AttributeInfo[] attributes, ClassFile classFile) {
        this.cp = cp;
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
        this.classFile = classFile;
        name = classFile.name + '.' + classFile.cp.infos[nameIndex].info;
    }
}
