package complex;

import common.CommonFunc;
import common.DependencyException;
import complex.FactoryTestC.FactoryA1;
import complex.FactoryTestC.FactoryB1;
import complex.FactoryTestC.FactoryC1;
import complex.FactoryTestC.FactoryD1;
import implementations.ImplementationA1;
import implementations.ImplementationB1;
import interfaces.InterfaceA;
import interfaces.InterfaceB;
import interfaces.InterfaceC;
import interfaces.InterfaceD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContainerTestComplex extends CommonFunc {

    private Injector injector;

    @BeforeEach
    public void start(){
        injector = new Container();
    }

    @Test
    public void TestFactory() throws DependencyException {
        addConstant();
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        verification();
    }

    @Test
    public void TestSingleton() throws DependencyException {
        addConstant();
        injector.registerSingleton(InterfaceD.class, new FactoryD1(), Integer.class);
        verification();
    }

    @Test
    public void TestSingletonSameInstance() throws DependencyException {
        addConstant();
        addFactorySingleton();

        InterfaceC a = injector.getObject(InterfaceC.class);
        InterfaceC a1 = injector.getObject(InterfaceC.class);

        assertThat(a1, is(a));
    }


    @Test
    public void DuplicateConstantError() throws DependencyException {
        addConstant();
        assertThrows(common.DependencyException.class, () -> injector.registerConstant(Integer.class, 94));
    }

    @Test
    public void DuplicateFactoryError() throws DependencyException {
        addConstant();
        addFactorySingleton();
        assertThrows(common.DependencyException.class,() -> injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class));
    }

    @Test
    public void getNonExistent() {
        assertThrows(common.DependencyException.class,() -> injector.getObject(InterfaceD.class));
    }

    @Test
    public void setNonExistentDependency() throws DependencyException {
        addFactorySingleton();
        assertThrows(common.DependencyException.class,() -> injector.getObject(InterfaceD.class));
    }

    @Test
    public void factoryA1Correctly() throws DependencyException {
        addConstant();
        addFactorySingleton();

        InterfaceA a = injector.getObject(InterfaceA.class);
        ImplementationA1 a1 = (ImplementationA1) a;

        InterfaceB b = a1.getB();
        ImplementationB1 b1 = (ImplementationB1) b;

        InterfaceD d = b1.getD();
        testD(d);

        InterfaceC c = a1.getC();
        testC(c);
    }

    @Test
    public void factoryB1Correctly() throws DependencyException {
        addConstant();
        addFactorySingleton();

        InterfaceB b = injector.getObject(InterfaceB.class);
        ImplementationB1 b1 = (ImplementationB1) b;

        InterfaceD d = b1.getD();
        testD(d);
    }

    @Test
    public void factoryC1Correctly() throws common.DependencyException {
        addConstant();
        addFactorySingleton();

        InterfaceC c = injector.getObject(InterfaceC.class);
        testC(c);
    }

    @Test
    public void factoryD1Correctly() throws DependencyException {
        addConstant();
        addFactorySingleton();

        InterfaceD d = injector.getObject(InterfaceD.class);
        testD(d);
    }

    @Test
    public void setInadequateObject() throws DependencyException {
        addConstant();
        injector.registerSingleton(InterfaceD.class, new FactoryD1(), String.class);

        assertThrows(common.DependencyException.class,() -> injector.getObject(InterfaceD.class));
    }


    @Test
    public void getLoop() throws common.DependencyException {
        addConstant();
        injector.registerSingleton(InterfaceC.class, new FactoryC1(), InterfaceD.class);
        injector.registerFactory(InterfaceD.class, new FactoryD1(), InterfaceB.class);
        injector.registerSingleton(InterfaceB.class, new FactoryB1(), InterfaceC.class);
        assertThrows(common.DependencyException.class,() -> injector.getObject(InterfaceB.class));
    }

    private void addConstant() throws DependencyException {
        injector.registerConstant(Integer.class, 42);
        injector.registerConstant(String.class, "CONSTANT");
    }

    private void addFactorySingleton() throws DependencyException {

        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        injector.registerSingleton(InterfaceB.class, new FactoryB1(), InterfaceD.class);
        injector.registerSingleton(InterfaceC.class, new FactoryC1(), String.class);
        injector.registerFactory(InterfaceA.class, new FactoryA1(), InterfaceB.class, InterfaceC.class);
    }

    private void verification() throws DependencyException {
        InterfaceD d = injector.getObject(InterfaceD.class);
        testD(d);
    }

}