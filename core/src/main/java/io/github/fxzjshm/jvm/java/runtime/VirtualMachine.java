package io.github.fxzjshm.jvm.java.runtime;

import java.util.HashSet;
import java.util.Set;

public class VirtualMachine {
    public Set<EmulatedThread> threads = new HashSet<>();
    public Reflect reflect = new Reflect();
}
