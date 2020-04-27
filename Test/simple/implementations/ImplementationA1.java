package simple.implementations;

import simple.interfaces.InterfaceA;
import simple.interfaces.InterfaceB;
import simple.interfaces.InterfaceC;

public class ImplementationA1 implements InterfaceA {
    private InterfaceB b;
    private InterfaceC c;
    public ImplementationA1(InterfaceB b, InterfaceC c) {
        this.b = b; this.c = c;
    }
}
