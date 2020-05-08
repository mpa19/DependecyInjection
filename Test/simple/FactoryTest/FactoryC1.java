package simple.FactoryTest;

import common.DependencyException;
import implementations.ImplementationC1;
import simple.Factory;

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
