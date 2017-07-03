package io.github.fxzjshm.jvm.java.runtime;

import java.util.LinkedList;
import java.util.List;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;

public class Frame {
    public List<Object> localVars = new LinkedList<>();
    public OperandStack<Object> operandStack = new OperandStack<>();

    public void exec(ByteArrayReader reader) {
        int code = (byte) reader.read();
        switch (code) {
            case 0x0: // nop
                break;
            case 0x1: // aconst_null
                operandStack.push(null);
                break;
            case 0x2:
            case 0x3:
            case 0x4:
            case 0x5:
            case 0x6:
            case 0x7:
            case 0x8: // iconst_<n>
                operandStack.push(code - 0x03);
                break;
            case 0x9:
            case 0xa: // lconst_<n>
                operandStack.push((long) (code - 0x09));
                break;
            case 0xb:
            case 0xc:
            case 0xd: // fconst_<n>
                operandStack.push((float) (code - 0x0b));
                break;
            case 0xe:
            case 0xf: // dconst_<n>
                operandStack.push((double) (code - 0x0e));
                break;
            case 0x10: // bipush
                operandStack.push(reader.readUint8());
                break;
            case 0x11: // sipush
                operandStack.push(reader.readUint16());
                break;
            case 0x12: // ldc
//TODO impl
                break;
            case 0x13:
//TODO impl
                break;
            case 0x14:
//TODO impl
                break;
            case 0x15: // iload
            case 0x16: // lload
            case 0x17: // fload
            case 0x18: // dload
            case 0x19: // aload
                operandStack.push(localVars.get(reader.readUint8()));
                break;
            case 0x1a:
            case 0x1b:
            case 0x1c:
            case 0x1d: // iload_<n>
                operandStack.push(localVars.get(code - 0x1a));
                break;
            case 0x1e:
            case 0x1f:
            case 0x20:
            case 0x21: // lload_<n>
                operandStack.push(localVars.get(code - 0x1e));
                break;
            case 0x22:
            case 0x23:
            case 0x24:
            case 0x25: // fload_<n>
                operandStack.push(localVars.get(code - 0x22));
                break;
            case 0x26:
            case 0x27:
            case 0x28:
            case 0x29: // dload_<n>
                operandStack.push(localVars.get(code - 0x26));
                break;
            case 0x2a:
            case 0x2b:
            case 0x2c:
            case 0x2d: // aload_<n>
                operandStack.push(localVars.get(code - 0x2a));
                break;
            case 0x2e:
//TODO impl
                break;
            case 0x2f:
//TODO impl
                break;
            case 0x30:
//TODO impl
                break;
            case 0x31:
//TODO impl
                break;
            case 0x32:
//TODO impl
                break;
            case 0x33:
//TODO impl
                break;
            case 0x34:
//TODO impl
                break;
            case 0x35:
//TODO impl
                break;
            case 0x36: // istore
            case 0x37: // lstore
            case 0x38: // fstore
            case 0x39: // dstore
            case 0x3a: // astore
                localVars.set(reader.readUint8(), operandStack.pop());
                break;
            case 0x3b:
            case 0x3c:
            case 0x3d:
            case 0x3e: // istore_<n>
                localVars.set(code - 0x3b, operandStack.pop());
                break;
            case 0x3f:
            case 0x40:
            case 0x41:
            case 0x42: // lstore_<n>
                localVars.set(code - 0x3f, operandStack.pop());
                break;
            case 0x43:
            case 0x44:
            case 0x45:
            case 0x46: // fstore_<n>
                localVars.set(code - 0x43, operandStack.pop());
                break;
            case 0x47:
            case 0x48:
            case 0x49:
            case 0x4a: // dstore_<n>
                localVars.set(code - 0x47, operandStack.pop());
                break;
            case 0x4b:
            case 0x4c:
            case 0x4d:
            case 0x4e: // astore_<n>
                localVars.set(code - 0x4b, operandStack.pop());
                break;
            case 0x4f:
//TODO impl
                break;
            case 0x50:
//TODO impl
                break;
            case 0x51:
//TODO impl
                break;
            case 0x52:
//TODO impl
                break;
            case 0x53:
//TODO impl
                break;
            case 0x54:
//TODO impl
                break;
            case 0x55:
//TODO impl
                break;
            case 0x56:
//TODO impl
                break;
            case 0x57: // pop
                operandStack.pop();
                break;
            case 0x58: // pop2
                operandStack.pop();
                operandStack.pop();
                break;
            case 0x59: // dup
                operandStack.push(operandStack.peek());
                break;
            case 0x5a: // dup_x1
                Object v1 = operandStack.pop(), v2 = operandStack.pop();
                operandStack.push(v1);
                operandStack.push(v2);
                operandStack.push(v1);
                break;
            case 0x5b: // dup_x2
                v1 = operandStack.pop();
                v2 = operandStack.pop();
                Object v3 = operandStack.pop();
                operandStack.push(v1);
                operandStack.push(v3);
                operandStack.push(v2);
                operandStack.push(v1);
                break;
            case 0x5c: // dup2
                v1 = operandStack.pop();
                v2 = operandStack.pop();
                operandStack.push(v2);
                operandStack.push(v1);
                operandStack.push(v2);
                operandStack.push(v1);
                break;
            case 0x5d: // dup2_x1
                v1 = operandStack.pop();
                v2 = operandStack.pop();
                v3 = operandStack.pop();
                operandStack.push(v2);
                operandStack.push(v1);
                operandStack.push(v3);
                operandStack.push(v2);
                operandStack.push(v1);
                break;
            case 0x5e: // dup2_x2
                v1 = operandStack.pop();
                v2 = operandStack.pop();
                v3 = operandStack.pop();
                Object v4 = operandStack.pop();
                operandStack.push(v2);
                operandStack.push(v1);
                operandStack.push(v4);
                operandStack.push(v3);
                operandStack.push(v2);
                operandStack.push(v1);
                break;
            case 0x5f: // swap
                v1 = operandStack.pop();
                v2 = operandStack.pop();
                operandStack.push(v1);
                operandStack.push(v2);
                break;
            case 0x60: // iadd
                Integer i1 = (Integer) operandStack.pop();
                Integer i2 = (Integer) operandStack.pop();
                operandStack.push(i1 + i2);
                break;
            case 0x61: // ladd
                Long l1 = (Long) operandStack.pop();
                Long l2 = (Long) operandStack.pop();
                operandStack.push(l1 + l2);
                break;
            case 0x62: // fadd
                Float f1 = (Float) operandStack.pop();
                Float f2 = (Float) operandStack.pop();
                operandStack.push(f1 + f2);
                break;
            case 0x63: // dadd
                Double d1 = (Double) operandStack.pop();
                Double d2 = (Double) operandStack.pop();
                operandStack.push(d1 + d2);
                break;
            case 0x64:
//TODO impl
                break;
            case 0x65:
//TODO impl
                break;
            case 0x66:
//TODO impl
                break;
            case 0x67:
//TODO impl
                break;
            case 0x68:
//TODO impl
                break;
            case 0x69:
//TODO impl
                break;
            case 0x6a:
//TODO impl
                break;
            case 0x6b:
//TODO impl
                break;
            case 0x6c:
//TODO impl
                break;
            case 0x6d:
//TODO impl
                break;
            case 0x6e:
//TODO impl
                break;
            case 0x6f:
//TODO impl
                break;
            case 0x70:
//TODO impl
                break;
            case 0x71:
//TODO impl
                break;
            case 0x72:
//TODO impl
                break;
            case 0x73:
//TODO impl
                break;
            case 0x74:
//TODO impl
                break;
            case 0x75:
//TODO impl
                break;
            case 0x76:
//TODO impl
                break;
            case 0x77:
//TODO impl
                break;
            case 0x78:
//TODO impl
                break;
            case 0x79:
//TODO impl
                break;
            case 0x7a:
//TODO impl
                break;
            case 0x7b:
//TODO impl
                break;
            case 0x7c:
//TODO impl
                break;
            case 0x7d:
//TODO impl
                break;
            case 0x7e:
//TODO impl
                break;
            case 0x7f:
//TODO impl
                break;
            case 0x80:
//TODO impl
                break;
            case 0x81:
//TODO impl
                break;
            case 0x82:
//TODO impl
                break;
            case 0x83:
//TODO impl
                break;
            case 0x84:
//TODO impl
                break;
            case 0x85:
//TODO impl
                break;
            case 0x86:
//TODO impl
                break;
            case 0x87:
//TODO impl
                break;
            case 0x88:
//TODO impl
                break;
            case 0x89:
//TODO impl
                break;
            case 0x8a:
//TODO impl
                break;
            case 0x8b:
//TODO impl
                break;
            case 0x8c:
//TODO impl
                break;
            case 0x8d:
//TODO impl
                break;
            case 0x8e:
//TODO impl
                break;
            case 0x8f:
//TODO impl
                break;
            case 0x90:
//TODO impl
                break;
            case 0x91:
//TODO impl
                break;
            case 0x92:
//TODO impl
                break;
            case 0x93:
//TODO impl
                break;
            case 0x94:
//TODO impl
                break;
            case 0x95:
//TODO impl
                break;
            case 0x96:
//TODO impl
                break;
            case 0x97:
//TODO impl
                break;
            case 0x98:
//TODO impl
                break;
            case 0x99:
//TODO impl
                break;
            case 0x9a:
//TODO impl
                break;
            case 0x9b:
//TODO impl
                break;
            case 0x9c:
//TODO impl
                break;
            case 0x9d:
//TODO impl
                break;
            case 0x9e:
//TODO impl
                break;
            case 0x9f:
//TODO impl
                break;
            case 0xa0:
//TODO impl
                break;
            case 0xa1:
//TODO impl
                break;
            case 0xa2:
//TODO impl
                break;
            case 0xa3:
//TODO impl
                break;
            case 0xa4:
//TODO impl
                break;
            case 0xa5:
//TODO impl
                break;
            case 0xa6:
//TODO impl
                break;
            case 0xa7:
//TODO impl
                break;
            case 0xa8:
//TODO impl
                break;
            case 0xa9:
//TODO impl
                break;
            case 0xaa:
//TODO impl
                break;
            case 0xab:
//TODO impl
                break;
            case 0xac:
//TODO impl
                break;
            case 0xad:
//TODO impl
                break;
            case 0xae:
//TODO impl
                break;
            case 0xaf:
//TODO impl
                break;
            case 0xb0:
//TODO impl
                break;
            case 0xb1:
//TODO impl
                break;
            case 0xb2:
//TODO impl
                break;
            case 0xb3:
//TODO impl
                break;
            case 0xb4:
//TODO impl
                break;
            case 0xb5:
//TODO impl
                break;
            case 0xb6:
//TODO impl
                break;
            case 0xb7:
//TODO impl
                break;
            case 0xb8:
//TODO impl
                break;
            case 0xb9:
//TODO impl
                break;
            case 0xba:
//TODO impl
                break;
            case 0xbb:
//TODO impl
                break;
            case 0xbc:
//TODO impl
                break;
            case 0xbd:
//TODO impl
                break;
            case 0xbe:
//TODO impl
                break;
            case 0xbf:
//TODO impl
                break;
            case 0xc0:
//TODO impl
                break;
            case 0xc1:
//TODO impl
                break;
            case 0xc2:
//TODO impl
                break;
            case 0xc3:
//TODO impl
                break;
            case 0xc4:
//TODO impl
                break;
            case 0xc5:
//TODO impl
                break;
            case 0xc6:
//TODO impl
                break;
            case 0xc7:
//TODO impl
                break;
            case 0xc8:
//TODO impl
                break;
            case 0xc9:
//TODO impl
                break;
            case 0xca:
//TODO impl
                break;
            case 0xcb:
//TODO impl
                break;
            case 0xcc:
//TODO impl
                break;
            case 0xcd:
//TODO impl
                break;
            case 0xce:
//TODO impl
                break;
            case 0xcf:
//TODO impl
                break;
            case 0xd0:
//TODO impl
                break;
            case 0xd1:
//TODO impl
                break;
            case 0xd2:
//TODO impl
                break;
            case 0xd3:
//TODO impl
                break;
            case 0xd4:
//TODO impl
                break;
            case 0xd5:
//TODO impl
                break;
            case 0xd6:
//TODO impl
                break;
            case 0xd7:
//TODO impl
                break;
            case 0xd8:
//TODO impl
                break;
            case 0xd9:
//TODO impl
                break;
            case 0xda:
//TODO impl
                break;
            case 0xdb:
//TODO impl
                break;
            case 0xdc:
//TODO impl
                break;
            case 0xdd:
//TODO impl
                break;
            case 0xde:
//TODO impl
                break;
            case 0xdf:
//TODO impl
                break;
            case 0xe0:
//TODO impl
                break;
            case 0xe1:
//TODO impl
                break;
            case 0xe2:
//TODO impl
                break;
            case 0xe3:
//TODO impl
                break;
            case 0xe4:
//TODO impl
                break;
            case 0xe5:
//TODO impl
                break;
            case 0xe6:
//TODO impl
                break;
            case 0xe7:
//TODO impl
                break;
            case 0xe8:
//TODO impl
                break;
            case 0xe9:
//TODO impl
                break;
            case 0xea:
//TODO impl
                break;
            case 0xeb:
//TODO impl
                break;
            case 0xec:
//TODO impl
                break;
            case 0xed:
//TODO impl
                break;
            case 0xee:
//TODO impl
                break;
            case 0xef:
//TODO impl
                break;
            case 0xf0:
//TODO impl
                break;
            case 0xf1:
//TODO impl
                break;
            case 0xf2:
//TODO impl
                break;
            case 0xf3:
//TODO impl
                break;
            case 0xf4:
//TODO impl
                break;
            case 0xf5:
//TODO impl
                break;
            case 0xf6:
//TODO impl
                break;
            case 0xf7:
//TODO impl
                break;
            case 0xf8:
//TODO impl
                break;
            case 0xf9:
//TODO impl
                break;
            case 0xfa:
//TODO impl
                break;
            case 0xfb:
//TODO impl
                break;
            case 0xfc:
//TODO impl
                break;
            case 0xfd:
//TODO impl
                break;
            case 0xfe:
//TODO impl
                break;
            case 0xff:
//TODO impl
                break;
        }
    }


}
