package io.github.fxzjshm.jvm.java.runtime;

import io.github.fxzjshm.jvm.java.classfile.MemberInfo;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttributeInfos;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.CodeAttribute;

public class Method extends Member {
    // public static Map<String, Method> reflectMap = new Hashtable<>();

    public byte[] code;

    public Method(MemberInfo info, Class clazz) {
        super(info,clazz);
        for (AttributeInfos.AttributeInfo attributeInfo : info.attributes) {
            if (attributeInfo instanceof CodeAttribute) {
                code = ((CodeAttribute) attributeInfo).code;
            }
        }

    }
}
