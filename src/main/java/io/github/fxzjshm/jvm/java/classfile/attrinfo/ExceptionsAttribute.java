package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ClassReader;

/**
 * An attribute that stores exceptions.
 *
 * @author fxzjshm
 */
public class ExceptionsAttribute implements AttrbuteInfos.AttributeInfo {

    public int[] exceptionIndexTable;

    @Override
    public void readInfo(ClassReader reader) {
        exceptionIndexTable = reader.readUint16s();
    }

}
