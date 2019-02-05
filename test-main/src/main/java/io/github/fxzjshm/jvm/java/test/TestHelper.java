package io.github.fxzjshm.jvm.java.test;

import java.util.Objects;

public class TestHelper {
    public static void assertEqual(Object a, Object b) {
        if (!Objects.equals(a, b)) throw new AssertionError(a.toString() + "does not equal to " + b.toString());
    }
}
