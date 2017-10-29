package io.github.fxzjshm.jvm.java.classfile;

import io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttributeInfos.AttributeInfo;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.ConstantValueAttribute;

public class MemberInfo {

    public ConstantPool cp;
    public int accessFlags;
    public int /* Constant pool index*/ nameIndex, descriptorIndex, constantValueIndex;
    public AttributeInfo[] attributes;
    public ClassFile classFile;
    public String name, descriptor;
    // public ConstantValueAttribute constantValue;

    public MemberInfo(ConstantPool cp, int accessFlags, int nameIndex, int descriptorIndex, AttributeInfo[] attributes, ClassFile classFile) {
        this.cp = cp;
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
        this.classFile = classFile;
        name = classFile.name + '.' + classFile.cp.infos[nameIndex].info;
        descriptor = (String) classFile.cp.infos[descriptorIndex].info;

        for (AttributeInfo attr : attributes) {
            if (attr instanceof ConstantValueAttribute) {
                constantValueIndex = ((ConstantValueAttribute) attr).constantValueIndex;
                break; // Should be only one per field;
            }
        }
    }
}
