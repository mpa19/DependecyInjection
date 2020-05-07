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
    private Injector injector;


    @BeforeEach
    void setUp(){


    }

    @Test
    public void TestFactoryOneParam() throws DependencyException {
        addConstant();
        injector.registerFactory("D",  new FactoryD1(), "I");
        verification();
    }

    @Test
    public void TestFactoryMultiParam() throws DependencyException{
        addConstant();
        injector.registerFactory("D", new FactoryD1(), "I", "E", "N");
        verification();
    }

    @Test
    public void TestSingletonSimple() throws DependencyException {
        addConstant();
        injector.registerSingleton("D", new FactoryD1(), "I", "E", "N");
        verification();
    }

    @Test
    public void TestSingleton() throws DependencyException {
        addConstant();
        injector.registerSingleton("F", new FactoryD1(), "I");

        InterfaceD d = (InterfaceD) injector.getObject("F");
        InterfaceD d1 = (InterfaceD) injector.getObject("F");
        assertThat(d, instanceOf(d1.getClass()));
    }

    @Test
    public void DuplicateConstantError() throws DependencyException{
        addConstant();
        assertThrows(DependencyException.class, () -> injector.registerConstant("I", 94));
        assertDoesNotThrow(() -> injector.registerConstant("New Constant", 30));
    }

    @Test
    public void DuplicateFactoryError() throws DependencyException{
        addConstant();
        addFactory();
        assertThrows(DependencyException.class,() -> injector.registerFactory("D", new FactoryD1(), "I"));
        assertDoesNotThrow(() -> injector.registerFactory("K", new FactoryD1(), "I"));
    }

    @Test
    public void getNonExistent() throws DependencyException {
        addConstant();
        injector.registerFactory("D", new FactoryD1(), "I");
        assertThrows(DependencyException.class,() -> injector.getObject("D"));
    }

    @Test
    public void setNonExistentDependency() throws DependencyException {
        addConstant();
        injector.registerFactory("H", new FactoryD1(), "L");
        assertThrows(DependencyException.class,() -> injector.getObject("H"));
    }

    @Test
    public void factoryA1Correctly() throws DependencyException{
        addConstant();
        addFactory();

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

    @Test
    public void factoryB1Correctly() throws DependencyException {
        addConstant();
        addFactory();

        InterfaceB b = (InterfaceB) injector.getObject("C");
        ImplementationB1 b1 = (ImplementationB1) b;

        InterfaceD d = b1.getD();
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }

    @Test
    public void factoryC1Correctly() throws DependencyException {
        addConstant();
        addFactory();

        InterfaceC c = (InterfaceC) injector.getObject("T");
        ImplementationC1 c1 = (ImplementationC1) c;
        assertThat(c1.getS(), is("CONSTANT"));
    }

    @Test
    public void factoryD1Correctly() throws DependencyException {
        addConstant();
        addFactory();

        InterfaceD d = (InterfaceD) injector.getObject("Z");
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }

    @Test
    public void setInadequateObject() throws DependencyException{
        addConstant();
        addFactory();
        injector.registerSingleton("H", new FactoryD1(), "T");

        assertThrows(DependencyException.class,() -> injector.getObject("H"));
    }

    @Test
    public void getLoop() throws DependencyException{
        addConstant();
        injector.registerSingleton("G", new FactoryD1(), "B");
        injector.registerSingleton("B", new FactoryD1(), "H");
        injector.registerSingleton("H", new FactoryD1(), "G");

        assertThrows(DependencyException.class,() -> injector.getObject("H"));
    }

    private void addConstant() throws DependencyException {
        injector = new Container();

        injector.registerConstant("I", 42);
        injector.registerConstant("E", 52);
        injector.registerConstant("N", 82);
        injector.registerConstant("A", "CONSTANT");
    }

    private void addFactory() throws DependencyException {

        injector.registerFactory("Z", new FactoryD1(), "I");
        injector.registerFactory("C", new FactoryB1(), "Z");
        injector.registerFactory("T", new FactoryC1(), "A");
        injector.registerFactory("D", new FactoryA1(), "C","T");
    }

    private void verification() throws DependencyException {
        InterfaceD d = (InterfaceD) injector.getObject("D");
        assertThat(d, instanceOf(ImplementationD1.class));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));

    }
}