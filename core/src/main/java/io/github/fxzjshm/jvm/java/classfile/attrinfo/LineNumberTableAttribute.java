package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;

/**
 * The attribute that stores line numbers.
 *
 * @author fxzjshm
 */
public class LineNumberTableAttribute implements AttributeInfos.AttributeInfo {

    public static class LineNumberTableEntry {

        public int startPc, lineNumber;
    }

    public LineNumberTableEntry[] lineNumberTable;

    @Override
    public void readInfo(ByteArrayReader reader) {
        int lineNumberTableLength = reader.readUint16();
        lineNumberTable = new LineNumberTableEntry[lineNumberTableLength];
        for (int i = 0; i < lineNumberTableLength; i++) {
            LineNumberTableEntry entry = new LineNumberTableEntry();
            entry.startPc = reader.readUint16();
            entry.lineNumber = reader.readUint16();
            lineNumberTable[i] = entry;
        }
    }

}
