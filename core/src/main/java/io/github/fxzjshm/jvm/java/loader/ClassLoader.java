package io.github.fxzjshm.jvm.java.loader;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

import io.github.fxzjshm.jvm.java.classfile.Bitmask;
import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;
import io.github.fxzjshm.jvm.java.classfile.ClassFile;
import io.github.fxzjshm.jvm.java.runtime.data.Class;
import io.github.fxzjshm.jvm.java.runtime.data.Field;

public class ClassLoader {
    public Map<String, Class> map = new Hashtable<>();
    public Classpath classpath;

    public ClassLoader(Classpath classpath) {
        this.classpath = classpath;
    }

    public Class loadClass(String name) throws IOException {
        if (map.containsKey(name)) return map.get(name);
        else {
            byte[] data = loadNonArrayClass(name);
            Class clazz = defineClass(data);
            link(clazz);
            return clazz;
        }
    }

    private static void link(Class clazz) {
        // TODO verify(clazz);
        prepare(clazz);
    }

    private static void prepare(Class clazz) {
        calcInstanceFieldSlotIds(clazz);
        calcStaticFieldSlotIds(clazz);
        allocAndInitStaticVars(clazz);
    }

    private static void allocAndInitStaticVars(Class clazz) {
        clazz.staticVars = new Object[clazz.staticSlotCount];
        for (Field field : clazz.fields) {
            if (((field.info.accessFlags & Bitmask.ACC_STATIC) != 0) && ((field.info.accessFlags & Bitmask.ACC_FINAL) != 0)) {
                // initStaticFinalVar(clazz, field);
                int cpIndex = field.constValueIndex;
                if (cpIndex > 0) {
                    clazz.staticVars[field.slotId] = clazz.rtcp.consts[field.constValueIndex];
                }
            }
        }
    }

    private static void calcStaticFieldSlotIds(Class clazz) {
        int slotId = 0;
        for (Field field : clazz.fields) {
            if ((field.info.accessFlags & Bitmask.ACC_STATIC) != 0) {
                field.slotId = slotId;
                slotId += (field.isLongOrDouble) ? 2 : 1;
            }
        }
        clazz.staticSlotCount = slotId;
    }

    private static void calcInstanceFieldSlotIds(Class clazz) {
        int slotId = (clazz.superClass != null) ? (clazz.superClass.instanceSlotCount) : 0;
        for (Field field : clazz.fields) {
            if ((field.info.accessFlags & Bitmask.ACC_STATIC) == 0) {
                field.slotId = slotId;
                slotId += (field.isLongOrDouble) ? 2 : 1;
            }
        }
        clazz.instanceSlotCount = slotId;
    }

    private Class defineClass(byte[] data) throws IOException {
        Class clazz = new Class(new ClassFile(new ByteArrayReader(data)), this);
        resolveSuperClass(clazz);
        resolveInterfaces(clazz);
        map.put(clazz.classFile.name, clazz);
        return clazz;
    }

    private static void resolveInterfaces(Class clazz) throws IOException {
        int n = clazz.interfaces.length;
        if (n > 0) {
            clazz.interfaces = new Class[n];
            for (int i = 0; i < n; i++) {
                clazz.interfaces[i] = clazz.loader.loadClass(clazz.classFile.interfaceNames[i]);
            }
        }
    }

    private static void resolveSuperClass(Class clazz) throws IOException {
        if (!Objects.equals(clazz.classFile.name, "java/lang/Object")) {
            clazz.superClass = clazz.loader.loadClass(clazz.classFile.superClassName);
        }
    }

    private byte[] loadNonArrayClass(String name) {
        return classpath.readClass(name);
    }
}
