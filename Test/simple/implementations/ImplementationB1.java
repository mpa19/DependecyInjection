package simple.implementations;

import simple.interfaces.InterfaceB;
import simple.interfaces.InterfaceD;

public class ImplementationB1 implements InterfaceB {
    private InterfaceD d;
    public ImplementationB1(InterfaceD d) {
        this.d = d;
    }
}
