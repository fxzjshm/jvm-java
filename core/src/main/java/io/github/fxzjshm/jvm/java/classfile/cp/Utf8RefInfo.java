package io.github.fxzjshm.jvm.java.classfile.cp;

public class Utf8RefInfo extends ConstantPool.ConstantComplexInfo {
    public int cpIndex;
    public String s;

    @Override
    public void cache(ConstantPool cp) {
        s = (String) (cp.infos[cpIndex].info);
    }
}
