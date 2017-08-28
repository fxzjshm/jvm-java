package io.github.fxzjshm.jvm.java.test;

import org.junit.Assert;
import org.junit.Test;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;

public class ByteArrayReaderTest {

    @Test
    public void testReadUInt8() {
        Assert.assertEquals(193, new ByteArrayReader(new byte[]{-63}).readUint8());
    }

    @Test
    public void testReadInt8() {
        Assert.assertEquals(-63, new ByteArrayReader(new byte[]{-63}).readInt8());
    }

    @Test
    public void testReadInt16() {
        Assert.assertEquals(-2333, new ByteArrayReader(new byte[]{-10,-29}).readInt16());
    }

}