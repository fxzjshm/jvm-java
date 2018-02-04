package io.github.fxzjshm.jvm.java.runtime.data;

import java.util.Map;

import io.github.fxzjshm.jvm.java.api.Class;
import io.github.fxzjshm.jvm.java.loader.ExternalClassLoader;

public class ExternalClass extends Class {
    public ExternalClass(ExternalClassLoader loader, java.lang.Class clazz) {
        this.loader=loader;
        jClass = clazz;
        accessFlags = clazz.getModifiers();
    }

    @Override
    public boolean isSubClassOf(Class target) {
        Map<String,Class> classMap=loader.vm().reflect.classMap;
        for(java.lang.Class clazz=jClass.getSuperclass();clazz!=null;clazz=clazz.getSuperclass()){
            if(classMap.get(clazz.getName())==target)return true;
        }
        return false;
    }

    @Override
    public boolean isImplements(Class iface) {
        return;
    }

    @Override
    public boolean isSubInterfaceOf(Class iface) {
        return;
    }
}
