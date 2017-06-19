package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ClassReader;

/**
 * Stores local variables.
 *
 * @author fxzjshm
 */
public class LocalVariableTableAttribute implements AttrbuteInfos.AttributeInfo {

    public static class LocalVariableTableEntry {

        public int startPc, length, nameIndex, descriptorIndex, index;
    }

    public LocalVariableTableEntry[] localVariableTable;

    @Override
    public void readInfo(ClassReader reader) {
        int localVariableTableLength = reader.readUint16();
        localVariableTable = new LocalVariableTableEntry[localVariableTableLength];
        for (int i = 0; i < localVariableTableLength; i++) {
            LocalVariableTableEntry entry = new LocalVariableTableEntry();
            entry.startPc = reader.readUint16();
            entry.length = reader.readUint16();
            entry.nameIndex = reader.readUint16();
            entry.descriptorIndex = reader.readUint16();
            entry.index = reader.readUint16();
            localVariableTable[i] = entry;
        }
    }

}
