package io.github.fxzjshm.jvm.java.runtime;

import java.util.Stack;

import io.github.fxzjshm.jvm.java.api.VFrame;

public class Thread {
    public VirtualMachine vm;
    public Stack<VFrame> stack;

    public Thread(VirtualMachine vm) {
        this.vm = vm;
        stack = new Stack<>();
    }

    public VFrame currentFrame(){
        return stack.peek();
    }
}
