package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttrbuteInfos.AttributeInfo;

/**
 * "@Deprecated" and "@Synthetic"
 *
 * @author fxzjshm
 */
public class MarkerAttribute implements AttributeInfo {

    @Override
    public void readInfo(ByteArrayReader reader) {
    }

    @Deprecated
    public static class DeprecatedAttribute extends MarkerAttribute {
    }

    public static class SyntheticAttribute extends MarkerAttribute {
    }
}
