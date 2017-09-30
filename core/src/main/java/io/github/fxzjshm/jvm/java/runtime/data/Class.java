package io.github.fxzjshm.jvm.java.runtime.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import io.github.fxzjshm.jvm.java.classfile.ClassFile;
import io.github.fxzjshm.jvm.java.classfile.MemberInfo;
import io.github.fxzjshm.jvm.java.loader.ClassLoader;

public class Class {
    public static Map<String, Class> reflectMap = new Hashtable<>();

    public ClassLoader loader;
    public Set<Method> methods;
    public Set<Field> fields;
    public ClassFile classFile;
    public Class superClass, interfaces[];
    public int instanceSlotCount, staticSlotCount;
    public Object[] staticVars;
    public RuntimeConstantPool rtcp;

    public Class(ClassFile cf, ClassLoader cl) {
        loader = cl;
        classFile = cf;

        methods = new HashSet<>(classFile.methods.length);
        for (MemberInfo methodInfo : classFile.methods) {
            methods.add(new Method(methodInfo, this));
        }

        fields = new HashSet<>(classFile.fields.length);
        for (MemberInfo fieldInfo : classFile.fields) {
            fields.add(new Field(fieldInfo, this));
        }

        rtcp = new RuntimeConstantPool(this, cf.cp);

        reflectMap.put(cf.name, this);
    }

    public boolean isSubClassOf(Class target) {
        for (Class c = superClass; c != null; c = c.superClass) {
            if (c == target) return true;
        }
        return false;
    }
}
