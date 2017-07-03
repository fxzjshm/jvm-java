package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;

/**
 * Store a constant value.
 *
 * @author fxzjshm
 */
public class ConstantValueAttribute implements AttrbuteInfos.AttributeInfo {

    public int constantValueIndex;

    @Override
    public void readInfo(ByteArrayReader reader) {
        constantValueIndex = reader.readUint16();
    }

}
