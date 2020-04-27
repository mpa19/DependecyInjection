package simple;

import org.junit.Test;
import simple.FactoryTest.FactoryD1;
import simple.implementations.ImplementationD1;
import simple.interfaces.InterfaceD;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class ContainerTest {

    @Test
    public void test1() throws DependencyException {
        Injector injector = new Container();
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("D");
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }


    public void addConstants(){

    }
}