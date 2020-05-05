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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ContainerTest {

    private Factory factoryA;
    private Factory factoryB;
    private Factory factoryC;
    private Factory factoryD;
    Injector injector;


    @BeforeEach
    void setUp(){


    }

    @Test
    public void TestFactoryOneParam() throws DependencyException {
        injector = new Container();
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryD1(), "I");
        test();
    }

    @Test
    public void TestFactoryMultiParam() throws DependencyException{
        regConstant();
        injector.registerFactory("D", new FactoryD1(), "I", "E", "N");
        test();
    }

    @Test
    public void TestSingleton() throws DependencyException {
        regConstant();
        injector.registerSingleton("D", new FactoryD1(), "I", "E", "N");
        test();

    }

    @Test
    public void DuplicateConstantError() throws DependencyException{
        injector = new Container();
        injector.registerConstant("I", 42);
        assertThrows(DependencyException.class, () -> injector.registerConstant("I", 94));
        assertDoesNotThrow(() -> injector.registerConstant("New Constant", 30));
    }

    @Test
    public void DuplicateFactoryError() throws DependencyException{
        injector = new Container();
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryD1(), "I");
        assertThrows(DependencyException.class,() -> injector.registerFactory("D", new FactoryD1(), "I"));
        assertDoesNotThrow(() -> injector.registerFactory("E", new FactoryD1(), "I"));
    }

    @Test
    public void getNonExistentError(){
        injector = new Container();
        assertThrows(DependencyException.class,() -> injector.getObject("D"));
    }

    @Test
    public void factoryA1Correctly() throws DependencyException{
        addConstant();

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

    private void addConstant() throws DependencyException {
        injector = new Container();

        injector.registerConstant("I", 42);
        injector.registerConstant("A", "CONSTANT");

        injector.registerFactory("Z", new FactoryD1(), "I");
        injector.registerFactory("C", new FactoryB1(), "Z");
        injector.registerFactory("T", new FactoryC1(), "A");
        injector.registerFactory("D", new FactoryA1(), "C","T");
    }

    private void regConstant() throws DependencyException {
        injector = new Container();

        injector.registerConstant("I", 42);
        injector.registerConstant("E", 52);
        injector.registerConstant("N", 82);

    }

    private void test() throws DependencyException {

        InterfaceD d = (InterfaceD) injector.getObject("D");
        assertThat(d, instanceOf(ImplementationD1.class));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));

    }
}