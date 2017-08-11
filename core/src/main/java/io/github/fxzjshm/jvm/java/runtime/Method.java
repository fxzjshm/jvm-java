package io.github.fxzjshm.jvm.java.runtime;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;
import io.github.fxzjshm.jvm.java.classfile.ClassFile;
import io.github.fxzjshm.jvm.java.classfile.MemberInfo;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttributeInfos;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.CodeAttribute;

public class Method {
    public ClassFile classFile;
    public MemberInfo info;
    public ByteArrayReader reader;

    public Method(MemberInfo info, ClassFile classFile) {
        this.info = info;
        this.classFile = classFile;
        for (AttributeInfos.AttributeInfo attributeInfo : info.attributes) {
            if (attributeInfo instanceof CodeAttribute) {
                reader = new ByteArrayReader(((CodeAttribute) attributeInfo).code);
            }
        }
    }
}
