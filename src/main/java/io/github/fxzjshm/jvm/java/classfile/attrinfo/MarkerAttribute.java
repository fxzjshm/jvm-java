package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ClassReader;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttrbuteInfos.AttributeInfo;

/**
 * "@Deprecated" and "@Synthetic"
 *
 * @author fxzjshm
 */
public class MarkerAttribute implements AttributeInfo {

    @Override
    public void readInfo(ClassReader reader) {
    }

    @Deprecated
    public static class DeprecatedAttribute extends MarkerAttribute {
    }

    public static class SyntheticAttribute extends MarkerAttribute {
    }
}
