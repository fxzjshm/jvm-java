package io.github.fxzjshm.jvm.java.test;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;

public class ByteArrayReaderTest extends AbstractTest{

    ByteArrayReader bar1 = new ByteArrayReader(new byte[]{-63});

    // @Test
    public void testReadUInt8() {
        bar1.setPos(0);
        TestHelper.assertEqual(193, bar1.readUint8());
    }

    // @Test
    public void testReadInt8() {
        bar1.setPos(0);
        TestHelper.assertEqual (-63 , bar1.readInt8());
    }

    // @Test
    public void testReadInt16() throws IOException {
            ByteArrayReader bar2 = new ByteArrayReader(new byte[]{-10, -29});
            TestHelper.assertEqual(-2333, bar2.readInt16());
            bar2.close();
    }

    @Override
    public void before() {

    }

    @Override
    public void test() {

    }

    @Override
    public void after() {

    }
}