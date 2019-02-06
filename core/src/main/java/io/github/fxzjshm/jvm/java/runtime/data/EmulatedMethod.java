package io.github.fxzjshm.jvm.java.runtime.data;

import io.github.fxzjshm.jvm.java.api.VMethod;
import io.github.fxzjshm.jvm.java.api.VClass;
import io.github.fxzjshm.jvm.java.classfile.MemberInfo;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttributeInfos;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.CodeAttribute;

public class EmulatedMethod extends EmulatedMember implements VMethod {

    public byte[] code;

    public EmulatedMethod(MemberInfo info, VClass clazz) {
    	super(info,clazz);
        for (AttributeInfos.AttributeInfo attributeInfo : info.attributes) {
            if (attributeInfo instanceof CodeAttribute) {
                code = ((CodeAttribute) attributeInfo).code;
            }
        }
        //TODO Reflect.methodMap.put(info.name, this);
    }
}
