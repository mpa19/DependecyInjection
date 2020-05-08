package implementations;

import interfaces.InterfaceA;
import interfaces.InterfaceB;
import interfaces.InterfaceC;

public class ImplementationA1 implements InterfaceA {
    public InterfaceB getB() {
        return b;
    }

    public InterfaceC getC() {
        return c;
    }

    private InterfaceB b;
    private InterfaceC c;
    public ImplementationA1(InterfaceB b, InterfaceC c) {
        this.b = b; this.c = c;
    }
}
