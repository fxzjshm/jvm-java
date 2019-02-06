package io.github.fxzjshm.jvm.java.runtime.data;

import io.github.fxzjshm.jvm.java.api.VClass;

public class Instance {
    public VClass clazz;
    public Object[] data, extra;

    public Instance(VClass clazz) {
        this.clazz = clazz;
        data = new Object[((EmulatedClass)clazz).instanceSlotCount];
    }
}
