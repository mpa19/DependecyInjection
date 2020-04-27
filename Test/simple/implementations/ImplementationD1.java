package simple.implementations;

import simple.interfaces.InterfaceD;

public class ImplementationD1 implements InterfaceD {
    private int i;
    public ImplementationD1(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }
}
