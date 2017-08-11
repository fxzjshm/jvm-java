package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;

/**
 * An attribute that stores exceptions.
 *
 * @author fxzjshm
 */
public class ExceptionsAttribute implements AttributeInfos.AttributeInfo {

    public int[] exceptionIndexTable;

    @Override
    public void readInfo(ByteArrayReader reader) {
        exceptionIndexTable = reader.readUint16s();
    }

}
