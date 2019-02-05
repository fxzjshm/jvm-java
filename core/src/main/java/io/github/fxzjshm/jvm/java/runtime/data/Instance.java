package io.github.fxzjshm.jvm.java.runtime.data;

import io.github.fxzjshm.jvm.java.api.Class;

public class Instance {
    public Class clazz;
    public Object[] data, extra;

    public Instance(Class clazz) {
        this.clazz = clazz;
        data = new Object[((EmulatedClass)clazz).instanceSlotCount];
    }
}
