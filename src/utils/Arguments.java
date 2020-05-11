package utils;

public class Arguments<A, B, C> {
    private final A type;
    private final B factoryVal;
    private final C dependencies;


    public Arguments(A type, B factoryVal, C third) {
        this.type = type;
        this.factoryVal = factoryVal;
        this.dependencies = third;
    }

    public Arguments(A type, B factoryVal) {
        this.type = type;
        this.factoryVal = factoryVal;
        this.dependencies = null;
    }

    public A getType() { return type; }

    public B getFactoryVal() {
        return factoryVal;
    }

    public C getDependencies() {
        return dependencies;
    }

}
