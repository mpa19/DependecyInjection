package simple.implementations;

import simple.interfaces.InterfaceC;

public class ImplementationC1 implements InterfaceC {
    public String getS() {
        return s;
    }

    private String s;
    public ImplementationC1(String s) {
        this.s = s;
    }
}
