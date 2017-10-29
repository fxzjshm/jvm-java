package io.github.fxzjshm.jvm.java.classfile.cp;

public class MemberRefInfo extends ConstantPool.ConstantComplexInfo {
    public int classIndex, nameAndTypeIndex;
    public NameAndTypeRefInfo nameAndType;
    public Utf8RefInfo clazz;

    @Override
    public void cache(ConstantPool cp) {
        clazz = (Utf8RefInfo) (cp.infos[classIndex].info);
        nameAndType = (NameAndTypeRefInfo) (cp.infos[nameAndTypeIndex].info);
    }
}
