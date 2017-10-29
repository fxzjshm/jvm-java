package io.github.fxzjshm.jvm.java.runtime.data;

public class Instance {
    public Class clazz;
    public Object[] data, extra;

    public Instance(Class clazz) {
        this.clazz = clazz;
        data = new Object[clazz.instanceSlotCount];
    }
}
