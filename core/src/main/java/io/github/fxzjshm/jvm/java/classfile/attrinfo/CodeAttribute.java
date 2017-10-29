package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;
import io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttributeInfos.AttributeInfo;

/**
 * Stores code for methods.
 *
 * @author fxzjshm
 */
public class CodeAttribute implements AttributeInfo {

    public ConstantPool cp;
    public int maxStack;
    public int maxLocals;
    public byte[] code;
    public  ExceptionTableEntry[] exceptionTable;
    public AttributeInfo[] attributes;

    public static class ExceptionTableEntry {
        int startPc, endPc, handlerPc, catchType;
    }

    @Override
    public void readInfo(ByteArrayReader reader) {
        maxStack = reader.readUint16();
        maxLocals = reader.readUint16();
        int codeLength = reader.readInt32();
        code = reader.readBytes(codeLength);
        exceptionTable = readExceptionTable(reader);
        attributes = AttributeInfos.attributeInfos(reader, cp);
    }

    public ExceptionTableEntry[] readExceptionTable(ByteArrayReader reader) {
        int exceptionTableLength = reader.readUint16();
        exceptionTable = new ExceptionTableEntry[exceptionTableLength];
        for (int i = 0; i < exceptionTableLength; i++) {
            ExceptionTableEntry e = new ExceptionTableEntry();
            e.startPc = reader.readUint16();
            e.endPc = reader.readUint16();
            e.handlerPc = reader.readUint16();
            e.catchType = reader.readUint16();
            exceptionTable[i] = e;
        }
        return exceptionTable;
    }
}
