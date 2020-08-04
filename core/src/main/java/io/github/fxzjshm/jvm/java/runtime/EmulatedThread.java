package io.github.fxzjshm.jvm.java.runtime;

import java.util.Stack;

import io.github.fxzjshm.jvm.java.api.VFrame;

public class EmulatedThread {
    public VirtualMachine vm;
    public Stack<VFrame> stack;

    public EmulatedThread(VirtualMachine vm) {
        this.vm = vm;
        stack = new Stack<>();
    }

    public VFrame currentFrame() {
        return stack.peek();
    }
}
