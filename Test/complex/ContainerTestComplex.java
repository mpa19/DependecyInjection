package complex;

public class ContainerTestComplex {

    private Injector injector;

    /*@Test
    public void TestFactoryOneParam() throws simple.DependencyException {
        addConstant();
        injector.registerFactory("D",  new FactoryD1(), "I");
        verification();
    }

    @Test
    public void TestFactoryMultiParam() throws simple.DependencyException {
        addConstant();
        injector.registerFactory("D", new FactoryD1(), "I", "E", "N");
        verification();
    }

    @Test
    public void TestSingletonSimple() throws simple.DependencyException {
        addConstant();
        injector.registerSingleton("D", new FactoryD1(), "I", "E", "N");
        verification();
    }

    @Test
    public void TestSingleton() throws simple.DependencyException {
        addConstant();
        injector.registerSingleton("F", new FactoryD1(), "I");

        InterfaceD d = (InterfaceD) injector.getObject("F");
        InterfaceD d1 = (InterfaceD) injector.getObject("F");
        assertThat(d, instanceOf(d1.getClass()));
    }

    @Test
    public void DuplicateConstantError() throws simple.DependencyException {
        addConstant();
        assertThrows(simple.DependencyException.class, () -> injector.registerConstant("I", 94));
        assertDoesNotThrow(() -> injector.registerConstant("New Constant", 30));
    }

    @Test
    public void DuplicateFactoryError() throws simple.DependencyException {
        addConstant();
        addFactory();
        assertThrows(simple.DependencyException.class,() -> injector.registerFactory("D", new FactoryD1(), "I"));
        assertDoesNotThrow(() -> injector.registerFactory("K", new FactoryD1(), "I"));
    }

    @Test
    public void getNonExistent() throws simple.DependencyException {
        addConstant();
        injector.registerFactory("D", new FactoryD1(), "I");
        assertThrows(simple.DependencyException.class,() -> injector.getObject("D"));
    }

    @Test
    public void setNonExistentDependency() throws simple.DependencyException {
        addConstant();
        injector.registerFactory("H", new FactoryD1(), "L");
        assertThrows(simple.DependencyException.class,() -> injector.getObject("H"));
    }

    @Test
    public void factoryA1Correctly() throws simple.DependencyException {
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
    public void factoryB1Correctly() throws simple.DependencyException {
        addConstant();
        addFactory();

        InterfaceB b = (InterfaceB) injector.getObject("C");
        ImplementationB1 b1 = (ImplementationB1) b;

        InterfaceD d = b1.getD();
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }

    @Test
    public void factoryC1Correctly() throws simple.DependencyException {
        addConstant();
        addFactory();

        InterfaceC c = (InterfaceC) injector.getObject("T");
        ImplementationC1 c1 = (ImplementationC1) c;
        assertThat(c1.getS(), is("CONSTANT"));
    }

    @Test
    public void factoryD1Correctly() throws simple.DependencyException {
        addConstant();
        addFactory();

        InterfaceD d = (InterfaceD) injector.getObject("Z");
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }

    @Test
    public void setInadequateObject() throws simple.DependencyException {
        addConstant();
        addFactory();
        injector.registerSingleton("H", new FactoryD1(), "T");

        assertThrows(simple.DependencyException.class,() -> injector.getObject("H"));
    }

    @Test
    public void getLoop() throws simple.DependencyException {
        addConstant();
        injector.registerSingleton("G", new FactoryD1(), "B");
        injector.registerSingleton("B", new FactoryD1(), "H");
        injector.registerSingleton("H", new FactoryD1(), "G");

        assertThrows(simple.DependencyException.class,() -> injector.getObject("H"));
    }

    private void addConstant() throws simple.DependencyException {
        injector = new Container();

        injector.registerConstant("I", 42);
        injector.registerConstant("E", 52);
        injector.registerConstant("N", 82);
        injector.registerConstant("A", "CONSTANT");
    }

    private void addFactory() throws simple.DependencyException {

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

    }*/
}