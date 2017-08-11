package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;

/**
 * An unknown attribute.
 *
 * @author fxzjshm
 */
public class UnparsedAttribute implements AttributeInfos.AttributeInfo {

    public String name;
    public int length;
    public byte[] info;

    @Override
    public void readInfo(ByteArrayReader reader) {
        info = reader.readBytes(length);
    }

}
