package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ClassReader;

/**
 * An unknown attribute.
 *
 * @author fxzjshm
 */
public class UnparsedAttribute implements AttrbuteInfos.AttributeInfo {

    public String name;
    public int length;
    public byte[] info;

    @Override
    public void readInfo(ClassReader reader) {
        info = reader.readBytes(length);
    }

}
