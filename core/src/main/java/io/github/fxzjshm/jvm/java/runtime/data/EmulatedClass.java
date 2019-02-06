package io.github.fxzjshm.jvm.java.runtime.data;

import java.util.HashSet;

import io.github.fxzjshm.jvm.java.api.VMethod;
import io.github.fxzjshm.jvm.java.api.VClass;
import io.github.fxzjshm.jvm.java.api.VClassLoader;
import io.github.fxzjshm.jvm.java.classfile.ClassFile;
import io.github.fxzjshm.jvm.java.classfile.MemberInfo;

public class EmulatedClass extends VClass {

    public ClassFile classFile;
    public int instanceSlotCount;
    public int staticSlotCount;
    public Object[] staticVars;
    public RuntimeConstantPool rtcp;

    public EmulatedClass(ClassFile cf, VClassLoader cl) {
        loader = cl;
        classFile = cf;

        methods = new HashSet<>(classFile.methods.length);
        for (MemberInfo methodInfo : classFile.methods) {
            methods.add(new EmulatedMethod(methodInfo, this));
        }

        fields = new HashSet<>(classFile.fields.length);
        for (MemberInfo fieldInfo : classFile.fields) {
            fields.add(new EmulatedField(fieldInfo, this));
        }

        rtcp = new RuntimeConstantPool(this, cf.cp);

        accessFlags = classFile.accessFlags;

        // TODO Reflect.classMap.put(cf.name, this) has done in DefaultClassLoader, is it correct?

        name = classFile.name;
        packageName = classFile.packageName;
    }
}
