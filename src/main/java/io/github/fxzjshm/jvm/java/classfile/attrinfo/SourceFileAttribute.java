package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;
import io.github.fxzjshm.jvm.java.classfile.ConstantPool.ConstantInfo;

/**
 * The attribute that contain source file name.
 *
 * @author fxzjshm
 */
public class SourceFileAttribute implements AttrbuteInfos.AttributeInfo {

    public ConstantInfo[] cp;
    public int sourceFileIndex;

    @Override
    public void readInfo(ByteArrayReader reader) {
        sourceFileIndex = reader.readUint16();
    }
}
