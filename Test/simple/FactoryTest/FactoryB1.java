package simple.FactoryTest;

import simple.DependencyException;
import simple.Factory;
import simple.implementations.ImplementationB1;
import simple.interfaces.InterfaceD;

public class FactoryB1 implements Factory {
    @Override
    public ImplementationB1 create(Object... parameters) throws DependencyException {
        InterfaceD i;
        try {
            i = (InterfaceD) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationB1(i);    }
}
