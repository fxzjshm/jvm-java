package io.github.fxzjshm.jvm.java.runtime.data;

import java.util.Hashtable;
import java.util.Map;

public class Instance {
    public Class clazz;
    public Object[] data, extra;

    public Instance(Class clazz) {
        this.clazz = clazz;
        data = new Object[clazz.instanceSlotCount];
    }
}
