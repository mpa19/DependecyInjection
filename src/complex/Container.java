package complex;

import utils.Relation;

import java.util.HashMap;
import java.util.Map;

public class Container implements Injector {

    private enum ObjectType {CONSTANT, FACTORY, SINGLETON}

    private final Map<Class, Relation> services;

    private static Map<Class, Object> singleton;

    public Container() {
        this.services = new HashMap<>();
        this.singleton = new HashMap<>();
    }

    @Override
    public <E> void registerConstant(Class<E> name, E value) throws DependencyException {
        if(services.containsKey(name))
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        services.put(name, new Relation<>(ObjectType.CONSTANT, value, null));
    }

    @Override
    public <E> void registerFactory(Class<E> name, Factory<? extends E> creator, Class<E>... parameters) throws DependencyException {
        if(services.containsKey(name))
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        services.put(name, new Relation<>(ObjectType.FACTORY, creator, parameters));
    }

    @Override
    public <E> void registerSingleton(Class<E> name, Factory<? extends E> creator, Class<E>... parameters) throws DependencyException {
        if(services.containsKey(name))
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        services.put(name, new Relation<>(ObjectType.SINGLETON, creator, parameters));
    }

    @Override
    public <E> E getObject(Class<E> name) throws DependencyException {
        Relation<ObjectType, Object, Object> value = services.get(name);
        if(value == null)
            throw new DependencyException(new DependencyException("The key was not found in the map."));

        switch(value.getType()) {
            case FACTORY:
                return ((Factory<E>)value.getFactoryVal()).create(funAux(value));
            case CONSTANT:
                return (E) value.getFactoryVal();
            case SINGLETON:
                if(!singleton.containsKey(name)){
                    E val = ((Factory<E>)value.getFactoryVal()).create(funAux(value));
                    singleton.put(name, val);
                    return val;
                }
                return (E) singleton.get(name);
        }
        throw new DependencyException(new DependencyException("The ObjectType was neither SERVICE nor CONSTANT."));
    }

    private Object[] funAux(Relation value) throws DependencyException {
        Class[] values = (Class[]) value.getDependencies();

        Object[] params = new Object[values.length];
        int cont = 0;

        for(Class x : values){
            params[cont] = this.getObject(x);
            cont++;
        }
        return params;
    }
}
