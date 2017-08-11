package io.github.fxzjshm.jvm.java.classfile;

import java.io.ByteArrayInputStream;

public class ByteArrayReader extends ByteArrayInputStream {

    public ByteArrayReader(byte[] buf) {
        super(buf);
    }

    @Override
    public synchronized int read() {
        if (pos >= count) throw new RuntimeException("Not enough bytes!");
        return super.read();
    }

    public synchronized int readInt8() {
        return (pos < count) ? buf[pos++] : -1;
    }

    public synchronized int readUint8() {
        return read();
    }

    public synchronized int readInt16() {
        return (short) (readUint8() << 8 | readUint8());
    }

    public synchronized int readUint16() {
        return readUint8() << 8 | readUint8();
    }

    public synchronized int[] readUint16s() {
        return readUint16s(readUint16()); // Next u2 is the length
    }

    public synchronized int[] readUint16s(int n) {
        int[] ints = new int[n];
        for (int i = 0; i < n; i++) {
            ints[i] = readUint16();
        }
        return ints;
    }

    public synchronized int readInt32() {
        return readUint16() << 16 | readUint16();
    }

    public synchronized int[] readInt32s(int n) {
        int[] ints = new int[n];
        for (int i = 0; i < n; i++) ints[i] = readInt32();
        return ints;
    }

    public synchronized long readInt64() {
        return (((long) readInt32()) << 32) | readInt32();
    }

    public synchronized byte[] readBytes(int n) {
        byte[] bytes = new byte[n];
        System.arraycopy(this.buf, pos, bytes, 0, n);
        long m = this.skip(n);
        if (m != n)
            throw new IllegalArgumentException("Not enough bytes! Expected:" + n + ", Actual: " + m);
        return bytes;
    }

    public synchronized void skipPadding() {
        while (pos % 4 != 0) pos++;
    }

    public int getPos() {
        return pos;
    }

    public synchronized void setPos(int pos) {
        this.pos = pos;
    }
}
