package simple;

import common.DependencyException;
import implementations.ImplementationA1;
import implementations.ImplementationB1;
import implementations.ImplementationC1;
import implementations.ImplementationD1;
import interfaces.InterfaceA;
import interfaces.InterfaceB;
import interfaces.InterfaceC;
import interfaces.InterfaceD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simple.FactoryTest.FactoryA1;
import simple.FactoryTest.FactoryB1;
import simple.FactoryTest.FactoryC1;
import simple.FactoryTest.FactoryD1;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ContainerTest {

    private Injector injector;

    @BeforeEach
    public void start(){
        injector = new simple.Container();
    }

    @Test
    public void TestFactoryOneParam() throws DependencyException {
        addConstant();
        injector.registerFactory("D",  new FactoryD1(), "I");
        verification();
    }

    @Test
    public void TestSingletonSimple() throws DependencyException {
        addConstant();
        injector.registerSingleton("D", new FactoryD1(), "I");
        verification();
    }

    @Test
    public void TestSingletonSameInstance() throws DependencyException {
        addConstant();
        addFactorySingleton();

        InterfaceC a = (InterfaceC) injector.getObject("T");
        InterfaceC a1 = (InterfaceC) injector.getObject("T");

        assertThat(a1, is(a));
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
        addFactorySingleton();
        assertThrows(DependencyException.class,() -> injector.registerFactory("D", new FactoryD1(), "I"));
        assertDoesNotThrow(() -> injector.registerFactory("K", new FactoryD1(), "I"));
    }

    @Test
    public void DuplicateSingletonError() throws DependencyException{
        addConstant();
        addFactorySingleton();
        assertThrows(DependencyException.class,() -> injector.registerSingleton("C", new FactoryD1(), "I"));
        assertDoesNotThrow(() -> injector.registerSingleton("K", new FactoryD1(), "I"));
    }

    @Test
    public void getNonExistent() throws DependencyException {
        assertThrows(DependencyException.class,() -> injector.getObject("D"));
    }

    @Test
    public void setNonExistentDependency() throws DependencyException {
        injector.registerFactory("H", new FactoryD1(), "I");
        assertThrows(DependencyException.class,() -> injector.getObject("H"));
    }

    @Test
    public void factoryA1Correctly() throws DependencyException{
        addConstant();
        addFactorySingleton();

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
        addFactorySingleton();

        InterfaceB b = (InterfaceB) injector.getObject("C");
        ImplementationB1 b1 = (ImplementationB1) b;

        InterfaceD d = b1.getD();
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }

    @Test
    public void factoryC1Correctly() throws DependencyException {
        addConstant();
        addFactorySingleton();

        InterfaceC c = (InterfaceC) injector.getObject("T");
        ImplementationC1 c1 = (ImplementationC1) c;
        assertThat(c1.getS(), is("CONSTANT"));
    }

    @Test
    public void factoryD1Correctly() throws DependencyException {
        addConstant();
        addFactorySingleton();

        InterfaceD d = (InterfaceD) injector.getObject("Z");
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }

    @Test
    public void setInadequateObject() throws DependencyException{
        addConstant();
        addFactorySingleton();
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
        injector.registerConstant("I", 42);
        injector.registerConstant("E", 52);
        injector.registerConstant("N", 82);
        injector.registerConstant("A", "CONSTANT");
    }

    private void addFactorySingleton() throws DependencyException {

        injector.registerFactory("Z", new FactoryD1(), "I");
        injector.registerSingleton("C", new FactoryB1(), "Z");
        injector.registerSingleton("T", new FactoryC1(), "A");
        injector.registerFactory("D", new FactoryA1(), "C","T");
    }

    private void verification() throws DependencyException {
        InterfaceD d = (InterfaceD) injector.getObject("D");
        assertThat(d, instanceOf(ImplementationD1.class));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));

    }
}