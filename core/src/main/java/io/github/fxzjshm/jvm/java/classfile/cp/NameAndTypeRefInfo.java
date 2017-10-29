package io.github.fxzjshm.jvm.java.classfile.cp;

public class NameAndTypeRefInfo extends ConstantPool.ConstantComplexInfo {
    public int nameIndex, descriptorIndex;
    public String name, descriptor;

    @Override
    public void cache(ConstantPool cp) {
        name = (String) (cp.infos[nameIndex].info);
        descriptor = (String) (cp.infos[descriptorIndex].info);
    }
}
