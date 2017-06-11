package io.github.fxzjshm.jvm.java.classfile;

import java.io.ByteArrayInputStream;

public class ClassReader extends ByteArrayInputStream {

    public ClassReader(byte[] buf) {
        super(buf);
    }

    public int readUint8() {
        return read();
    }

    public int readUint16() {
        return read() << 8 | read();
    }

    public int[] readUint16s() {
        return readUint16s(readUint16()); // Next u2 is the length
    }

    public int[] readUint16s(int n) {
        int[] ints = new int[n];
        for (int i = 0; i < n; i++) {
            ints[i] = readUint16();
        }
        return ints;
    }

    public int readInt32() {
        return readUint16() << 16 | readUint16();
    }

    public long readInt64() {
        return (((long) readInt32()) << 32) | readInt32();
    }

    public byte[] readBytes(int n) {
        byte[] bytes = new byte[n];
        System.arraycopy(this.buf, pos, bytes, 0, n);
        this.skip(n);
        return bytes;
    }
}
