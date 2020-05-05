package simple.FactoryTest;

import simple.DependencyException;
import simple.Factory;
import simple.implementations.ImplementationC1;

public class FactoryC1 implements Factory {
    @Override
    public ImplementationC1 create(Object... parameters) throws DependencyException {
        String i;
        try {
            i = (String) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationC1(i);
    }
}
