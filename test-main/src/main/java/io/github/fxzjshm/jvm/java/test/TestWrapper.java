package io.github.fxzjshm.jvm.java.test;

import java.util.HashSet;
import java.util.Set;

public class TestWrapper {
    public Set<TestRunnable> beforeFunctions, testFunctions, afterFunctions;

    public TestWrapper() {
        beforeFunctions = new HashSet<>();
        testFunctions = new HashSet<>();
        afterFunctions = new HashSet<>();
    }
}
