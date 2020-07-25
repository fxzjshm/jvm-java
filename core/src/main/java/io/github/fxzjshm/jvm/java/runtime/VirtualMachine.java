package io.github.fxzjshm.jvm.java.runtime;

import java.util.HashSet;
import java.util.Set;

public class VirtualMachine {
    public Set<Thread> threads = new HashSet<>();
    public Reflect reflect = new Reflect();
}
