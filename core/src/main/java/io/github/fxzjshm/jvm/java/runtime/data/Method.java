package io.github.fxzjshm.jvm.java.runtime.data;

import io.github.fxzjshm.jvm.java.classfile.MemberInfo;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttributeInfos;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.CodeAttribute;
import io.github.fxzjshm.jvm.java.runtime.Reflect;

public class Method extends Member {

    public byte[] code;

    public Method(MemberInfo info, Class clazz) {
        super(info, clazz);
        for (AttributeInfos.AttributeInfo attributeInfo : info.attributes) {
            if (attributeInfo instanceof CodeAttribute) {
                code = ((CodeAttribute) attributeInfo).code;
            }
        }
        Reflect.methodMap.put(info.name, this);
    }
}
