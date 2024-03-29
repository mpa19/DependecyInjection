package simple.FactoryTest;

import common.DependencyException;
import implementations.ImplementationB1;
import interfaces.InterfaceD;
import simple.Factory;

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
