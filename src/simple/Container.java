package simple;

import utils.Relation;

import java.util.HashMap;
import java.util.Map;

public class Container implements Injector {

    private enum ObjectType {CONSTANT, FACTORY, SINGLETON}

    private final Map<String, Relation> services;

    private static Map<String, Object> singleton;

    public Container() {
        this.services = new HashMap<>();
        this.singleton = new HashMap<>();
    }

    @Override
    public void registerConstant(String name, Object value) throws DependencyException {
        if(services.containsKey(name))
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        services.put(name, new Relation<>(ObjectType.CONSTANT, value, null));
    }

    @Override
    public void registerFactory(String name, Factory creator, String... parameters) throws DependencyException {
        if(services.containsKey(name))
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        services.put(name, new Relation<>(ObjectType.FACTORY, creator, parameters));
    }

    @Override
    public void registerSingleton(String name, Factory creator, String... parameters) throws DependencyException {
        if(services.containsKey(name))
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        services.put(name, new Relation<>(ObjectType.SINGLETON, creator, parameters));
    }

    @Override
    public Object getObject(String name) throws DependencyException {
        Relation<ObjectType, Object, Object> value = services.get(name);
        if(value == null)
            throw new DependencyException(new DependencyException("The key was not found in the map."));

        switch(value.getType()) {
            case FACTORY:
                return ((Factory)value.getFactoryVal()).create(funAux(value));
            case CONSTANT:
                return value.getFactoryVal();
            case SINGLETON:
                if(!singleton.containsKey(name)){
                    Object val = ((Factory)value.getFactoryVal()).create(funAux(value));
                    singleton.put(name, val);
                    return val;
                }
                return singleton.get(name);
        }
        throw new DependencyException(new DependencyException("The ObjectType was neither SERVICE nor CONSTANT."));
    }

    private Object[] funAux(Relation value) throws DependencyException {
        String[] values = (String[]) value.getDependencies();

        Object[] params = new Object[values.length];
        int cont = 0;

        for(String x : values){
            params[cont] = this.getObject(x);
            cont++;
        }
        return params;
    }
}
