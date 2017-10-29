package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;
import io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool;

/**
 * The attribute that contain source file name.
 *
 * @author fxzjshm
 */
public class SourceFileAttribute implements AttributeInfos.AttributeInfo {

    public ConstantPool cp;
    public int sourceFileIndex;

    @Override
    public void readInfo(ByteArrayReader reader) {
        sourceFileIndex = reader.readUint16();
    }
}
