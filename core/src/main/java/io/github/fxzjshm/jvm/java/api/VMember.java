package io.github.fxzjshm.jvm.java.api;

public interface VMember {
    VClass clazz();
    int accessFlags();
    String name();
    String descriptor();
}
