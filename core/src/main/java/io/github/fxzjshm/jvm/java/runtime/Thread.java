package io.github.fxzjshm.jvm.java.runtime;

import java.util.Stack;

public class Thread {
    public VM vm;
    public Stack<EmulatedFrame> stack;

    public Thread(VM vm) {
        this.vm = vm;
        stack = new Stack<>();
    }
}
