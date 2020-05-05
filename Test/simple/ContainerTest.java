package simple;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import simple.FactoryTest.FactoryA1;
import simple.FactoryTest.FactoryB1;
import simple.FactoryTest.FactoryC1;
import simple.FactoryTest.FactoryD1;
import simple.implementations.ImplementationA1;
import simple.implementations.ImplementationB1;
import simple.implementations.ImplementationC1;
import simple.implementations.ImplementationD1;
import simple.interfaces.InterfaceA;
import simple.interfaces.InterfaceB;
import simple.interfaces.InterfaceC;
import simple.interfaces.InterfaceD;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class ContainerTest {


    private Factory factoryA;
    private Factory factoryB;
    private Factory factoryC;
    private Factory factoryD;


    @BeforeEach
    void setUp(){

    }

    @Test
    public void TestFactoryOneParam() throws DependencyException {
        Injector injector = new Container();
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryD1(), "I");

        InterfaceD d = (InterfaceD) injector.getObject("D");
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));

    }

    @Test
    public void TestFactoryMultiParam() throws DependencyException{
        Injector injector = new Container();
        injector.registerConstant("I", 42);
        injector.registerConstant("E", 52);
        injector.registerConstant("N", 82);
        injector.registerFactory("D", new FactoryD1(), "I", "E", "N");

        InterfaceD d = (InterfaceD) injector.getObject("D");
        assertThat(d, instanceOf(ImplementationD1.class));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }

    @Test
    public void TestSingleton() throws DependencyException {
        Injector injector = new Container();
        injector.registerConstant("I", 42);
        injector.registerConstant("E", 52);
        injector.registerConstant("N", 82);
        injector.registerSingleton("D", new FactoryD1(), "I", "E", "N");

        InterfaceD d = (InterfaceD) injector.getObject("D");
        assertThat(d, instanceOf(ImplementationD1.class));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));

        InterfaceD d12 = (InterfaceD) injector.getObject("D");
        assertThat(d12, instanceOf(ImplementationD1.class));
        ImplementationD1 d11 = (ImplementationD1) d12;
        assertThat(d11.getI(), is(42));

    }

    @Test(expected = DependencyException.class)
    public void DuplicateConstantError() throws DependencyException{
        Injector injector = new Container();
        injector.registerConstant("I", 42);
        injector.registerConstant("I", 94);
    }

    @Test(expected = DependencyException.class)
    public void DuplicateFactoryError() throws DependencyException{
        Injector injector = new Container();
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryD1(), "I");
        injector.registerFactory("D", new FactoryD1(), "I");
    }

    @Test(expected = DependencyException.class)
    public void getNonExistentError() throws DependencyException{
        Injector injector = new Container();
        injector.getObject("D");
    }

    @Test
    public void factoryA1Correctly() throws DependencyException{
        Injector injector = new Container();
        injector.registerConstant("I", 42);
        injector.registerConstant("A", "CONSTANT");
        injector.registerFactory("Z", new FactoryD1(), "I");
        injector.registerFactory("C", new FactoryB1(), "Z");
        injector.registerFactory("T", new FactoryC1(), "A");
        injector.registerFactory("D", new FactoryA1(), "C","T");

        InterfaceA a = (InterfaceA) injector.getObject("D");
        ImplementationA1 a1 = (ImplementationA1) a;

        InterfaceB b = a1.getB();
        ImplementationB1 b1 = (ImplementationB1) b;
        InterfaceD d = b1.getD();
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));

        InterfaceC c = a1.getC();
        ImplementationC1 c1 = (ImplementationC1) c;
        assertThat(c1.getS(), is("CONSTANT"));
    }
}