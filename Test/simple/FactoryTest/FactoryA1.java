package simple.FactoryTest;

import simple.DependencyException;
import simple.Factory;
import simple.implementations.ImplementationA1;
import simple.interfaces.InterfaceB;
import simple.interfaces.InterfaceC;

public class FactoryA1 implements Factory {
    @Override
    public ImplementationA1 create(Object... parameters) throws DependencyException {
        InterfaceB i;
        InterfaceC a;
        try {
            i = (InterfaceB) parameters[0];
            a = (InterfaceC) parameters[1];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationA1(i, a);
    }


}
