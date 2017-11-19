package io.github.fxzjshm.jvm.java.runtime;

import java.util.Stack;

public class OperandStack<E> extends Stack<E> {
    private static final long serialVersionUID = -7684406741364834757L;

    @Override
    public E push(E item) {
        super.push(item);
        if (item instanceof Long || item instanceof Double)
            super.push(null);
        return item;
    }

    @Override
    public synchronized E pop() {
        if (size() < 2) return super.pop();
        E item = elementAt(size() - 2);
        if (item instanceof Long || item instanceof Double)
            super.pop();
        return super.pop();
    }

    public synchronized E popRaw() {
        return super.pop();
    }

    public synchronized E pushRaw(E item) {
        return super.push(item);
    }

    @Override
    public synchronized E peek() {
        int size = size();
        if (size < 2)
            return super.peek();
        E item = elementAt(size - 2);
        return (item instanceof Long || item instanceof Double) ? item : elementAt(size - 1);
    }
}
