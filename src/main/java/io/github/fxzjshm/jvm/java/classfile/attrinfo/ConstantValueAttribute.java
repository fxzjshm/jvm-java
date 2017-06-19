package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ClassReader;

/**
 * Store a constant value.
 *
 * @author fxzjshm
 */
public class ConstantValueAttribute implements AttrbuteInfos.AttributeInfo {

    public int constantValueIndex;

    @Override
    public void readInfo(ClassReader reader) {
        constantValueIndex = reader.readUint16();
    }

}
